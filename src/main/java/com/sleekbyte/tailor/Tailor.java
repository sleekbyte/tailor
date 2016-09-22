package com.sleekbyte.tailor;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftLexer;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.TopLevelContext;
import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ConfigProperties;
import com.sleekbyte.tailor.common.ConstructLengths;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.format.Formatter;
import com.sleekbyte.tailor.integration.XcodeIntegrator;
import com.sleekbyte.tailor.listeners.BlankLineListener;
import com.sleekbyte.tailor.listeners.BraceStyleListener;
import com.sleekbyte.tailor.listeners.ErrorListener;
import com.sleekbyte.tailor.listeners.FileListener;
import com.sleekbyte.tailor.listeners.TodoCommentListener;
import com.sleekbyte.tailor.listeners.lengths.MaxLengthListener;
import com.sleekbyte.tailor.listeners.lengths.MinLengthListener;
import com.sleekbyte.tailor.listeners.whitespace.CommentWhitespaceListener;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.output.ViolationSuppressor;
import com.sleekbyte.tailor.utils.CLIArgumentParser.CLIArgumentParserException;
import com.sleekbyte.tailor.utils.CommentExtractor;
import com.sleekbyte.tailor.utils.Configuration;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.ParseException;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Performs static analysis on Swift source files.
 */
public final class Tailor {

    public AtomicInteger numSkippedFiles = new AtomicInteger(0);
    public AtomicLong numErrors = new AtomicLong(0);
    public AtomicLong numWarnings = new AtomicLong(0);
    public Configuration configuration;
    public ConcurrentSkipListSet<Printer> printersForAllFiles = new ConcurrentSkipListSet<>();

    private int numberOfFilesBeforePurge;
    private AtomicInteger numFiles = new AtomicInteger(0);

    /**
     * Non-zero exit status when any violation messages have Severity.ERROR, controlled by --max-severity
     */
    public static void handleErrorViolations(Formatter formatter, long numErrors) {
        ExitCode exitCode = formatter.getExitStatus(numErrors);
        if (exitCode != ExitCode.SUCCESS) {
            System.exit(exitCode.ordinal());
        }
    }

    /**
     * Handle command line exceptions by printing out error message and exiting.
     */
    public static void handleCLIException(Exception exception) {
        System.err.println(exception.getMessage());
        Configuration.printHelp();
        System.exit(ExitCode.failure());
    }

    /**
     * Handle YAML related exceptions by printing out appropriate error message and exiting.
     */
    public static void handleYAMLException(YAMLException exception) {
        System.err.println("Error parsing .tailor.yml:");
        System.err.println(exception.getMessage());
        System.exit(ExitCode.failure());
    }

    /**
     * Handle IO exceptions by printing out appropriate error message and exiting.
     */
    public static void handleIOException(IOException exception) {
        System.err.println("Source file analysis failed. Reason: " + exception.getMessage());
        System.exit(ExitCode.failure());
    }

    /**
     * Prints error indicating no source file was provided, and exits.
     */
    private void exitWithNoSourceFilesError() {
        System.err.println(Messages.NO_SWIFT_FILES_FOUND);
        configuration.printHelp();
        System.exit(ExitCode.failure());
    }

    /**
     * Creates listeners according to the rules that are enabled.
     *
     * @param enabledRules list of enabled rules
     * @param printer      passed into listener constructors
     * @param tokenStream  passed into listener constructors
     * @throws CLIArgumentParserException if listener for an enabled rule is not found
     */
    private List<SwiftBaseListener> createListeners(Set<Rules> enabledRules,
                                                    Printer printer,
                                                    CommonTokenStream tokenStream,
                                                    ConstructLengths constructLengths,
                                                    CommentExtractor commentExtractor)
        throws CLIArgumentParserException {

        List<SwiftBaseListener> listeners = new LinkedList<>();
        Set<String> classNames = enabledRules.stream().map(Rules::getClassName).collect(Collectors.toSet());
        for (String className : classNames) {
            try {

                if (className.equals(FileListener.class.getName())) {
                    continue;
                }

                if (className.equals(CommentWhitespaceListener.class.getName())) {
                    CommentWhitespaceListener commentWhitespaceListener = new CommentWhitespaceListener(printer,
                        commentExtractor.getSingleLineComments(), commentExtractor.getMultilineComments());
                    commentWhitespaceListener.analyze();
                } else if (className.equals(TodoCommentListener.class.getName())) {
                    TodoCommentListener todoCommentListener = new TodoCommentListener(printer,
                        commentExtractor.getSingleLineComments(), commentExtractor.getMultilineComments());
                    todoCommentListener.analyze();
                } else if (className.equals(BraceStyleListener.class.getName())) {
                    listeners.add(new BraceStyleListener(printer, tokenStream));
                } else if (className.equals(BlankLineListener.class.getName())) {
                    listeners.add(new BlankLineListener(printer, tokenStream));
                } else {
                    Constructor listenerConstructor = Class.forName(className).getConstructor(Printer.class);
                    listeners.add((SwiftBaseListener) listenerConstructor.newInstance(printer));
                }

            } catch (ReflectiveOperationException e) {
                throw new CLIArgumentParserException("Listeners were not successfully created: " + e);
            }
        }

        listeners.add(new MinLengthListener(printer, constructLengths, enabledRules));
        listeners.add(new MaxLengthListener(printer, constructLengths, enabledRules));

        return listeners;
    }

    /** Runs SwiftLexer on input file to generate token stream.
     *
     * @param input Lexer input
     * @return Token stream
     */
    private Optional<CommonTokenStream> getTokenStream(File input) {
        try (FileInputStream inputStream = new FileInputStream(input)) {
            SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
            if (!configuration.debugFlagSet()) {
                lexer.removeErrorListeners();
                lexer.addErrorListener(new ErrorListener());
            }
            return Optional.of(new CommonTokenStream(lexer));
        } catch (IOException e) {
            handleIOException(e);
        } catch (CLIArgumentParserException e) {
            handleCLIException(e);
        }
        return Optional.empty();
    }

    /**
     * Clear the DFA cache if --purge option is set.
     *
     * @param parser current SwiftParser instance
     */
    private void clearDFACache(SwiftParser parser) {
        numFiles.incrementAndGet();
        if (numFiles.compareAndSet(numberOfFilesBeforePurge, 0)) {
            parser.getInterpreter().clearDFA();
        }
    }

    /**
     * Parse token stream to generate a CST.
     *
     * @param tokenStream Token stream generated by lexer
     * @return Parse Tree or null if parsing error occurs (and debug flag is set)
     */
    private Optional<TopLevelContext> getParseTree(Optional<CommonTokenStream> tokenStream) {
        Optional<TopLevelContext> tree = Optional.empty();
        if (!tokenStream.isPresent()) {
            return tree;
        }
        SwiftParser swiftParser = new SwiftParser(tokenStream.get());
        try {
            if (!configuration.debugFlagSet()) {
                swiftParser.removeErrorListeners();
                swiftParser.addErrorListener(new ErrorListener());
            }
            tree = Optional.of(swiftParser.topLevel());
        } catch (CLIArgumentParserException e) {
            handleCLIException(e);
        }
        if (configuration.shouldClearDFAs()) {
            clearDFACache(swiftParser);
        }
        return tree;
    }

    /**
     * Walks the provided parse tree using the list of listeners.
     *
     * @param listeners List of parse tree listeners.
     * @param tree Parse tree.
     */
    private void walkParseTree(List<SwiftBaseListener> listeners, TopLevelContext tree) {
        ParseTreeWalker walker = new ParseTreeWalker();
        listeners.forEach(listener -> walker.walk(listener, tree));
    }

    /**
     * Analyzes an individual file by creating the corresponding listeners and walking the file's parse tree.
     *
     * @param inputFile File to analyze.
     * @param optTokenStream Common token stream for input file.
     * @param optTree Parse tree for input file.
     * @throws CLIArgumentParserException if an error occurs when parsing cmd line arguments
     */
    private void analyzeFile(File inputFile,
                             Optional<CommonTokenStream> optTokenStream,
                             Optional<TopLevelContext> optTree,
                             Formatter formatter,
                             Severity maxSeverity,
                             ConstructLengths constructLengths,
                             Set<Rules> enabledRules)
        throws CLIArgumentParserException {

        try {
            Printer printer = new Printer(inputFile, maxSeverity, formatter);
            if (optTokenStream.isPresent() && optTree.isPresent()) {
                CommonTokenStream tokenStream = optTokenStream.get();
                TopLevelContext tree = optTree.get();

                // Suppress violations for lines that have been disabled in source code
                CommentExtractor commentExtractor = new CommentExtractor(tokenStream);
                ViolationSuppressor disableAnalysis = new ViolationSuppressor(printer,
                    commentExtractor.getSingleLineComments(),
                    commentExtractor.getMultilineComments());
                disableAnalysis.analyze();

                // Generate listeners
                List<SwiftBaseListener> listeners =
                    createListeners(enabledRules, printer, tokenStream, constructLengths, commentExtractor);
                walkParseTree(listeners, tree);
                try (FileListener fileListener =
                         new FileListener(printer, inputFile, constructLengths, enabledRules)) {
                    fileListener.verify();
                }

                numErrors.addAndGet(printer.getNumErrorMessages());
                numWarnings.addAndGet(printer.getNumWarningMessages());

            } else {
                printer.setShouldPrintParseErrorMessage(true);
            }
            printersForAllFiles.add(printer);
        } catch (IOException e) {
            handleIOException(e);
        } catch (CLIArgumentParserException e) {
            handleCLIException(e);
        }
    }

    /**
     * Analyze files with SwiftLexer, SwiftParser and Listeners.
     *
     * @param fileNames List of files to analyze
     * @throws CLIArgumentParserException if an error occurs when parsing cmd line arguments
     * @throws IOException if a file cannot be opened
     */
    private void analyzeFiles(Set<String> fileNames) throws CLIArgumentParserException, IOException {
        ColorSettings colorSettings =
            new ColorSettings(configuration.shouldColorOutput(), configuration.shouldInvertColorOutput());
        Formatter formatter = configuration.getFormatter(colorSettings);
        Severity maxSeverity = configuration.getMaxSeverity();
        ConstructLengths constructLengths = configuration.parseConstructLengths();
        Set<Rules> enabledRules = configuration.getEnabledRules();

        numberOfFilesBeforePurge = configuration.numberOfFilesBeforePurge();

        List<File> files = fileNames.parallelStream().map(File::new).collect(Collectors.toList());
        formatter.printProgressInfo(
            String.format("Analyzing %s:%n", Formatter.pluralize(fileNames.size(), "file", "files")));

        files.parallelStream().forEach(
            file -> {
                try {
                    Optional<CommonTokenStream> tokenStream = getTokenStream(file);
                    Optional<TopLevelContext> tree = getParseTree(tokenStream);
                    analyzeFile(file, tokenStream, tree, formatter, maxSeverity, constructLengths, enabledRules);
                    formatter.printProgressInfo(".");
                } catch (ErrorListener.ParseException e) {
                    formatter.printProgressInfo("S");
                    Printer printer = new Printer(file, maxSeverity, formatter);
                    printer.setShouldPrintParseErrorMessage(true);
                    printersForAllFiles.add(printer);
                    numSkippedFiles.incrementAndGet();
                } catch (CLIArgumentParserException e) {
                    handleCLIException(e);
                }
            });
        formatter.printProgressInfo(String.format("%n"));

        printersForAllFiles.forEach(printer -> {
                try {
                    printer.printAllMessages();
                } catch (IOException e) {
                    handleIOException(e);
                }
            });

        formatter.displaySummary(fileNames.size(), numSkippedFiles.get(), numErrors.get(), numWarnings.get());
        handleErrorViolations(formatter, numErrors.get());
    }

    /**
     * Main runner for Tailor.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Tailor tailor = new Tailor();

        try {
            tailor.configuration = new Configuration(args);

            if (tailor.configuration.shouldPrintHelp()) {
                tailor.configuration.printHelp();
                System.exit(ExitCode.success());
            }
            if (tailor.configuration.shouldPrintVersion()) {
                System.out.println(new ConfigProperties().getVersion());
                System.exit(ExitCode.success());
            }
            if (tailor.configuration.shouldPrintRules()) {
                Printer.printRules();
                System.exit(ExitCode.success());
            }

            // Exit program after configuring Xcode project
            String xcodeprojPath = tailor.configuration.getXcodeprojPath();
            if (xcodeprojPath != null) {
                System.exit(XcodeIntegrator.setupXcode(xcodeprojPath));
            }

            Set<String> fileNames = tailor.configuration.getFilesToAnalyze();
            if (fileNames.size() == 0) {
                tailor.exitWithNoSourceFilesError();
            }

            if (tailor.configuration.shouldListFiles()) {
                System.out.println(Messages.FILES_TO_BE_ANALYZED);
                fileNames.forEach(System.out::println);
                System.exit(ExitCode.success());
            }

            tailor.analyzeFiles(fileNames);
        } catch (ParseException | CLIArgumentParserException e) {
            Tailor.handleCLIException(e);
        } catch (YAMLException e) {
            Tailor.handleYAMLException(e);
        } catch (IOException e) {
            Tailor.handleIOException(e);
        }
    }
}
