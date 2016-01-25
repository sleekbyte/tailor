package com.sleekbyte.tailor.format;

import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.output.ViolationMessage;

import java.io.IOException;
import java.util.List;

/**
 * Interface used by the Printer class to display violation messages/errors.
 * New formats can be added by implementing this interface.
 */
public interface Formatter {

    void displayViolationMessages(List<ViolationMessage> violationMessages) throws IOException;

    void displayParseErrorMessage() throws IOException;

    void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings);

    ExitCode getExitStatus(long numErrors, long numWarnings);

    static String pluralize(long number, String singular, String plural) {
        return String.format("%d %s", number, number == 1 ? singular : plural);
    }
}
