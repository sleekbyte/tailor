package com.sleekbyte.tailor.utils;

import org.antlr.v4.runtime.ParserRuleContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

/**
 * Util class for source files.
 */
public class SourceFileUtil {

    private static final String READ_ONLY_MODE = "r";
    private static final byte NEWLINE_DELIMITER = (byte) '\n';

    public static boolean fileTooLong(int numOfLines, int maxLength) {
        return maxLength > 0 && numOfLines > maxLength;
    }

    public static boolean lineTooLong(int lineLength, int maxLineLength) {
        return (maxLineLength > 0) && (lineLength > maxLineLength);
    }

    public static boolean lineHasTrailingWhitespace(int lineLength, String line) {
        return (lineLength > 0) && (Character.isWhitespace(line.charAt(lineLength - 1)));
    }

    public static boolean constructTooLong(ParserRuleContext ctx, int maxLength) {
        return maxLength > 0 && (ctx.getStop().getLine() - ctx.getStart().getLine()) > maxLength;
    }

    public static boolean nameTooLong(ParserRuleContext ctx, int maxLength) {
        return maxLength > 0 && ctx.getText().length() > maxLength;
    }

    public static boolean nameTooShort(ParserRuleContext ctx, int minLength) {
        return minLength > 0 && ctx.getText().length() < minLength;
    }

    /**
     * Checks whether a file is terminated with exactly one trailing newline.
     *
     * @param inputFile the file to check for a trailing newline
     * @return true if file is terminated with exactly one newline
     * @throws IOException if the file cannot be read
     */
    public static boolean singleNewlineTerminated(File inputFile) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(inputFile, READ_ONLY_MODE);

        // Zero terminating newlines
        if (inputFile.length() < 1) {
            return true;
        }
        randomAccessFile.seek(inputFile.length() - 1);
        if (Byte.compare(randomAccessFile.readByte(), NEWLINE_DELIMITER) != 0) {
            return false;
        }

        // File contains a single newline character and nothing else
        if (inputFile.length() < 2) {
            return true;
        }

        // More than one terminating newline
        randomAccessFile.seek(inputFile.length() - 2);
        return (Byte.compare(randomAccessFile.readByte(), NEWLINE_DELIMITER) != 0);
    }

    /**
     * Checks whether a file contains any leading whitespace characters.
     *
     * @param inputFile the file to check for leading whitespace
     * @return true if file starts with whitespace
     * @throws IOException if the file cannot be read
     */
    public static boolean hasLeadingWhitespace(File inputFile) throws IOException {
        BufferedReader reader = Files.newBufferedReader(inputFile.toPath());
        int character = reader.read();
        if (character != -1 && Character.isWhitespace((char) character)) {
            return true;
        }
        reader.close();
        return false;
    }
}
