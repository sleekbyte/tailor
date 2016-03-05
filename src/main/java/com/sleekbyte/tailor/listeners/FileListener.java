package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.ConstructLengths;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.Pair;
import com.sleekbyte.tailor.utils.SourceFileUtil;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Listener for verifying source files.
 */
public final class FileListener implements AutoCloseable {

    private static final String DISABLE_LINE_PATTERN = "// tailor:disable";
    private static final String DISABLE_REGION_BEGIN_PATTERN = "// tailor:disable-region-begin";
    private static final String DISABLE_REGION_END_PATTERN = "// tailor:disable-region-end";
    private Printer printer;
    private File inputFile;
    private ConstructLengths constructLengths;
    private LineNumberReader reader;
    private int numOfLines = 0;
    private Set<Rules> enabledRules;

    /**
     * Constructs a file listener with the specified printer, input file, and max lengths restrictions.
     *
     * @param printer    the printer to use for displaying violation messages
     * @param inputFile  the source file to verify
     * @param constructLengths the restrictions for maximum lengths
     */
    public FileListener(Printer printer, File inputFile, ConstructLengths constructLengths, Set<Rules> enabledRules)
        throws IOException {
        this.printer = printer;
        this.inputFile = inputFile;
        this.constructLengths = constructLengths;
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
        List<Pair<Integer, Integer>> ignoreRegionList = new ArrayList<>();
        Stack<Integer> ignoreBlockBeginStack = new Stack<>();
        for (String line = this.reader.readLine(); line != null; line = this.reader.readLine()) {
            lineLength = line.length();
            lineNumber = this.reader.getLineNumber();
            // Count the number of lines in a file
            this.numOfLines++;

            String trimmedLine = line.trim();
            if (trimmedLine.equals(DISABLE_REGION_BEGIN_PATTERN)) {
                ignoreBlockBeginStack.push(lineNumber);
            } else if (trimmedLine.equals(DISABLE_REGION_END_PATTERN)) {
                if (ignoreBlockBeginStack.empty()) {
                    // throw error
                }
                ignoreRegionList.add(new Pair<>(ignoreBlockBeginStack.pop(), lineNumber));
            }

            if (trimmedLine.endsWith(DISABLE_LINE_PATTERN)) {
                this.printer.ignoreLine(lineNumber);
            }

            if (enabledRules.contains(Rules.MAX_LINE_LENGTH)
                && SourceFileUtil.lineTooLong(lineLength, this.constructLengths.maxLineLength)) {
                lineLengthViolation(lineNumber, lineLength, this.constructLengths.maxLineLength, Rules.MAX_LINE_LENGTH,
                    Messages.EXCEEDS_CHARACTER_LIMIT);
            }

            if (enabledRules.contains(Rules.TRAILING_WHITESPACE)
                    && SourceFileUtil.lineHasTrailingWhitespace(lineLength, line)) {
                trailingWhitespaceViolation(lineNumber, lineLength);
            }
        }

        ignoreLinesInDisableRegion(ignoreRegionList);

        if (enabledRules.contains(Rules.MAX_FILE_LENGTH)) {
            verifyFileLength();
        }

        if (enabledRules.contains(Rules.TERMINATING_NEWLINE)) {
            verifyNewlineTerminated();
        }

        if (enabledRules.contains(Rules.LEADING_WHITESPACE)) {
            verifyNoLeadingWhitespace();
        }
    }

    private void ignoreLinesInDisableRegion(List<Pair<Integer, Integer>> ignoreRegionList) throws IOException {
        int lineNumber;
        for (String line = this.reader.readLine(); line != null; line = this.reader.readLine()) {
            lineNumber = this.reader.getLineNumber();
            System.out.println("ASDAS NUMBER: " + lineNumber);
            for (Pair<Integer, Integer> region : ignoreRegionList) {
                if (region.getFirst() < lineNumber && lineNumber < region.getSecond()) {
                    this.printer.ignoreLine(lineNumber);
                }
            }
        }
    }

    private void lineLengthViolation(int lineNumber, int lineLength, int lengthLimit,  Rules rule, String msg) {
        String lengthVersusLimit = " (" + lineLength + "/" + lengthLimit + ")";
        // Mark error on first line beyond limit
        Location location = new Location(lineNumber, lengthLimit + 1);
        this.printer.error(rule, Messages.LINE + msg + lengthVersusLimit, location);
    }

    private void trailingWhitespaceViolation(int lineNumber, int lineLength) {
        Location location = new Location(lineNumber, lineLength);
        this.printer.warn(Rules.TRAILING_WHITESPACE, Messages.LINE + Messages.TRAILING_WHITESPACE, location);
    }

    private void verifyFileLength() {
        if (SourceFileUtil.fileTooLong(this.numOfLines, this.constructLengths.maxFileLength)) {
            String lengthVersusLimit = " (" + this.numOfLines + "/" + this.constructLengths.maxFileLength + ")";
            // Mark error on first line beyond limit
            Location location = new Location(this.constructLengths.maxFileLength + 1);
            this.printer.error(Rules.MAX_FILE_LENGTH, Messages.FILE + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit,
                location);
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

    protected int getNumOfLines() {
        return numOfLines;
    }

}
