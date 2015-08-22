package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.SourceFileUtil;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Listener for verifying source files.
 */
public class FileListener implements AutoCloseable {

    private static final String SUPPRESS_VIOLATIONS = "// tailor:disable";
    private Printer printer;
    private File inputFile;
    private MaxLengths maxLengths;
    private LineNumberReader reader;
    private int numOfLines = 0;
    private Set<Integer> ignoredLines = new HashSet<>(); // Set<lineNumber>
    private Map<Integer, Integer> longLines = new HashMap<>(); // Map<lineNumber, lineLength>
    private Map<Integer, Integer> trailingLines = new HashMap<>(); // Map<lineNumber, lineLength>

    /**
     * Constructs a file listener with the specified printer, input file, and max lengths restrictions.
     *
     * @param printer    the printer to use for displaying violation messages
     * @param inputFile  the source file to verify
     * @param maxLengths the restrictions for maximum lengths
     */
    public FileListener(Printer printer, File inputFile, MaxLengths maxLengths) throws IOException {
        this.printer = printer;
        this.inputFile = inputFile;
        this.maxLengths = maxLengths;
        this.reader = new LineNumberReader(Files.newBufferedReader(inputFile.toPath()));
        readFile();
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    private void readFile() throws IOException {
        for (String line = this.reader.readLine(); line != null; line = this.reader.readLine()) {
            int lineLength = line.length();
            int lineNumber = this.reader.getLineNumber();
            // Counts the number of lines in a file
            this.numOfLines++;

            // Suppress all violations on lines ending with the given pattern
            if (line.endsWith(SUPPRESS_VIOLATIONS)) {
                this.ignoredLines.add(lineNumber);
            }

            // Checks for lines in a source file that are longer than the specified maximum length
            if (this.maxLengths.maxLineLength > 0 && lineLength > this.maxLengths.maxLineLength) {
                this.longLines.put(lineNumber, lineLength);
            }

            // Checks whether a file contains any trailing whitespace characters
            if (lineLength > 0 && Character.isWhitespace(line.charAt(lineLength - 1))) {
                this.trailingLines.put(lineNumber, lineLength);
            }
        }

        this.printer.ignoreLines(this.ignoredLines);
    }

    /**
     * Verify that all source file specific rules are satisfied.
     */
    public void verify() throws IOException {
        verifyFileLength(this.maxLengths.maxFileLength);
        verifyLineLengths(this.maxLengths.maxLineLength);
        verifyNewlineTerminated();
        verifyNoLeadingWhitespace();
        verifyNoTrailingWhitespace();
    }

    private void verifyFileLength(int maxLines) {
        if (SourceFileUtil.fileTooLong(this.numOfLines, maxLines)) {
            String lengthVersusLimit = " (" + this.numOfLines + "/" + maxLines + ")";
            // Mark error on first line beyond limit
            Location location = new Location(maxLines + 1);
            this.printer.error(Messages.FILE + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    private void verifyLineLengths(int maxLineLength) {
        Set<Map.Entry<Integer, Integer>> longLines = this.longLines.entrySet();
        for (Map.Entry<Integer, Integer> entry : longLines) {
            String lengthVersusLimit = " (" + entry.getValue() + "/" + maxLineLength + ")";
            // Mark error on first character beyond limit
            Location location = new Location(entry.getKey(), maxLineLength + 1);
            this.printer.error(Messages.LINE + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }

    private void verifyNewlineTerminated() throws IOException {
        if (!SourceFileUtil.singleNewlineTerminated(this.inputFile)) {
            Location location = new Location(this.numOfLines);
            this.printer.error(Messages.FILE + Messages.NEWLINE_TERMINATOR, location);
        }
    }

    private void verifyNoLeadingWhitespace() throws IOException {
        if (SourceFileUtil.hasLeadingWhitespace(this.inputFile)) {
            Location location = new Location(1, 1);
            this.printer.warn(Messages.FILE + Messages.LEADING_WHITESPACE, location);
        }
    }

    private void verifyNoTrailingWhitespace() {
        Set<Map.Entry<Integer, Integer>> trailingLines = this.trailingLines.entrySet();
        for (Map.Entry<Integer, Integer> entry : trailingLines) {
            Location location = new Location(entry.getKey(), entry.getValue());
            this.printer.warn(Messages.LINE + Messages.TRAILING_WHITESPACE, location);
        }
    }

    int getNumOfLines() {
        return numOfLines;
    }

    Map<Integer, Integer> getLongLines() {
        return longLines;
    }

    Map<Integer, Integer> getTrailingLines() {
        return trailingLines;
    }

}
