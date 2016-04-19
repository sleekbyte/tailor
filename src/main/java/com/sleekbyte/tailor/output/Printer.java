package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.format.Formatter;
import com.sleekbyte.tailor.utils.Pair;
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
public final class Printer implements Comparable<Printer> {

    private File inputFile;
    private Severity maxSeverity;
    private Formatter formatter;
    private Map<String, ViolationMessage> msgBuffer = new HashMap<>();
    private Set<Pair<Integer, Integer>> ignoredRegions = new HashSet<>();
    private boolean shouldPrintParseErrorMessage = false;

    /**
     * Constructs a printer for the specified input file, maximum severity, and color setting.
     *
     * @param inputFile The source file to verify
     * @param maxSeverity The maximum severity of any emitted violation messages
     * @param formatter Format to print in
     */
    public Printer(File inputFile, Severity maxSeverity, Formatter formatter) {
        this.inputFile = inputFile;
        this.maxSeverity = maxSeverity;
        this.formatter = formatter;
    }

    /**
     * Prints warning message.
     *
     * @param rule Rule associated with warning
     * @param warningMsg Warning message to print
     * @param location Location object containing line and column number for printing
     */
    public void warn(Rules rule, String warningMsg, Location location) {
        print(rule, Severity.WARNING, warningMsg, location);
    }

    // Use this method to print non rule based messages.
    public void warn(String warningMsg, Location location) {
        print(Severity.WARNING, warningMsg, location);
    }

    /**
     * Prints error message.
     *
     * @param rule Rule associated with error
     * @param errorMsg Error message to print
     * @param location Location object containing line and column number for printing
     */
    public void error(Rules rule, String errorMsg, Location location) {
        print(rule, Severity.min(Severity.ERROR, maxSeverity), errorMsg, location);
    }

    // Visible for testing only
    public static String genOutputStringForTest(Rules rule, String filePath, int line, Severity severity, String msg) {
        return new ViolationMessage(rule, filePath, line, 0, severity, msg).toString();
    }

    // Visible for testing only
    public static String genOutputStringForTest(String filePath, int line, int column, Severity severity, String msg) {
        return new ViolationMessage(filePath, line, column, severity, msg).toString();
    }

    // Visible for testing only
    public static String genOutputStringForTest(Rules rule, String filePath, int line, int column, Severity severity,
                                                String msg) {
        return new ViolationMessage(rule, filePath, line, column, severity, msg).toString();
    }

    public List<ViolationMessage> getViolationMessages() {
        return new ArrayList<>(this.msgBuffer.values());
    }

    /**
     * Calls formatter to display all violation or error messages.
     *
     * @throws IOException if formatter cannot retrieve canonical path from inputFile
     */
    public void printAllMessages() throws IOException {
        if (shouldPrintParseErrorMessage) {
            printParseErrorMessage();
        } else {
            List<ViolationMessage> outputList = getViolationMessages().stream()
                .filter(this::shouldDisplayViolationMessage).collect(Collectors.toList());

            Collections.sort(outputList);
            formatter.displayViolationMessages(outputList, inputFile);
        }
    }

    public long getNumErrorMessages() {
        return getNumMessagesWithSeverity(Severity.ERROR);
    }

    public long getNumWarningMessages() {
        return getNumMessagesWithSeverity(Severity.WARNING);
    }

    /**
     * Suppress analysis output for a given region.
     *
     * @param start line number where the region begins
     * @param end line number where the region ends
     */
    public void ignoreRegion(int start, int end) {
        this.ignoredRegions.add(new Pair<>(start, end));
    }

    public void setShouldPrintParseErrorMessage(boolean shouldPrintError) {
        shouldPrintParseErrorMessage = shouldPrintError;
    }

    /**
     * Print all rules along with their descriptions to STDOUT.
     */
    public static void printRules() {
        Rules[] rules = Rules.values();

        AnsiConsole.out.println(Ansi.ansi().render(String.format("@|bold %d rules available|@%n", rules.length)));
        for (Rules rule : rules) {
            AnsiConsole.out.println(Ansi.ansi().render(String.format("@|bold %s|@%n"
                + "@|underline Description:|@ %s%n"
                + "@|underline Style Guide:|@ %s%n", rule.getName(), rule.getDescription(), rule.getLink())));
        }
    }

    @Override
    public int compareTo(Printer printer) {
        return this.inputFile.compareTo(printer.inputFile);
    }

    @Override
    public boolean equals(Object printerObject) {
        if (!(printerObject instanceof Printer)) {
            return false;
        }
        return this.inputFile.equals(((Printer) printerObject).inputFile);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 100;
    }

    private void print(Severity severity, String msg, Location location) {
        ViolationMessage violationMessage = new ViolationMessage(location.line, location.column, severity, msg);
        addToMsgBuffer(violationMessage);
    }

    private void print(Rules rule, Severity severity, String msg, Location location) {
        ViolationMessage violationMessage = new ViolationMessage(rule, location.line, location.column, severity, msg);
        addToMsgBuffer(violationMessage);
    }

    private void addToMsgBuffer(ViolationMessage violationMessage) {
        try {
            violationMessage.setFilePath(this.inputFile.getCanonicalPath());
        } catch (IOException e) {
            System.err.println("Error in getting canonical path of input file: " + e.getMessage());
        }
        this.msgBuffer.put(violationMessage.toString(), violationMessage);
    }

    private void printParseErrorMessage() throws IOException {
        formatter.displayParseErrorMessage(inputFile);
    }

    private long getNumMessagesWithSeverity(Severity severity) {
        return msgBuffer.values().stream()
            .filter(this::shouldDisplayViolationMessage)
            .filter(msg -> msg.getSeverity().equals(severity)).count();
    }

    private boolean shouldDisplayViolationMessage(ViolationMessage msg) {
        for (Pair<Integer, Integer> ignoredRegion : ignoredRegions) {
            if (ignoredRegion.getFirst() <= msg.getLineNumber()
                && msg.getLineNumber() <= ignoredRegion.getSecond()) {
                return false;
            }
        }
        return true;
    }
}
