package com.sleekbyte.tailor;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftLexer;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.integration.XcodeIntegrator;
import com.sleekbyte.tailor.listeners.CommentAnalyzer;
import com.sleekbyte.tailor.listeners.ErrorListener;
import com.sleekbyte.tailor.listeners.FileListener;
import com.sleekbyte.tailor.listeners.MainListener;
import com.sleekbyte.tailor.listeners.MaxLengthListener;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ArgumentParser;
import com.sleekbyte.tailor.utils.ArgumentParserException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Performs static analysis on Swift source files.
 */
public class Tailor {

    private static ArgumentParser argumentParser = new ArgumentParser();
    private static List<String> pathNames;

    /**
     * Prints error indicating no source file was provided, and exits.
     */
    public static void exitWithMissingSourceFileError() {
        System.err.println("Swift source file must be provided.");
        argumentParser.printHelp();
        System.exit(ExitCode.FAILURE);
    }

    /**
     * Checks environment variable SRCROOT (set by Xcode) for the top-level path to the source code and adds path to
     * pathNames.
     */
    public static void checkSrcRoot() {
        String srcRoot = System.getenv("SRCROOT");
        if (srcRoot == null || srcRoot.equals("")) {
            exitWithMissingSourceFileError();
        }
        pathNames.add(srcRoot);
    }

    /**
     * Iterate through pathNames and derive swift source files from each path.
     *
     * @return Swift file names
     * @throws IOException if path specified does not exist
     */
    public static Set<String> getSwiftSourceFiles() throws IOException {
        Set<String> filenames = new TreeSet<>();
        for (String pathName : pathNames) {
            File file = new File(pathName);
            if (file.isDirectory()) {
                Files.walk(Paths.get(pathName))
                    .filter(path -> path.toString().endsWith(".swift"))
                    .filter(path -> {
                            File tempFile = path.toFile();
                            return tempFile.isFile() && tempFile.canRead();
                        })
                    .forEach(path -> filenames.add(path.toString()));
            } else if (file.isFile() && pathName.endsWith(".swift") && file.canRead()) {
                filenames.add(pathName);
            } else {
                throw new IOException("Cannot read " + pathName);
            }
        }
        return filenames;
    }

    /**
     * Creates listeners according to the rules that are enabled.
     *
     * @param enabledRules list of enabled rules
     * @param printer      passed into listener constructors
     * @throws ArgumentParserException if listener for an enabled rule is not found
     */
    public static List<SwiftBaseListener> createListeners(Set<Rules> enabledRules, Printer printer)
        throws ArgumentParserException {
        List<SwiftBaseListener> listeners = new LinkedList<>();
        for (Rules rule : enabledRules) {
            try {
                Constructor listenerConstructor = Class.forName(rule.getClassName()).getConstructor(Printer.class);
                listeners.add((SwiftBaseListener) listenerConstructor.newInstance(printer));
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
                | InstantiationException | IllegalAccessException e) {
                throw new ArgumentParserException("Listeners were not successfully created: " + e);
            }
        }
        return listeners;
    }

    /** Runs SwiftLexer on input file to generate token stream.
     *
     * @param inputFile Lexer input
     * @return Token stream
     * @throws IOException if file cannot be opened
     * @throws ArgumentParserException if cmd line arguments cannot be parsed
     */
    public static CommonTokenStream getTokenStream(File inputFile) throws IOException, ArgumentParserException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
        if (!argumentParser.debugFlagSet()) {
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
     * @throws ArgumentParserException if an error occurs when parsing cmd line arguments
     */
    public static SwiftParser.TopLevelContext getParseTree(CommonTokenStream tokenStream)
        throws ArgumentParserException {
        SwiftParser swiftParser = new SwiftParser(tokenStream);
        if (!argumentParser.debugFlagSet()) {
            swiftParser.removeErrorListeners();
            swiftParser.addErrorListener(new ErrorListener());
        }
        return swiftParser.topLevel();
    }

    /**
     * Analyze files with SwiftLexer, SwiftParser and Listeners.
     *
     * @param filenames List of files to analyze
     * @throws ArgumentParserException if an error occurs when parsing cmd line arguments
     * @throws IOException if a file cannot be opened
     */
    public static void analyzeFiles(Set<String> filenames) throws ArgumentParserException, IOException {
        long numErrors = 0;
        MaxLengths maxLengths = argumentParser.parseMaxLengths();
        Severity maxSeverity = argumentParser.getMaxSeverity();
        ColorSettings colorSettings =
            new ColorSettings(argumentParser.shouldColorOutput(), argumentParser.shouldInvertColorOutput());
        Set<Rules> enabledRules = argumentParser.getEnabledRules();

        for (String filename : filenames) {
            File inputFile = new File(filename);
            CommonTokenStream tokenStream;
            SwiftParser.TopLevelContext tree;

            try {
                tokenStream = getTokenStream(inputFile);
                tree = getParseTree(tokenStream);
            } catch (ErrorListener.ParseException e) {
                Printer printer = new Printer(inputFile, maxSeverity, colorSettings);
                printer.printParseErrorMessage();
                continue;
            }

            try (Printer printer = new Printer(inputFile, maxSeverity, colorSettings)) {
                List<SwiftBaseListener> listeners = createListeners(enabledRules, printer);
                listeners.add(new MaxLengthListener(printer, maxLengths));
                listeners.add(new MainListener(printer, maxLengths, tokenStream));
                ParseTreeWalker walker = new ParseTreeWalker();
                for (SwiftBaseListener listener : listeners) {
                    walker.walk(listener, tree);
                }
                try (FileListener fileListener = new FileListener(printer, inputFile, maxLengths)) {
                    fileListener.verify();
                }
                CommentAnalyzer commentAnalyzer = new CommentAnalyzer(tokenStream, printer);
                commentAnalyzer.analyze();
                numErrors += printer.getNumErrorMessages();
            }
        }

        if (numErrors >= 1L) {
            // Non-zero exit status when any violation messages have Severity.ERROR, controlled by --max-severity
            System.exit(ExitCode.FAILURE);
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

            CommandLine cmd = argumentParser.parseCommandLine(args);
            if (argumentParser.shouldPrintHelp()) {
                argumentParser.printHelp();
                System.exit(ExitCode.SUCCESS);
            }

            // Exit program after configuring Xcode project
            String xcodeprojPath = argumentParser.getXcodeprojPath();
            if (xcodeprojPath != null) {
                System.exit(XcodeIntegrator.setupXcode(xcodeprojPath));
            }
            if (cmd.getArgs().length >= 1) {
                pathNames.addAll(Arrays.asList(cmd.getArgs()));
            }
            if (pathNames.size() == 0) {
                checkSrcRoot();
            }
            Set<String> filenames = getSwiftSourceFiles();
            if (filenames.size() == 0) {
                exitWithMissingSourceFileError();
            }

            analyzeFiles(filenames);
        } catch (IOException e) {
            System.err.println("Source file analysis failed. Reason: " + e.getMessage());
            System.exit(ExitCode.FAILURE);
        } catch (ParseException | ArgumentParserException e) {
            System.err.println(e.getMessage());
            argumentParser.printHelp();
            System.exit(ExitCode.FAILURE);
        }

    }

}
