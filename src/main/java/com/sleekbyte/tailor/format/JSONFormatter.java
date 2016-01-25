package com.sleekbyte.tailor.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.SummaryJSON;
import com.sleekbyte.tailor.output.ViolationJSON;
import com.sleekbyte.tailor.output.ViolationMessage;

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

        prettyPrintJSON(output);
    }

    @Override
    public void displayParseErrorMessage() throws IOException {
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.PATH, inputFile.getCanonicalPath());

        output.put(Messages.VIOLATIONS, new ArrayList<>());
        output.put(Messages.PARSED, false);

        prettyPrintJSON(output);
    }

    @Override
    public void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings) {
        long numFilesAnalyzed = numFiles - numSkipped;
        long numViolations = numErrors + numWarnings;

        SummaryJSON summary = new SummaryJSON(numFilesAnalyzed, numSkipped, numViolations, numErrors, numWarnings);
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.SUMMARY, summary);

        prettyPrintJSON(output);
    }

    @Override
    public ExitCode getExitStatus(long numErrors, long numWarnings) {
        return ExitCode.SUCCESS;
    }

    private void prettyPrintJSON(Map<String, Object> output) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        System.out.println(gson.toJson(output));
    }
}
