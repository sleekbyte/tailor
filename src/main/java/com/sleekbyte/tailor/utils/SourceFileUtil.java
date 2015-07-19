package com.sleekbyte.tailor.utils;

import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Util class for source files.
 */
public class SourceFileUtil {

    private static final String READ_ONLY_MODE = "r";
    private static final byte NEWLINE_DELIMITER = (byte) '\n';
    private static File fileWithStoredLength;
    private static int storedNumOfLines;

    /**
     * Counts the number of lines in a file.
     * Opens and reads all lines of the file, then memoizes that count for the same file.
     *
     * @param inputFile the file in which to count the number of lines
     * @return the number of lines in the file
     * @throws IOException if the file cannot be read
     */
    public static int numLinesInFile(File inputFile) throws IOException {
        if (inputFile.equals(fileWithStoredLength)) {
            return storedNumOfLines;
        }

        LineNumberReader reader = new LineNumberReader(Files.newBufferedReader(inputFile.toPath()));
        int numOfLines = 0;
        while (reader.readLine() != null) {
            numOfLines++;
        }
        reader.close();

        // memoize length of inputFile
        fileWithStoredLength = inputFile;
        storedNumOfLines = numOfLines;

        return numOfLines;
    }

    public static boolean fileTooLong(File inputFile, int maxLength) throws IOException {
        return maxLength > 0 && numLinesInFile(inputFile) > maxLength;
    }

    public static boolean constructTooLong(ParserRuleContext ctx, int maxLength) {
        return maxLength > 0 && (ctx.getStop().getLine() - ctx.getStart().getLine()) > maxLength;
    }

    /**
     * Checks for lines in a source file that are longer than the specified maximum length.
     *
     * @param inputFile the file in which to count the lengths of each line
     * @param maxLength the maximum allowed length for any line
     * @return a map of line numbers to their lengths for lines exceeding the maximum allowed length
     * @throws IOException if the file cannot be read
     */
    public static Map<Integer, Integer> linesTooLong(File inputFile, int maxLength) throws IOException {
        // Map<lineNumber, lineLength>
        Map<Integer, Integer> longLines = new HashMap<>();
        if (maxLength > 0) {
            LineNumberReader reader = new LineNumberReader(Files.newBufferedReader(inputFile.toPath()));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.length() > maxLength) {
                    longLines.put(reader.getLineNumber(), line.length());
                }
            }
            reader.close();
        }
        return longLines;
    }

    public static boolean nameTooLong(ParserRuleContext ctx, int maxLength) {
        return maxLength > 0 && ctx.getText().length() > maxLength;
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
}
