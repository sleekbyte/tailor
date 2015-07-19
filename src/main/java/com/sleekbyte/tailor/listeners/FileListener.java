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
import java.util.Map;
import java.util.Set;

/**
 * Listener for verifying source files.
 */
public class FileListener {

    private Printer printer;
    private File inputFile;
    private MaxLengths maxLengths;

    /**
     * Constructs a file listener with the specified printer, input file, and max lengths restrictions.
     *
     * @param printer    the printer to use for displaying violation messages
     * @param inputFile  the source file to verify
     * @param maxLengths the restrictions for maximum lengths
     */
    public FileListener(Printer printer, File inputFile, MaxLengths maxLengths) {
        this.printer = printer;
        this.inputFile = inputFile;
        this.maxLengths = maxLengths;
    }

    /**
     * Verify that all source file specific rules are satisfied.
     */
    public void verify() throws IOException {
        verifyFileLength(maxLengths.maxFileLength);
        verifyLineLengths(maxLengths.maxLineLength);
        verifyNewlineTerminated();
        verifyNoLeadingWhitespace();
        verifyTrailingWhitespace();
    }

    private void verifyFileLength(int maxLines) throws IOException {
        if (SourceFileUtil.fileTooLong(this.inputFile, maxLines)) {
            String lengthVersusLimit = " (" + SourceFileUtil.numLinesInFile(this.inputFile) + "/" + maxLines + ")";
            // Mark error on first line beyond limit
            Location location = new Location(maxLines + 1);
            this.printer.error(Messages.FILE + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    private void verifyLineLengths(int maxLineLength) throws IOException {
        // Map<lineNumber, lineLength>
        Set<Map.Entry<Integer, Integer>> longLines =
            SourceFileUtil.linesTooLong(this.inputFile, maxLineLength).entrySet();

        for (Map.Entry<Integer, Integer> entry : longLines) {
            String lengthVersusLimit = " (" + entry.getValue() + "/" + maxLineLength + ")";
            // Mark error on first character beyond limit
            Location location = new Location(entry.getKey(), maxLineLength + 1);
            this.printer.error(Messages.LINE + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }

    private void verifyNewlineTerminated() throws IOException {
        if (!SourceFileUtil.singleNewlineTerminated(this.inputFile)) {
            Location location = new Location(SourceFileUtil.numLinesInFile(this.inputFile));
            this.printer.error(Messages.FILE + Messages.NEWLINE_TERMINATOR, location);
        }
    }

    private void verifyNoLeadingWhitespace() throws IOException {
        if (SourceFileUtil.hasLeadingWhitespace(this.inputFile)) {
            Location location = new Location(1, 1);
            this.printer.warn(Messages.FILE + Messages.LEADING_WHITESPACE, location);
        }
    }

    private void verifyTrailingWhitespace() throws IOException {
        LineNumberReader reader = new LineNumberReader(Files.newBufferedReader(inputFile.toPath()));
        char lastCharacter;

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (line.length() > 0) {
                lastCharacter = line.charAt(line.length() - 1);
                if (Character.isWhitespace(lastCharacter)) {
                    Location location = new Location(reader.getLineNumber(), line.length());
                    this.printer.warn(Messages.LINE + Messages.TRAILING_WHITESPACE, location);
                }
            }
        }
        reader.close();
    }

}
