package com.sleekbyte.tailor.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.ViolationMessage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Formatter that displays violation messages in valid JSON output.
 */
public final class JSONFormatter extends Formatter {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private static final List<Map<String, Object>> FILES = new ArrayList<>();

    public JSONFormatter(ColorSettings colorSettings) {
        super(colorSettings);
    }

    protected static List<Map<String, Object>> getFiles() {
        return FILES;
    }

    protected static void clearFiles() {
        FILES.clear();
    }

    @Override
    public void displayViolationMessages(List<ViolationMessage> violationMessages, File inputFile) throws IOException {
        List<Map<String, Object>> violations = new ArrayList<>();
        for (ViolationMessage msg : violationMessages) {
            Map<String, Object> violation = new HashMap<>();
            Map<String, Integer> location = new HashMap<>();

            location.put(Messages.LINE_KEY, msg.getLineNumber());
            if (msg.getColumnNumber() != 0) {
                location.put(Messages.COLUMN_KEY, msg.getColumnNumber());
            }
            violation.put(Messages.LOCATION_KEY, location);
            violation.put(Messages.SEVERITY_KEY, msg.getSeverity().toString());
            violation.put(Messages.RULE_KEY, msg.getRule().getName());
            violation.put(Messages.MESSAGE_KEY, msg.getMessage());

            violations.add(violation);
        }
        storeMessages(violations, true, inputFile.getCanonicalPath());
    }

    @Override
    public void displayParseErrorMessage(File inputFile) throws IOException {
        storeMessages(new ArrayList<>(), false, inputFile.getCanonicalPath());
    }

    @Override
    public void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings) {
        long numFilesAnalyzed = numFiles - numSkipped;
        long numViolations = numErrors + numWarnings;

        Map<String, Long> summary = new HashMap<>();
        summary.put(Messages.ANALYZED_KEY, numFilesAnalyzed);
        summary.put(Messages.SKIPPED_KEY, numSkipped);
        summary.put(Messages.VIOLATIONS_KEY, numViolations);
        summary.put(Messages.ERRORS_KEY, numErrors);
        summary.put(Messages.WARNINGS_KEY, numWarnings);

        // Use LinkedHashMap implementation to maintain order of FILES_KEY above SUMMARY_KEY in output
        Map<String, Object> output = new LinkedHashMap<>();
        output.put(Messages.FILES_KEY, FILES);
        output.put(Messages.SUMMARY_KEY, summary);
        System.out.println(GSON.toJson(output));
    }

    @Override
    public void printProgressInfo(String str) {
        // Not Applicable to this formatter
    }

    private void storeMessages(List<Map<String, Object>> violations, boolean parsed, String filePath)
        throws IOException {
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.PATH_KEY, filePath);
        output.put(Messages.VIOLATIONS_KEY, violations);
        output.put(Messages.PARSED_KEY, parsed);
        FILES.add(output);
    }

}
