package com.sleekbyte.tailor;

import com.sleekbyte.tailor.antlr.SwiftLexer;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.listeners.MainListener;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Tailor {

    public static void main(String[] args) {
        Options options = new Options();
        CommandLineParser cmdParser = new DefaultParser();
        try {

            CommandLine cmd = cmdParser.parse(options, args);
            File inputFile = new File(cmd.getArgs()[0]);
            FileInputStream inputStream = new FileInputStream(inputFile);
            SwiftLexer lexer = new SwiftLexer(new ANTLRInputStream(inputStream));
            CommonTokenStream stream = new CommonTokenStream(lexer);
            SwiftParser swiftParser = new SwiftParser(stream);
            SwiftParser.TopLevelContext tree = swiftParser.topLevel();
            Printer printer = new Printer(inputFile);

            MaxLengths maxLengths = new MaxLengths();
            maxLengths.setMaxClassLength(0);
            maxLengths.setMaxClosureLength(0);
            maxLengths.setMaxFileLength(0);
            maxLengths.setMaxFunctionLength(0);
            maxLengths.setMaxLineLength(0);
            maxLengths.setMaxNameLength(0);
            maxLengths.setMaxStructLength(0);

            MainListener listener = new MainListener(printer, maxLengths);
            ParseTreeWalker walker = new ParseTreeWalker();

            walker.walk(listener, tree);

        } catch (ParseException | IOException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        }
    }

}
