package com.sleekbyte.tailor;

import com.sleekbyte.tailor.antlr.SwiftLexer;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.listeners.FileListener;
import com.sleekbyte.tailor.listeners.MainListener;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Performs static analysis on Swift source files.
 */
public class Tailor {

    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;
    private static ArgumentParser argumentParser = new ArgumentParser();

    public static void printMissingSourceFileError() {
        System.err.println("Swift source file must be provided.");
        argumentParser.printHelp();
        System.exit(EXIT_FAILURE);
    }

    public static void checkForSRCROOT(ArrayList<String> pathnames) {
        if (pathnames.size() == 0) {
            String srcRoot = System.getenv("SRCROOT");
            if (srcRoot == null || srcRoot.equals("")) {
                printMissingSourceFileError();
            }
            else {
                pathnames = new ArrayList<>();
                pathnames.add(srcRoot);
            }
        }
    }

    public static Set<String> getSwiftSourceFiles(ArrayList<String> pathnames) throws IOException {
        Set<String> filenames = new TreeSet<String>();
        for (String pathname: pathnames) {
            if (pathname.endsWith(".swift")) {
                filenames.add(pathname);
            }
            if (new File(pathname).isDirectory()) {
                Files.walk(Paths.get(pathname))
                    .filter((Path path) -> path.toString().endsWith(".swift"))
                    .forEach((Path path) -> filenames.add(path.toString()));
            }
        }
        return filenames;
    }

    /**
     * Main runner for Tailor.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        try {

            CommandLine cmd = argumentParser.parseCommandLine(args);
            if (argumentParser.shouldPrintHelp()) {
                argumentParser.printHelp();
                System.exit(EXIT_SUCCESS);
            }

            ArrayList<String> pathnames = new ArrayList<>();
            if (cmd.getArgs().length >= 1) {
                pathnames = new ArrayList<>(Arrays.asList(cmd.getArgs()));
            }

            if (pathnames.size() == 0) {
                checkForSRCROOT(pathnames);
            }

            Set<String> filenames = getSwiftSourceFiles(pathnames);

            if (filenames.size() == 0) {
                printMissingSourceFileError();
            }

//            filenames.stream().forEach((String name) -> System.out.println(name));

            long numErrors = 0;
            MaxLengths maxLengths = argumentParser.parseMaxLengths();
            Severity maxSeverity = argumentParser.getMaxSeverity();

            for (String filename: filenames) {
                File inputFile = new File(filename);
                FileInputStream inputStream = new FileInputStream(inputFile);
                SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
                CommonTokenStream stream = new CommonTokenStream(lexer);
                SwiftParser swiftParser = new SwiftParser(stream);
                SwiftParser.TopLevelContext tree = swiftParser.topLevel();

                try (Printer printer = new Printer(inputFile, maxSeverity)) {
                    MainListener listener = new MainListener(printer, maxLengths);
                    ParseTreeWalker walker = new ParseTreeWalker();
                    walker.walk(listener, tree);
                    FileListener fileListener = new FileListener(printer, inputFile, maxLengths);
                    fileListener.verify();
                    numErrors += printer.getNumErrorMessages();
                }
            }

            if (numErrors >= 1L) {
                // Non-zero exit status when any violation messages have Severity.ERROR, controlled by --max-severity
                System.exit(EXIT_FAILURE);
            }

        } catch (IOException e) {
            System.err.println("Source file analysis failed. Reason: " + e.getMessage());
            System.exit(EXIT_FAILURE);
        } catch (ParseException | ArgumentParserException e) {
            System.err.println(e.getMessage());
            argumentParser.printHelp();
            System.exit(EXIT_FAILURE);
        }
    }

}
