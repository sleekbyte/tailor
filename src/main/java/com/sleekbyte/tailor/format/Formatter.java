package com.sleekbyte.tailor.format;

import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.ViolationMessage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Formatter used to display violation messages.
 * New formats can be added by extending this abstract base class.
 */
public abstract class Formatter {

    protected ColorSettings colorSettings;

    public Formatter(ColorSettings colorSettings) {
        this.colorSettings = colorSettings;
    }

    /**
     * Print all violation messages for a given file to the console.
     *
     * @param violationMessages list of violation messages to print
     * @throws IOException if canonical path could not be retrieved from the inputFile
     */
    public abstract void displayViolationMessages(List<ViolationMessage> violationMessages, File inputFile)
        throws IOException;

    /**
     * Print a message to the console indicating that the file failed to be parsed.
     *
     * @throws IOException if canonical path could not be retrieved from the inputFile
     */
    public abstract void displayParseErrorMessage(File inputFile) throws IOException;

    /**
     * Print a message to the console stating the analysis and violation statistics for a given number of files.
     *
     * @param numFiles number of files to be analyzed
     * @param numSkipped number of files that could not be parsed successfully
     * @param numErrors number of errors detected during analysis
     * @param numWarnings number of warnings detected during analysis
     * @throws IOException if an I/O error occurs
     */
    public abstract void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings)
        throws IOException;

    /**
     * Determine an appropriate exit code for the application, depending on the number of errors and warnings found.
     *
     * @param numErrors number of errors detected during analysis
     * @return the ExitCode reflecting the application's status determined by the results of the analysis run
     */
    public ExitCode getExitStatus(long numErrors) {
        if (numErrors >= 1L) {
            return ExitCode.FAILURE;
        }
        return ExitCode.SUCCESS;
    }

    /**
     * Print progress info to the console if the format allows.
     *
     * @param str the string to print
     */
    public void printProgressInfo(String str) {
        System.out.print(str);
    }

    /**
     * Generate the canonical summary string indicating the number of files analyzed and skipped, with violation info.
     *
     * @param numFiles number of files to be analyzed
     * @param numSkipped number of files that could not be parsed successfully
     * @param numErrors number of errors detected during analysis
     * @param numWarnings number of warnings detected during analysis
     * @return String representation of the canonical summary message
     */
    public static String formatSummary(long numFiles, long numSkipped, long numErrors, long numWarnings) {
        long numFilesAnalyzed = numFiles - numSkipped;
        long numViolations = numErrors + numWarnings;
        return String.format("%nAnalyzed %s, skipped %s, and detected %s (%s, %s).%n",
            pluralize(numFilesAnalyzed, Messages.FILE_KEY, Messages.FILES_KEY),
            pluralize(numSkipped, Messages.FILE_KEY, Messages.FILES_KEY),
            pluralize(numViolations, Messages.SINGLE_VIOLATION_KEY, Messages.MULTI_VIOLATIONS_KEY),
            pluralize(numErrors, Messages.ERROR, Messages.ERRORS_KEY),
            pluralize(numWarnings, Messages.WARNING, Messages.WARNINGS_KEY)
        );
    }

    public static String pluralize(long number, String singular, String plural) {
        return String.format("%d %s", number, number == 1 ? singular : plural);
    }

}
