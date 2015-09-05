package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.SourceFileUtil;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.util.Set;

/**
 * Listener for verifying source files.
 */
public class FileListener implements AutoCloseable {

    private static final String DISABLE_PATTERN = "// tailor:disable";
    private Printer printer;
    private File inputFile;
    private MaxLengths maxLengths;
    private LineNumberReader reader;
    private int numOfLines = 0;
    private Set<Rules> enabledRules;

    /**
     * Constructs a file listener with the specified printer, input file, and max lengths restrictions.
     *
     * @param printer    the printer to use for displaying violation messages
     * @param inputFile  the source file to verify
     * @param maxLengths the restrictions for maximum lengths
     */
    public FileListener(Printer printer, File inputFile, MaxLengths maxLengths, Set<Rules> enabledRules)
        throws IOException {
        this.printer = printer;
        this.inputFile = inputFile;
        this.maxLengths = maxLengths;
        this.reader = new LineNumberReader(Files.newBufferedReader(inputFile.toPath()));
        this.enabledRules = enabledRules;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    /**
     * Verify that all source file specific rules are satisfied.
     */
    public void verify() throws IOException {
        int lineLength;
        int lineNumber;
        for (String line = this.reader.readLine(); line != null; line = this.reader.readLine()) {
            lineLength = line.length();
            lineNumber = this.reader.getLineNumber();
            // Count the number of lines in a file
            this.numOfLines++;

            // Suppress all violations on lines ending with the given pattern
            if (line.trim().endsWith(DISABLE_PATTERN)) {
                this.printer.ignoreLine(lineNumber);
            }

            if (SourceFileUtil.lineTooLong(lineLength, this.maxLengths.maxLineLength)) {
                lineLengthViolation(lineNumber, lineLength);
            }

            if (enabledRules.contains(Rules.TRAILING_WHITESPACE)
                    && SourceFileUtil.lineHasTrailingWhitespace(lineLength, line)) {
                trailingWhitespaceViolation(lineNumber, lineLength);
            }
        }

        verifyFileLength();
        if (enabledRules.contains(Rules.TERMINATING_NEWLINE)) {
            verifyNewlineTerminated();
        }
        if (enabledRules.contains(Rules.LEADING_WHITESPACE)) {
            verifyNoLeadingWhitespace();
        }
    }

    private void lineLengthViolation(int lineNumber, int lineLength) {
        String lengthVersusLimit = " (" + lineLength + "/" + this.maxLengths.maxLineLength + ")";
        // Mark error on first character beyond limit
        Location location = new Location(lineNumber, this.maxLengths.maxLineLength + 1);
        this.printer.error(null, Messages.LINE + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
    }

    private void trailingWhitespaceViolation(int lineNumber, int lineLength) {
        Location location = new Location(lineNumber, lineLength);
        this.printer.warn(Rules.TRAILING_WHITESPACE, Messages.LINE + Messages.TRAILING_WHITESPACE, location);
    }

    private void verifyFileLength() {
        if (SourceFileUtil.fileTooLong(this.numOfLines, this.maxLengths.maxFileLength)) {
            String lengthVersusLimit = " (" + this.numOfLines + "/" + this.maxLengths.maxFileLength + ")";
            // Mark error on first line beyond limit
            Location location = new Location(this.maxLengths.maxFileLength + 1);
            this.printer.error(null, Messages.FILE + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    private void verifyNewlineTerminated() throws IOException {
        if (!SourceFileUtil.singleNewlineTerminated(this.inputFile)) {
            Location location = new Location(this.numOfLines);
            this.printer.error(Rules.TERMINATING_NEWLINE, Messages.FILE + Messages.NEWLINE_TERMINATOR, location);
        }
    }

    private void verifyNoLeadingWhitespace() throws IOException {
        if (SourceFileUtil.hasLeadingWhitespace(this.inputFile)) {
            Location location = new Location(1, 1);
            this.printer.warn(Rules.LEADING_WHITESPACE, Messages.FILE + Messages.LEADING_WHITESPACE, location);
        }
    }

    int getNumOfLines() {
        return numOfLines;
    }

}
