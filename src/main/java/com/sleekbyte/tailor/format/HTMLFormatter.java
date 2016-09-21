package com.sleekbyte.tailor.format;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ConfigProperties;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.ViolationMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Formatter that displays violation messages in valid HTML output.
 */
public final class HTMLFormatter extends Formatter {

    protected static final String TEMPLATE_PATH = "index.html";
    private static final String NEWLINE_PATTERN = "\n";
    private static final List<Map<String, Object>> FILES = new ArrayList<>();

    public HTMLFormatter(ColorSettings colorSettings) {
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
            switch (msg.getSeverity()) {
                case ERROR:
                    violation.put(Messages.ERROR, true);
                    break;
                case WARNING:
                    violation.put(Messages.WARNING, true);
                    break;
                default:
                    break;
            }
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
    public void displaySummary(long numFiles, long numSkipped, long numErrors, long numWarnings) throws IOException {
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.SUMMARY_KEY,
            Formatter.formatSummary(numFiles, numSkipped, numErrors, numWarnings).replace(NEWLINE_PATTERN, ""));
        // Sort files by descending order of the number of violations
        FILES.sort((o1, o2) ->
            ((List) o2.get(Messages.VIOLATIONS_KEY)).size() - ((List) o1.get(Messages.VIOLATIONS_KEY)).size());
        output.put(Messages.FILES_KEY, FILES);
        output.put(Messages.VERSION_LONG_OPT, new ConfigProperties().getVersion());

        InputStreamReader inputStreamReader = new InputStreamReader(
            HTMLFormatter.class.getResourceAsStream(TEMPLATE_PATH),
            Charset.defaultCharset());
        Mustache mustache = new DefaultMustacheFactory().compile(inputStreamReader, TEMPLATE_PATH);
        mustache.execute(new OutputStreamWriter(System.out, Charset.defaultCharset()), output).flush();
        inputStreamReader.close();
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
        final int numViolations = violations.size();
        output.put(Messages.NUM_VIOLATIONS_KEY,
            Formatter.pluralize(numViolations, Messages.SINGLE_VIOLATION_KEY, Messages.MULTI_VIOLATIONS_KEY)
        );
        output.put(Messages.PARSED_KEY, parsed);
        FILES.add(output);
    }

}
