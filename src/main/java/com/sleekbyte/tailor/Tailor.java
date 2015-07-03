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

/**
 * Performs static analysis on Swift source files.
 */
public class Tailor {

    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;
    private static ArgumentParser argumentParser = new ArgumentParser();

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

            String pathname = "";
            if (cmd.getArgs().length >= 1) {
                pathname = cmd.getArgs()[0];
            }
            if (cmd.getArgs().length < 1 || pathname.isEmpty()) {
                System.err.println("Swift source file must be provided.");
                argumentParser.printHelp();
                System.exit(EXIT_FAILURE);
            }

            MaxLengths maxLengths = argumentParser.parseMaxLengths();
            Severity maxSeverity = argumentParser.getMaxSeverity();

            File inputFile = new File(pathname);
            FileInputStream inputStream = new FileInputStream(inputFile);
            SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
            CommonTokenStream stream = new CommonTokenStream(lexer);
            SwiftParser swiftParser = new SwiftParser(stream);
            SwiftParser.TopLevelContext tree = swiftParser.topLevel();

            final long numErrors;

            try (Printer printer = new Printer(inputFile, maxSeverity)) {
                MainListener listener = new MainListener(printer, maxLengths);
                ParseTreeWalker walker = new ParseTreeWalker();
                walker.walk(listener, tree);

                FileListener fileListener = new FileListener(printer, inputFile, maxLengths);
                fileListener.verify();

                numErrors = printer.getNumErrorMessages();
            }

            // Non-zero exit status when any violation messages have Severity.ERROR, controlled by --max-severity
            if (numErrors >= 1L) {
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
