package com.sleekbyte.tailor.format;


import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.ViolationMessage;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Formatter that displays violation messages in an Xcode compatible format.
 */
public final class XcodeFormatter extends Formatter {

    public XcodeFormatter(ColorSettings colorSettings) {
        super(colorSettings);
    }

    public int getHighestLineNumber(List<ViolationMessage> violationMessages) {
        return violationMessages.stream().max(Comparator.comparing(v -> v.getLineNumber())).get().getLineNumber();
    }

    public int getHighestColumnNumber(List<ViolationMessage> violationMessages) {
        return violationMessages.stream().max(Comparator.comparing(v -> v.getColumnNumber())).get().getColumnNumber();
    }

    /**
     * Returns a file header used to demarcate violation messages from different files.
     *
     * @param inputFile The input file
     * @param colorSettings Color settings
     * @return String containing the file header
     * @throws IOException if file cannot be opened
     */
    public static String getHeader(File inputFile, ColorSettings colorSettings) throws IOException {
        if (colorSettings.colorOutput) {
            String textColor = colorSettings.invertColor ? "white" : "black";
            return String.format("%n@|bg_blue," + textColor + " **********|@ @|bg_green," + textColor
                + " %s|@ @|bg_blue," + textColor + " **********|@", inputFile.getCanonicalPath());
        } else {
            return String.format("%n********** %s **********", inputFile.toString());
        }
    }

    @Override
    public void displayViolationMessages(List<ViolationMessage> violationMessages, File inputFile) throws IOException {
        if (violationMessages.size() > 0) {
            printColoredMessage(getHeader(inputFile, colorSettings));
        }
        if (colorSettings.colorOutput) {
            for (ViolationMessage output : violationMessages) {
                output.setColorSettings(colorSettings);
                output.setLineNumberWidth(String.valueOf(getHighestLineNumber(violationMessages)).length());
                output.setColumnNumberWidth(String.valueOf(getHighestColumnNumber(violationMessages)).length());
                AnsiConsole.out.println(Ansi.ansi().render(output.toString()));
            }
        } else {
            violationMessages.forEach(System.out::println);
        }
    }

    @Override
    public void displayParseErrorMessage(File inputFile) throws IOException {
        printColoredMessage(getHeader(inputFile, colorSettings));
        System.out.println(inputFile + Messages.COULD_NOT_BE_PARSED);
    }

    @Override
    public void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings) {
        System.out.println(Formatter.formatSummary(numFiles, numSkipped, numErrors, numWarnings));
    }

    private void printColoredMessage(String msg) {
        if (colorSettings.colorOutput) {
            AnsiConsole.out.println(Ansi.ansi().render(msg));
        } else {
            System.out.println(msg);
        }
    }
}
