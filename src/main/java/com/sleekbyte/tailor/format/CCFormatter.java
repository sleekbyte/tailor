package com.sleekbyte.tailor.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.ViolationMessage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Formatter that displays violation messages according to the Code Climate spec.
 */
public final class CCFormatter extends Formatter {

    public static final char NULL_CHAR = '\0';
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public CCFormatter(ColorSettings colorSettings) {
        super(colorSettings);
    }

    @Override
    public void displayViolationMessages(List<ViolationMessage> violationMessages, File inputFile) throws IOException {
        for (ViolationMessage msg : violationMessages) {
            Map<String, Object> violation = new HashMap<>();
            Map<String, Object> location = new HashMap<>();
            Map<String, Object> positions = new HashMap<>();
            Map<String, Object> lines = new HashMap<>();
            Map<String, Object> begin = new HashMap<>();
            Map<String, Object> end = new HashMap<>();

            if (msg.getColumnNumber() != 0) {
                begin.put(Messages.LINE_KEY, msg.getLineNumber());
                begin.put(Messages.COLUMN_KEY, msg.getColumnNumber());
                end.put(Messages.LINE_KEY, msg.getLineNumber());
                end.put(Messages.COLUMN_KEY, msg.getColumnNumber());
                positions.put(Messages.BEGIN_KEY, begin);
                positions.put(Messages.END_KEY, end);
                location.put(Messages.POSITIONS_KEY, positions);
            } else {
                lines.put(Messages.BEGIN_KEY, msg.getLineNumber());
                lines.put(Messages.END_KEY, msg.getLineNumber());
                location.put(Messages.LINES_KEY, lines);
            }

            violation.put(Messages.TYPE_KEY, Messages.ISSUE_VALUE);

            violation.put(Messages.CHECK_NAME_KEY, msg.getRule().getName());

            violation.put(Messages.DESCRIPTION_KEY, msg.getMessage());

            Map<String, Object> content = new HashMap<>();
            content.put(Messages.BODY_KEY, msg.getRule().getExamples());
            violation.put(Messages.CONTENT_KEY, content);

            List<String> categories = new ArrayList<>();
            categories.add(msg.getRule().getCategory());
            violation.put(Messages.CATEGORIES_KEY, categories);

            location.put(Messages.PATH_KEY, inputFile.getPath());
            violation.put(Messages.LOCATION_KEY, location);

            violation.put(Messages.REMEDIATION_POINTS_KEY, msg.getRule().getRemediationPoints());

            System.out.println(GSON.toJson(violation) + NULL_CHAR);
        }
    }

    @Override
    public void displayParseErrorMessage(File inputFile) throws IOException {
        // Not Applicable to this formatter
    }

    @Override
    public void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings) {
        // Not Applicable to this formatter
    }

    @Override
    public ExitCode getExitStatus(long numErrors) {
        return ExitCode.SUCCESS;
    }

    @Override
    public void printProgressInfo(String str) {
        // Not Applicable to this formatter
    }

}
