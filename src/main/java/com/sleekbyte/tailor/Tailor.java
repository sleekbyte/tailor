package com.sleekbyte.tailor;

import com.sleekbyte.tailor.antlr.SwiftLexer;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.listeners.FileListener;
import com.sleekbyte.tailor.listeners.MainListener;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ArgumentParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Tailor {

    public static void main(String[] args) {

        try {

            ArgumentParser argumentParser = new ArgumentParser(args);
            CommandLine cmd = argumentParser.parseCommandLine();
            String pathname = "";
            if (cmd.getArgs().length >= 1) {
                pathname = cmd.getArgs()[0];
            }
            if (cmd.getArgs().length < 1 || pathname.isEmpty()) {
                System.err.println("Swift source file must be provided.");
                argumentParser.printHelp();
                System.exit(1);
            }

            File inputFile = new File(pathname);
            FileInputStream inputStream = new FileInputStream(inputFile);
            SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
            CommonTokenStream stream = new CommonTokenStream(lexer);
            SwiftParser swiftParser = new SwiftParser(stream);
            SwiftParser.TopLevelContext tree = swiftParser.topLevel();
            Printer printer = new Printer(inputFile);

            MaxLengths maxLengths = argumentParser.parseMaxLengths();

            MainListener listener = new MainListener(printer, maxLengths);
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, tree);

            FileListener fileListener = new FileListener(printer, inputFile, maxLengths);
            fileListener.verify();

        } catch (ParseException | IOException e) {
            System.err.println("Source file analysis failed. Reason: " + e.getMessage());
            System.exit(1);
        }
    }

}
