package com.sleekbyte.tailor;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftLexer;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.TopLevelContext;
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
import com.sleekbyte.tailor.utils.ArgumentParser;
import com.sleekbyte.tailor.utils.ArgumentParser.ArgumentParserException;
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

    /**
     * Prints error indicating no source file was provided, and exits.
     */
    public static void exitWithNoSourceFilesError() {
        System.err.println(Messages.NO_SWIFT_FILES_FOUND);
        argumentParser.printHelp();
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

        Optional<Configuration> optionalConfig =
            ConfigurationFileManager.getConfiguration(argumentParser.getConfigFilePath());
        Optional<String> srcRoot = getSrcRoot();
        if (pathNames.size() >= 1) {
            fileNames.addAll(findFilesInPaths());
        } else if (optionalConfig.isPresent()) {
            Configuration config = optionalConfig.get();
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
     * @throws ArgumentParserException if listener for an enabled rule is not found
     */
    public static List<SwiftBaseListener> createListeners(Set<Rules> enabledRules, Printer printer,
                                                          CommonTokenStream tokenStream,
                                                          ConstructLengths constructLengths)
        throws ArgumentParserException {
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
                throw new ArgumentParserException("Listeners were not successfully created: " + e);
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
     */
    public static Optional<CommonTokenStream> getTokenStream(File input) {
        try {
            FileInputStream inputStream = new FileInputStream(input);
            SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
            if (!argumentParser.debugFlagSet()) {
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
     */
    public static Optional<TopLevelContext> getParseTree(Optional<CommonTokenStream> tokenStream) {
        Optional<TopLevelContext> tree = Optional.empty();
        if (!tokenStream.isPresent()) {
            return tree;
        }
        SwiftParser swiftParser = new SwiftParser(tokenStream.get());
        try {
            if (!argumentParser.debugFlagSet()) {
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
     * @param inputFile File to analyze.
     * @param optTokenStream Common token stream for input file.
     * @param optTree Parse tree for input file.
     * @throws ArgumentParserException if an error occurs when parsing cmd line arguments
     */
    public static void analyzeFile(File inputFile, Optional<CommonTokenStream> optTokenStream,
                                   Optional<TopLevelContext> optTree)
            throws ArgumentParserException {
        ConstructLengths constructLengths = argumentParser.parseConstructLengths();
        Severity maxSeverity = argumentParser.getMaxSeverity();
        ColorSettings colorSettings =
            new ColorSettings(argumentParser.shouldColorOutput(), argumentParser.shouldInvertColorOutput());
        Set<Rules> enabledRules = argumentParser.getEnabledRules();

        try {
            try (Printer printer = new Printer(inputFile, maxSeverity, colorSettings)) {
                if (optTokenStream.isPresent() && optTree.isPresent()) {
                    CommonTokenStream tokenStream = optTokenStream.get();
                    TopLevelContext tree = optTree.get();
                    List<SwiftBaseListener> listeners = createListeners(
                        enabledRules,
                        printer,
                        tokenStream,
                        constructLengths
                    );

                    walkParseTree(listeners, tree);

                    try (FileListener fileListener =
                             new FileListener(printer, inputFile, constructLengths, enabledRules)) {
                        fileListener.verify();
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

            final CommandLine cmd = argumentParser.parseCommandLine(args);
            if (argumentParser.shouldPrintHelp()) {
                argumentParser.printHelp();
                System.exit(ExitCode.success());
            }
            if (argumentParser.shouldPrintVersion()) {
                System.out.println(new ConfigProperties().getVersion());
                System.exit(ExitCode.success());
            }
            if (argumentParser.shouldPrintRules()) {
                Printer.printRules();
                System.exit(ExitCode.success());
            }

            // Exit program after configuring Xcode project
            String xcodeprojPath = argumentParser.getXcodeprojPath();
            if (xcodeprojPath != null) {
                System.exit(XcodeIntegrator.setupXcode(xcodeprojPath));
            }
            Set<String> fileNames = getSwiftSourceFiles(cmd.getArgs());
            if (fileNames.size() == 0) {
                exitWithNoSourceFilesError();
            }

            if (argumentParser.shouldListFiles()) {
                System.out.println(Messages.FILES_TO_BE_ANALYZED);
                fileNames.forEach(System.out::println);
                System.exit(ExitCode.success());
            }

            analyzeFiles(fileNames);
        } catch (ParseException | ArgumentParserException e) {
            handleCliException(e);
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
