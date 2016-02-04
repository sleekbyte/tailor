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
import com.sleekbyte.tailor.listeners.ConstantNamingListener;
import com.sleekbyte.tailor.listeners.DeclarationListener;
import com.sleekbyte.tailor.listeners.ErrorListener;
import com.sleekbyte.tailor.listeners.FileListener;
import com.sleekbyte.tailor.listeners.KPrefixListener;
import com.sleekbyte.tailor.listeners.TodoCommentListener;
import com.sleekbyte.tailor.listeners.lengths.MaxLengthListener;
import com.sleekbyte.tailor.listeners.lengths.MinLengthListener;
import com.sleekbyte.tailor.listeners.whitespace.CommentWhitespaceListener;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ArgumentParser;
import com.sleekbyte.tailor.utils.ArgumentParser.ArgumentParserException;
import com.sleekbyte.tailor.utils.CliArgumentParserException;
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
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Performs static analysis on Swift source files.
 */
public class Tailor {

    private static ArgumentParser argumentParser = new ArgumentParser();
    private static List<String> pathNames;
    private static long numSkippedFiles = 0;
    private static long numErrors = 0;
    private static long numWarnings = 0;
    private static Configuration configuration;

    /**
     * Prints error indicating no source file was provided, and exits.
     */
    public static void exitWithNoSourceFilesError() {
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
     * @throws CliArgumentParserException if listener for an enabled rule is not found
     */
    public static List<SwiftBaseListener> createListeners(Set<Rules> enabledRules, Printer printer,
                                                          CommonTokenStream tokenStream,
                                                          ConstructLengths constructLengths)
        throws CliArgumentParserException {

        List<SwiftBaseListener> listeners = new LinkedList<>();
        Set<String> classNames = enabledRules.stream().map(Rules::getClassName).collect(Collectors.toSet());

        for (String className : classNames) {
            try {

                CommentExtractor commentExtractor = new CommentExtractor(tokenStream);
                if (className.equals(FileListener.class.getName())) {
                    continue;
                } else if (className.equals(CommentWhitespaceListener.class.getName())) {
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
                throw new CliArgumentParserException("Listeners were not successfully created: " + e);
            }
        }

        listeners.add(new MinLengthListener(printer, constructLengths, enabledRules));
        listeners.add(new MaxLengthListener(printer, constructLengths, enabledRules));
        DeclarationListener decListener = new DeclarationListener(listeners);
        listeners.add(decListener);

        return listeners;
    }

    /** Runs SwiftLexer on input file to generate token stream.
     *
     * @param input Lexer input
     * @return Token stream
     * @throws IOException if file cannot be opened
     * @throws CliArgumentParserException if cmd line arguments cannot be parsed
     */
    public static Optional<CommonTokenStream> getTokenStream(File input) throws IOException,
        CliArgumentParserException {
        try {
            FileInputStream inputStream = new FileInputStream(input);
            SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
            if (!configuration.debugFlagSet()) {
                lexer.removeErrorListeners();
                lexer.addErrorListener(new ErrorListener());
            }
            return Optional.of(new CommonTokenStream(lexer));
        } catch (IOException e) {
            handleIoException(e);
        } catch (ArgumentParserException e) {
            handleCliException(e);
        }
        return Optional.empty();
    }

    /**
     * Parse token stream to generate a CST.
     *
     * @param tokenStream Token stream generated by lexer
     * @return Parse Tree or null if parsing error occurs (and debug flag is set)
     * @throws CliArgumentParserException if an error occurs when parsing cmd line arguments
     */
    public static Optional<TopLevelContext> getParseTree(Optional<CommonTokenStream> tokenStream)
        throws CliArgumentParserException {
        Optional<TopLevelContext> tree = Optional.empty();
        if (!tokenStream.isPresent()) {
            return tree;
        SwiftParser swiftParser = new SwiftParser(tokenStream.get());
        try {
            if (!configuration.debugFlagSet()) {
                swiftParser.removeErrorListeners();
                swiftParser.addErrorListener(new ErrorListener());
            }
            tree = Optional.of(swiftParser.topLevel());
        } catch (ArgumentParserException e) {
            handleCliException(e);
        }
        return tree;
    }

    /**
     * Walks the provided parse tree using the list of listeners.
     *
     * @param listeners List of parse tree listeners.
     * @param tree Parse tree.
     */
    public static void walkParseTree(List<SwiftBaseListener> listeners, TopLevelContext tree) {
        ParseTreeWalker walker = new ParseTreeWalker();
        for (SwiftBaseListener listener : listeners) {
            // The following listeners are used by DeclarationListener to walk the tree
            if (listener instanceof ConstantNamingListener || listener instanceof KPrefixListener) {
                continue;
            }
            walker.walk(listener, tree);
        }
    }

    /**
     * Analyzes an individual file by creating the corresponding listeners and walking the file's parse tree.
     *
     * @param fileNames List of files to analyze
     * @throws CliArgumentParserException if an error occurs when parsing cmd line arguments
     * @throws IOException if a file cannot be opened
     */
    public static void analyzeFiles(Set<String> fileNames) throws CliArgumentParserException, IOException {
        long numErrors = 0;
        long numSkippedFiles = 0;
        long numWarnings = 0;
        ConstructLengths constructLengths = configuration.parseConstructLengths();
        Severity maxSeverity = configuration.getMaxSeverity();
        ColorSettings colorSettings =
            new ColorSettings(configuration.shouldColorOutput(), configuration.shouldInvertColorOutput());
        Set<Rules> enabledRules = configuration.getEnabledRules();
        Formatter formatter = null;

        for (String fileName : fileNames) {
            File inputFile = new File(fileName);
            CommonTokenStream tokenStream;
            SwiftParser.TopLevelContext tree;
            formatter = configuration.getFormatter(inputFile, colorSettings);

            try {
                tokenStream = getTokenStream(inputFile);
                tree = getParseTree(tokenStream);
            } catch (ErrorListener.ParseException e) {
                Printer printer = new Printer(inputFile, maxSeverity, formatter);
                printer.printParseErrorMessage();
                numSkippedFiles++;
                continue;
            }

            try (Printer printer = new Printer(inputFile, maxSeverity, formatter)) {
                List<SwiftBaseListener> listeners = createListeners(enabledRules, printer, tokenStream);
                listeners.add(new MaxLengthListener(printer, constructLengths, enabledRules));
                listeners.add(new MinLengthListener(printer, constructLengths, enabledRules));
                DeclarationListener decListener = new DeclarationListener(listeners);
                listeners.add(decListener);

                ParseTreeWalker walker = new ParseTreeWalker();
                for (SwiftBaseListener listener : listeners) {
                    // The following listeners are used by DeclarationListener to walk the tree
                    if (listener instanceof ConstantNamingListener || listener instanceof KPrefixListener) {
                        continue;
                    }

                    numErrors += printer.getNumErrorMessages();
                    numWarnings += printer.getNumWarningMessages();
                } else {
                    printer.printParseErrorMessage();
                }
            }
        } catch (IOException e) {
            handleIoException(e);
        } catch (ArgumentParserException e) {
            handleCliException(e);
        }
    }

    /**
     * Analyze files with SwiftLexer, SwiftParser and Listeners.
     *
     * @param fileNames List of files to analyze
     * @throws ArgumentParserException if an error occurs when parsing cmd line arguments
     * @throws IOException if a file cannot be opened
     */
    public static void analyzeFiles(Set<String> fileNames) throws ArgumentParserException, IOException {
        numErrors = 0;
        numWarnings = 0;

        List<File> files = fileNames.parallelStream().map(File::new).collect(Collectors.toList());
        ConcurrentMap<File, Optional<CommonTokenStream>> filesToTokenStreams =
            files.parallelStream().collect(Collectors.toConcurrentMap(Function.identity(), Tailor::getTokenStream));
        System.out.format("Analyzing %s:%n", pluralize(fileNames.size(), "file", "files"));
        ConcurrentMap<File, Optional<TopLevelContext>> filesToTrees = files
            .parallelStream()
            .collect(Collectors.toConcurrentMap(Function.identity(),
                file -> {
                    Optional<TopLevelContext> tree = Optional.empty();
                    try {
                        tree = Tailor.getParseTree(filesToTokenStreams.get(file));
                        System.out.print(".");
                    } catch (ErrorListener.ParseException e) {
                        System.out.print("S");
                        numSkippedFiles++;
                    }
                    return tree;
                }));
        System.out.format("%n");
        files.stream().forEach(
            file -> {
                try {
                    Tailor.analyzeFile(file, filesToTokenStreams.get(file), filesToTrees.get(file));
                } catch (ArgumentParserException e) {
                    handleCliException(e);
                }
            });

        if (formatter != null) {
            formatter.displaySummary(fileNames.size(), numSkippedFiles, numErrors, numWarnings);
            // Non-zero exit status when any violation messages have Severity.ERROR, controlled by --max-severity
            ExitCode exitCode = formatter.getExitStatus(numErrors);
            if (exitCode != ExitCode.SUCCESS) {
                System.exit(exitCode.ordinal());
            }
        }
    }

    /**
     * Main runner for Tailor.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        try {
            configuration = new Configuration(args);

            if (configuration.shouldPrintHelp()) {
                configuration.printHelp();
                System.exit(ExitCode.success());
            }
            if (configuration.shouldPrintVersion()) {
                System.out.println(new ConfigProperties().getVersion());
                System.exit(ExitCode.success());
            }
            if (configuration.shouldPrintRules()) {
                Printer.printRules();
                System.exit(ExitCode.success());
            }

            // Exit program after configuring Xcode project
            String xcodeprojPath = configuration.getXcodeprojPath();
            if (xcodeprojPath != null) {
                System.exit(XcodeIntegrator.setupXcode(xcodeprojPath));
            }

            Set<String> fileNames = configuration.getFilesToAnalyze();
            if (fileNames.size() == 0) {
                exitWithNoSourceFilesError();
            }

            if (configuration.shouldListFiles()) {
                System.out.println(Messages.FILES_TO_BE_ANALYZED);
                fileNames.forEach(System.out::println);
                System.exit(ExitCode.success());
            }

            analyzeFiles(fileNames);
        } catch (ParseException | CliArgumentParserException e) {
            System.err.println(e.getMessage());
            configuration.printHelp();
            System.exit(ExitCode.failure());
        } catch (YAMLException e) {
            handleYamlException(e);
        } catch (IOException e) {
            handleIoException(e);
        }

    }

    private static void handleCliException(Exception exception) {
        System.err.println(exception.getMessage());
        argumentParser.printHelp();
        System.exit(ExitCode.failure());
    }

    private static void handleYamlException(YAMLException exception) {
        System.err.println("Error parsing .tailor.yml:");
        System.err.println(exception.getMessage());
        System.exit(ExitCode.failure());
    }

    private static void handleIoException(IOException exception) {
        System.err.println("Source file analysis failed. Reason: " + exception.getMessage());
        System.exit(ExitCode.failure());
    }

}
