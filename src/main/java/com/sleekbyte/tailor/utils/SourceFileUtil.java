package com.sleekbyte.tailor.utils;

import org.antlr.v4.runtime.ParserRuleContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Util class for source files
 */
public class SourceFileUtil {

    public static int numLinesInFile(File inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        int numOfLines = 0;
        while (reader.readLine() != null) {
            numOfLines++;
        }
        reader.close();
        return numOfLines;
    }

    public static boolean fileTooLong(File inputFile, int maxLength) throws IOException {
        return maxLength > 0 && numLinesInFile(inputFile) > maxLength;
    }

    public static boolean constructTooLong(ParserRuleContext ctx, int maxLength) {
        return maxLength > 0 && (ctx.getStop().getLine() - ctx.getStart().getLine()) > maxLength;
    }

    public static Map<Integer, Integer> linesTooLong(File inputFile, int maxLength) throws IOException {
        // Map<lineNumber, lineLength>
        Map<Integer, Integer> longLines = new HashMap<>();
        if (maxLength > 0) {
            LineNumberReader reader = new LineNumberReader(new FileReader(inputFile));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.length() > maxLength) {
                    longLines.put(reader.getLineNumber(), line.length());
                }
            }
        }
        return longLines;
    }

    public static boolean nameTooLong(ParserRuleContext ctx, int maxLength) {
        return maxLength > 0 && ctx.getText().length() > maxLength;
    }
}
