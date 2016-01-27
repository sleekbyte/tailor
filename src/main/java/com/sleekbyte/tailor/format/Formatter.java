package com.sleekbyte.tailor.format;

import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.output.ViolationMessage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Formatter used by the Printer class to display violation messages.
 * New formats can be added by extending this abstract base class.
 */
public abstract class Formatter {

    protected File inputFile;
    protected ColorSettings colorSettings;

    public Formatter(File inputFile, ColorSettings colorSettings) {
        this.inputFile = inputFile;
        this.colorSettings = colorSettings;
    }

    public abstract void displayViolationMessages(List<ViolationMessage> violationMessages) throws IOException;

    public abstract void displayParseErrorMessage() throws IOException;

    public abstract void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings);

    public abstract ExitCode getExitStatus(long numErrors, long numWarnings);

    static String pluralize(long number, String singular, String plural) {
        return String.format("%d %s", number, number == 1 ? singular : plural);
    }

}
