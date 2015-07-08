package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Severity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates and outputs formatted analysis messages for Xcode.
 */
public class Printer implements AutoCloseable {

    private File inputFile;
    private Severity maxSeverity;
    private Map<String, ViolationMessage> msgBuffer = new HashMap<>();

    public Printer(File inputFile, Severity maxSeverity) {
        this.inputFile = inputFile;
        this.maxSeverity = maxSeverity;
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
        ViolationMessage violationMessage = new ViolationMessage(location.line, location.column, severity, msg);
        try {
            violationMessage.setFilePath(this.inputFile.getCanonicalPath());
        } catch (IOException e) {
            System.err.println("Error in getting canonical path of input file: " + e.getMessage());
        }
        this.msgBuffer.put(violationMessage.toString(), violationMessage);
    }

    static String getHeader(File inputFile) {
        return String.format("%n********** %s **********", inputFile.toString());
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, Severity severity, String msg) {
        return new ViolationMessage(filePath, line, 0, severity, msg).toString();
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, int column, Severity severity,
                                                String msg) {
        return new ViolationMessage(filePath, line, column, severity, msg).toString();
    }

    @Override
    public void close() {
        List<ViolationMessage> outputList = new ArrayList<>(this.msgBuffer.values());
        Collections.sort(outputList);
        if (outputList.size() > 0) {
            System.out.println(getHeader(inputFile));
        }
        outputList.forEach(System.out::println);
        this.msgBuffer.clear();
    }

    public long getNumErrorMessages() {
        return msgBuffer.values().stream().filter(msg -> msg.getSeverity().equals(Severity.ERROR)).count();
    }

}
