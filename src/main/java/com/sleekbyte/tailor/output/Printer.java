package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;

import java.io.File;
import java.io.IOException;

/**
 * Generates and outputs formatted analysis messages for Xcode
 */
public class Printer {

    private File inputFile;

    public Printer(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Prints warning message
     * @param warningMsg warning message to print
     * @param location location object containing line and column number for printing
     */
    public void warn(String warningMsg, Location location) {
        // TODO: Extract string to Messages
        print("warning", warningMsg, location);
    }

    /**
     * Prints error message
     * @param errorMsg error message to print
     * @param location location object containing line and column number for printing
     */
    public void error(String errorMsg, Location location) {
        // TODO: Extract string to Messages
        print("error", errorMsg, location);
    }

    private void print(String classification, String msg, Location location) {
        String outputString = "";
        try {
            outputString = inputFile.getCanonicalPath() + ":" + location.line + ":" + location.column + ": " +
                classification + ": " + msg;
        } catch (IOException e) {
            System.err.println("Error in getting canonical path of input file: " + e.getMessage());
        }
        System.out.println(outputString);
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, String msg, int line, int column,
                                                String classification) {
        return filePath + ":" + line + ":" + column + ": " + classification + ": " + msg;
    }
}
