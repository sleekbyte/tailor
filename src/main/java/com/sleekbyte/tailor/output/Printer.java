package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Generates and outputs formatted analysis messages for Xcode
 */
public class Printer implements AutoCloseable {

    private File inputFile;
    private Set<String> msgBuffer = new HashSet<>();

    public Printer(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Prints warning message
     * @param warningMsg warning message to print
     * @param location location object containing line and column number for printing
     */
    public void warn(String warningMsg, Location location) {
        print(Messages.WARNING, warningMsg, location);
    }

    /**
     * Prints error message
     * @param errorMsg error message to print
     * @param location location object containing line and column number for printing
     */
    public void error(String errorMsg, Location location) {
        print(Messages.ERROR, errorMsg, location);
    }

    private void print(String classification, String msg, Location location) {
        String outputString = "";
        try {
            String column = location.column != 0 ? ":" + location.column : "";
            outputString = this.inputFile.getCanonicalPath() + ":" + location.line + column + ": " +
                classification + ": " + msg;
        } catch (IOException e) {
            System.err.println("Error in getting canonical path of input file: " + e.getMessage());
        }
        this.msgBuffer.add(outputString);
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, String classification,
                                                String msg) {
        return filePath + ":" + line + ": " + classification + ": " + msg;
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, int column, String classification,
                                                String msg) {
        return filePath + ":" + line + ":" + column + ": " + classification + ": " + msg;
    }

    @Override
    public void close() {
        // TODO: #55: this.msgBuffer.sort(lineNumber1 < lineNumber2, columnNumber1 < columnNumber2);
        this.msgBuffer.forEach(System.out::println);
        this.msgBuffer.clear();
    }
}
