package com.sleekbyte.tailor.utils;

import org.antlr.v4.runtime.ParserRuleContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

}
