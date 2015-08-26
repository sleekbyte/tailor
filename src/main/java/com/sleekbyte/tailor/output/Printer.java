package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Severity;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Generates and outputs formatted analysis messages for Xcode.
 */
public class Printer implements AutoCloseable {

    private File inputFile;
    private Severity maxSeverity;
    private Map<String, ViolationMessage> msgBuffer = new HashMap<>();
    private Set<Integer> ignoredLineNumbers = new HashSet<>();
    private boolean colorOutput = false;
    private int highestLineNumber = 0;
    private int highestColumnNumber = 0;
    private boolean invertColorOutput = false;

    /**
     * Constructs a printer for the specified input file, maximum severity, and color setting.
     *
     * @param inputFile the source file to verify
     * @param maxSeverity the maximum severity of any emitted violation messages
     * @param colorOutput a flag to indicate whether to color console output
     */
    public Printer(File inputFile, Severity maxSeverity, boolean colorOutput) {
        this.inputFile = inputFile;
        this.maxSeverity = maxSeverity;
        this.colorOutput = colorOutput;
    }

    /**
     * Prints warning message.
     *
     * @param warningMsg warning message to print
     * @param location   location object containing line and column number for printing
     */
    public void warn(String warningMsg, Location location) {
        print(Severity.WARNING, warningMsg, location);
    }

    /**
     * Prints error message.
     *
     * @param errorMsg error message to print
     * @param location location object containing line and column number for printing
     */
    public void error(String errorMsg, Location location) {
        print(Severity.min(Severity.ERROR, maxSeverity), errorMsg, location);
    }

    private void print(Severity severity, String msg, Location location) {
        if (location.line > this.highestLineNumber) {
            this.highestLineNumber = location.line;
        }
        if (location.column > this.highestColumnNumber) {
            this.highestColumnNumber = location.column;
        }

        ViolationMessage violationMessage = new ViolationMessage(location.line, location.column, severity, msg);
        try {
            violationMessage.setFilePath(this.inputFile.getCanonicalPath());
        } catch (IOException e) {
            System.err.println("Error in getting canonical path of input file: " + e.getMessage());
        }
        this.msgBuffer.put(violationMessage.toString(), violationMessage);
    }

    static String getHeader(File inputFile, boolean colorOutput, boolean invertColorOutput) {
        if (colorOutput) {
            String textColor = invertColorOutput ? "white" : "black";
            return String.format("%n@|bg_blue," + textColor + " **********|@ @|bg_green," + textColor
                    + " %s|@ @|bg_blue," + textColor + " **********|@", inputFile.toString());
        } else {
            return String.format("%n********** %s **********", inputFile.toString());
        }
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, Severity severity, String msg) {
        return new ViolationMessage(filePath, line, 0, severity, msg).toString();
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, int column, Severity severity, String msg) {
        return new ViolationMessage(filePath, line, column, severity, msg).toString();
    }

    @Override
    public void close() {
        List<ViolationMessage> outputList = new ArrayList<>(this.msgBuffer.values().stream()
            .filter(msg -> !ignoredLineNumbers.contains(msg.getLineNumber())).collect(Collectors.toList()));
        Collections.sort(outputList);
        if (outputList.size() > 0) {
            String header = getHeader(inputFile, colorOutput, invertColorOutput);
            if (colorOutput) {
                AnsiConsole.out.println(Ansi.ansi().render(header));
            } else {
                System.out.println(header);
            }
        }
        if (colorOutput) {
            for (ViolationMessage output : outputList) {
                output.setColorOutput(colorOutput);
                if (invertColorOutput) {
                    output.invertColorOutput();
                }
                output.setLineNumberWidth(String.valueOf(highestLineNumber).length());
                output.setColumnNumberWidth(String.valueOf(highestColumnNumber).length());
                AnsiConsole.out.println(Ansi.ansi().render(output.toString()));
            }
        } else {
            outputList.forEach(System.out::println);
        }
        this.msgBuffer.clear();
    }

    /**
     * Print error message to indicate parse failure.
     */
    public void printParseErrorMessage() {
        String header = getHeader(inputFile, colorOutput, invertColorOutput);
        if (colorOutput) {
            AnsiConsole.out.println(Ansi.ansi().render(header));
        } else {
            System.out.println(header);
        }
        System.out.println(inputFile + " could not be parsed successfully, skipping...");
    }

    public long getNumErrorMessages() {
        return msgBuffer.values().stream().filter(msg -> msg.getSeverity().equals(Severity.ERROR)).count();
    }

    public void ignoreLine(int ignoredLineNumber) {
        this.ignoredLineNumbers.add(ignoredLineNumber);
    }

    public void setInvertColorOutput(boolean invertColorOutput) {
        this.invertColorOutput = invertColorOutput;
    }

}
