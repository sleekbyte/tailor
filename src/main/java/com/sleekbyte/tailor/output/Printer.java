package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates and outputs formatted analysis messages for Xcode
 */
public class Printer implements AutoCloseable {

    private File inputFile;
    private Map<String, ViolationMessage> msgBuffer = new HashMap<>();

    public Printer(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Prints warning message
     *
     * @param warningMsg warning message to print
     * @param location   location object containing line and column number for printing
     */
    public void warn(String warningMsg, Location location) {
        print(Messages.WARNING, warningMsg, location);
    }

    /**
     * Prints error message
     *
     * @param errorMsg error message to print
     * @param location location object containing line and column number for printing
     */
    public void error(String errorMsg, Location location) {
        print(Messages.ERROR, errorMsg, location);
    }

    private void print(String classification, String msg, Location location) {
        ViolationMessage violationMessage = new ViolationMessage(location.line, location.column, classification, msg);
        try {
            violationMessage.setFilePath(this.inputFile.getCanonicalPath());
        } catch (IOException e) {
            System.err.println("Error in getting canonical path of input file: " + e.getMessage());
        }
        this.msgBuffer.put(violationMessage.toString(), violationMessage);
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, String classification, String msg) {
        return new ViolationMessage(filePath, line, 0, classification, msg).toString();
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, int column, String classification,
                                                String msg) {
        return new ViolationMessage(filePath, line, column, classification, msg).toString();
    }

    @Override
    public void close() {
        List<ViolationMessage> outputList = new ArrayList<>(this.msgBuffer.values());
        Collections.sort(outputList);
        outputList.forEach(System.out::println);
        this.msgBuffer.clear();
    }
}
