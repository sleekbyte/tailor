package com.sleekbyte.tailor;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftLexer;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ConfigProperties;
import com.sleekbyte.tailor.common.Configuration;
import com.sleekbyte.tailor.common.ConstructLengths;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
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
import com.sleekbyte.tailor.utils.CliArgumentParser;
import com.sleekbyte.tailor.utils.CliArgumentParserException;
import com.sleekbyte.tailor.utils.CommentExtractor;
import com.sleekbyte.tailor.utils.ConfigurationFileManager;
import com.sleekbyte.tailor.utils.Finder;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Performs static analysis on Swift source files.
 */
public class Tailor {

    private static CliArgumentParser cliArgumentParser = new CliArgumentParser();
    private static Optional<Configuration> configuration;
    private static List<String> pathNames;

    /**
     * Prints error indicating no source file was provided, and exits.
     */
    public static void exitWithNoSourceFilesError() {
        System.err.println(Messages.NO_SWIFT_FILES_FOUND);
        cliArgumentParser.printHelp();
        System.exit(ExitCode.failure());
    }

    /**
     * Checks environment variable SRCROOT (set by Xcode) for the top-level path to the source code and adds path to
     * pathNames.
     */
    public static Optional<String> getSrcRoot() {
        String srcRoot = System.getenv("SRCROOT");
        if (srcRoot == null || srcRoot.equals("")) {
            return Optional.empty();
        }
        return Optional.of(srcRoot);
    }

    /**
     * Iterate through pathNames and derive swift source files from each path.
     *
     * @return Swift file names
     * @throws IOException if path specified does not exist
     */
    public static Set<String> getSwiftSourceFiles(String[] cliPaths) throws IOException {
        if (cliPaths.length >= 1) {
            pathNames.addAll(Arrays.asList(cliPaths));
        }
        Set<String> fileNames = new TreeSet<>();

        Optional<String> srcRoot = getSrcRoot();
        if (pathNames.size() >= 1) {
            fileNames.addAll(findFilesInPaths());
        } else if (configuration.isPresent()) {
            Configuration config = configuration.get();
            Optional<String> configFileLocation = config.getFileLocation();
            if (configFileLocation.isPresent()) {
                System.out.println(Messages.TAILOR_CONFIG_LOCATION + configFileLocation.get());
            }
            URI rootUri = new File(srcRoot.orElse(".")).toURI();
            Finder finder = new Finder(config.getInclude(), config.getExclude(), rootUri);
            Files.walkFileTree(Paths.get(rootUri), finder);
            fileNames.addAll(finder.getFileNames().stream().collect(Collectors.toList()));
        } else if (srcRoot.isPresent()) {
            pathNames.add(srcRoot.get());
            fileNames.addAll(findFilesInPaths());
        }

        return fileNames;
    }

    private static Set<String> findFilesInPaths() throws IOException {
        Set<String> fileNames = new HashSet<>();
        for (String pathName : pathNames) {
            File file = new File(pathName);
            if (file.isDirectory()) {
                Files.walk(Paths.get(pathName))
                    .filter(path -> path.toString().endsWith(".swift"))
                    .filter(path -> {
                            File tempFile = path.toFile();
                            return tempFile.isFile() && tempFile.canRead();
                        })
                    .forEach(path -> fileNames.add(path.toString()));
            } else if (file.isFile() && pathName.endsWith(".swift") && file.canRead()) {
                fileNames.add(pathName);
            }
        }
        return fileNames;
    }

    private static String pluralize(long number, String singular, String plural) {
        return String.format("%d %s", number, number == 1 ? singular : plural);
    }

    /**
     * Print results of analysis.
     *
     * @param numFiles Number of swift files detected
     * @param numSkipped Number of swift files skipped due to parsing errors
     * @param numErrors Number of error violations
     * @param numWarnings Number of warning violations
     */
    public static void printSummary(long numFiles, long numSkipped, long numErrors, long numWarnings) {
        long numFilesAnalyzed = numFiles - numSkipped;
        long numViolations = numErrors + numWarnings;
        System.out.println(String.format("%nAnalyzed %s, skipped %s, and detected %s (%s, %s).%n",
            pluralize(numFilesAnalyzed, "file", "files"),
            pluralize(numSkipped, "file", "files"),
            pluralize(numViolations, "violation", "violations"),
            pluralize(numErrors, "error", "errors"),
            pluralize(numWarnings, "warning", "warnings")
        ));
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
                                                          CommonTokenStream tokenStream)
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

            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
                | InstantiationException | IllegalAccessException e) {
                throw new CliArgumentParserException("Listeners were not successfully created: " + e);
            }
        }
        return listeners;
    }

    /** Runs SwiftLexer on input file to generate token stream.
     *
     * @param inputFile Lexer input
     * @return Token stream
     * @throws IOException if file cannot be opened
     * @throws CliArgumentParserException if cmd line arguments cannot be parsed
     */
    public static CommonTokenStream getTokenStream(File inputFile) throws IOException, CliArgumentParserException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
        if (!cliArgumentParser.debugFlagSet()) {
            lexer.removeErrorListeners();
            lexer.addErrorListener(new ErrorListener());
        }
        return new CommonTokenStream(lexer);
    }

    /**
     * Parse token stream to generate a CST.
     *
     * @param tokenStream Token stream generated by lexer
     * @return Parse Tree or null if parsing error occurs (and debug flag is set)
     * @throws CliArgumentParserException if an error occurs when parsing cmd line arguments
     */
    public static SwiftParser.TopLevelContext getParseTree(CommonTokenStream tokenStream)
        throws CliArgumentParserException {
        SwiftParser swiftParser = new SwiftParser(tokenStream);
        if (!cliArgumentParser.debugFlagSet()) {
            swiftParser.removeErrorListeners();
            swiftParser.addErrorListener(new ErrorListener());
        }
        return swiftParser.topLevel();
    }

    /**
     * Analyze files with SwiftLexer, SwiftParser and Listeners.
     *
     * @param fileNames List of files to analyze
     * @throws CliArgumentParserException if an error occurs when parsing cmd line arguments
     * @throws IOException if a file cannot be opened
     */
    public static void analyzeFiles(Set<String> fileNames) throws CliArgumentParserException, IOException {
        long numErrors = 0;
        long numSkippedFiles = 0;
        long numWarnings = 0;
        ConstructLengths constructLengths = cliArgumentParser.parseConstructLengths();
        Severity maxSeverity = cliArgumentParser.getMaxSeverity();
        ColorSettings colorSettings =
            new ColorSettings(cliArgumentParser.shouldColorOutput(), cliArgumentParser.shouldInvertColorOutput());
        Set<Rules> enabledRules = cliArgumentParser.getEnabledRules();

        for (String fileName : fileNames) {
            File inputFile = new File(fileName);
            CommonTokenStream tokenStream;
            SwiftParser.TopLevelContext tree;

            try {
                tokenStream = getTokenStream(inputFile);
                tree = getParseTree(tokenStream);
            } catch (ErrorListener.ParseException e) {
                Printer printer = new Printer(inputFile, maxSeverity, colorSettings);
                printer.printParseErrorMessage();
                numSkippedFiles++;
                continue;
            }

            try (Printer printer = new Printer(inputFile, maxSeverity, colorSettings)) {
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
                    walker.walk(listener, tree);
                }
                try (FileListener fileListener = new FileListener(printer, inputFile, constructLengths, enabledRules)) {
                    fileListener.verify();
                }

                numErrors += printer.getNumErrorMessages();
                numWarnings += printer.getNumWarningMessages();
            }
        }

        printSummary(fileNames.size(), numSkippedFiles, numErrors, numWarnings);

        if (numErrors >= 1L) {
            // Non-zero exit status when any violation messages have Severity.ERROR, controlled by --max-severity
            System.exit(ExitCode.failure());
        }
    }

    /**
     * Main runner for Tailor.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        try {
            pathNames = new ArrayList<>();

            final CommandLine cmd = cliArgumentParser.parseCommandLine(args);
            if (cliArgumentParser.shouldPrintHelp()) {
                cliArgumentParser.printHelp();
                System.exit(ExitCode.success());
            }
            if (cliArgumentParser.shouldPrintVersion()) {
                System.out.println(new ConfigProperties().getVersion());
                System.exit(ExitCode.success());
            }
            if (cliArgumentParser.shouldPrintRules()) {
                Printer.printRules();
                System.exit(ExitCode.success());
            }

            // Exit program after configuring Xcode project
            String xcodeprojPath = cliArgumentParser.getXcodeprojPath();
            if (xcodeprojPath != null) {
                System.exit(XcodeIntegrator.setupXcode(xcodeprojPath));
            }

            // Parse config file
            configuration = ConfigurationFileManager.getConfiguration(cliArgumentParser.getConfigFilePath());

            Set<String> fileNames = getSwiftSourceFiles(cmd.getArgs());
            if (fileNames.size() == 0) {
                exitWithNoSourceFilesError();
            }

            if (cliArgumentParser.shouldListFiles()) {
                System.out.println(Messages.FILES_TO_BE_ANALYZED);
                fileNames.forEach(System.out::println);
                System.exit(ExitCode.success());
            }

            analyzeFiles(fileNames);
        } catch (ParseException | CliArgumentParserException e) {
            System.err.println(e.getMessage());
            cliArgumentParser.printHelp();
            System.exit(ExitCode.failure());
        } catch (YAMLException e) {
            System.err.println("Error parsing .tailor.yml:");
            System.err.println(e.getMessage());
            System.exit(ExitCode.failure());
        } catch (IOException e) {
            System.err.println("Source file analysis failed. Reason: " + e.getMessage());
            System.exit(ExitCode.failure());
        }

    }

}
