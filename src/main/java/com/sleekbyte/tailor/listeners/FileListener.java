package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.SourceFileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Listener for verifying source files
 */
public class FileListener {

    private Printer printer;
    private File inputFile;
    private MaxLengths maxLengths;

    public FileListener(Printer printer, File inputFile, MaxLengths maxLengths) {
        this.printer = printer;
        this.inputFile = inputFile;
        this.maxLengths = maxLengths;
    }

    /**
     * Verify that all source file specific rules are satisfied
     */
    public void verify() throws IOException {
        this.verifyFileLength(maxLengths.maxFileLength);
        this.verifyLineLengths(maxLengths.maxLineLength);
    }

    private void verifyFileLength(int maxLines) throws IOException {
        if (SourceFileUtil.fileTooLong(this.inputFile, maxLines)) {
            String lengthVersusLimit = " (" + SourceFileUtil.numLinesInFile(this.inputFile) + "/" + maxLines + ")";
            // Mark error on first character of next line
            // TODO: #33: Use printer method where column is unspecified
            Location location = new Location(maxLines + 1, 1);
            this.printer.error(Messages.FILE + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    private void verifyLineLengths(int maxLineLength) throws IOException {
        // Map<lineNumber, lineLength>
        Set<Map.Entry<Integer, Integer>> longLines =
            SourceFileUtil.linesTooLong(this.inputFile, maxLineLength).entrySet();
        for (Map.Entry<Integer, Integer> entry : longLines) {
            String lengthVersusLimit = " (" + entry.getValue() + "/" + maxLineLength + ")";
            Location location = new Location(entry.getKey(), maxLineLength + 1);
            this.printer.error(Messages.LINE + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }

}
