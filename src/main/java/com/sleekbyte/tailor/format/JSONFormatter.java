package com.sleekbyte.tailor.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.ViolationMessage;
import com.sleekbyte.tailor.output.ViolationMessage.ViolationJSON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Formatter that displays violation messages in an Xcode compatible format.
 */
public final class JSONFormatter implements Formatter {

    private File inputFile;
    private ColorSettings colorSettings;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public JSONFormatter(File inputFile, ColorSettings colorSettings) {
        this.inputFile = inputFile;
        this.colorSettings = colorSettings;
    }

    @Override
    public void displayViolationMessages(List<ViolationMessage> violationMessages) throws IOException {
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.PATH, inputFile.getCanonicalPath());

        List<ViolationJSON> violations =
            violationMessages.stream().map(ViolationMessage::toJSON).collect(Collectors.toList());
        output.put(Messages.VIOLATIONS, violations);
        output.put(Messages.PARSED, true);

        System.out.println(gson.toJson(output));
    }

    @Override
    public void displayParseErrorMessage() throws IOException {
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.PATH, inputFile.getCanonicalPath());

        output.put(Messages.VIOLATIONS, new ArrayList<>());
        output.put(Messages.PARSED, false);

        System.out.println(gson.toJson(output));
    }

    /**
     * Primitive representation of summary for JSON serialization.
     */
    public static class SummaryJSON {
        private long analyzed;
        private long skipped;
        private long violations;
        private long errors;
        private long warnings;

        /**
         * Construct a primitive representation of a summary.
         * @param numAnalyzed files successfully analyzed
         * @param numSkipped files skipped due to parse failures
         * @param numViolations total violations found
         * @param numErrors errors found
         * @param numWarnings warnings found
         */
        public SummaryJSON(long numAnalyzed, long numSkipped, long numViolations, long numErrors, long numWarnings) {
            this.analyzed = numAnalyzed;
            this.skipped = numSkipped;
            this.violations = numViolations;
            this.errors = numErrors;
            this.warnings = numWarnings;
        }
    }

    @Override
    public void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings) {
        long numFilesAnalyzed = numFiles - numSkipped;
        long numViolations = numErrors + numWarnings;

        SummaryJSON summary = new SummaryJSON(numFilesAnalyzed, numSkipped, numViolations, numErrors, numWarnings);
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.SUMMARY, summary);

        System.out.println(gson.toJson(output));
    }

    @Override
    public ExitCode getExitStatus(long numErrors, long numWarnings) {
        return ExitCode.SUCCESS;
    }

}
