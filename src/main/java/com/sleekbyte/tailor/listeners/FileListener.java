package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.SourceFileUtil;

import java.io.File;
import java.io.IOException;

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

    public void verify() throws IOException {
        this.verifyFileLength(maxLengths.maxFileLength);
    }

    private void verifyFileLength(int maxLines) throws IOException {
        if (SourceFileUtil.fileTooLong(inputFile, maxLines)) {
            String lengthVersusLimit = " (" + SourceFileUtil.numLinesInFile(inputFile) + "/" + maxLines + ")";
            // Mark error on first character of next line
            // TODO: Use printer method without column
            Location location = new Location(maxLines + 1, 1);
            this.printer.error(Messages.FILE + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

}
