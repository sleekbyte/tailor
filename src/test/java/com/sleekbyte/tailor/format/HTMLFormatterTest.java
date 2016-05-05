package com.sleekbyte.tailor.format;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.ViolationMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public final class HTMLFormatterTest {

    private static final String WARNING_MSG = "this is a warning";
    private static final String ERROR_MSG = "this is an error";
    private static final ColorSettings colorSettings = new ColorSettings(false, false);

    protected ByteArrayOutputStream outContent;
    private File inputFile = new File("abc.swift");
    private HTMLFormatter formatter;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        formatter = new HTMLFormatter(colorSettings);
        formatter.clearFiles();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        formatter.clearFiles();
        formatter = null;
        System.setOut(null);
    }

    @Test
    public void testDisplayMessages() throws IOException {
        List<ViolationMessage> messages = new ArrayList<>();
        messages.add(new ViolationMessage(Rules.LOWER_CAMEL_CASE, inputFile.getCanonicalPath(), 10, 12,
            Severity.WARNING, WARNING_MSG));
        messages.add(new ViolationMessage(Rules.UPPER_CAMEL_CASE, inputFile.getCanonicalPath(),  11, 14,
            Severity.ERROR, ERROR_MSG));
        Collections.sort(messages);

        formatter.displayViolationMessages(messages, inputFile);
        List<Map<String, Object>> actualOutput = formatter.getFiles();
        assertEquals(expectedOutput(messages), actualOutput);
    }

    @Test
    public void testDisplayParseErrorMessage() throws IOException {
        formatter.displayParseErrorMessage(inputFile);
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.PATH_KEY, inputFile.getCanonicalPath());
        output.put(Messages.PARSED_KEY, false);
        output.put(Messages.VIOLATIONS_KEY, new ArrayList<>());
        output.put(Messages.NUM_VIOLATIONS_KEY, "0 " + Messages.MULTI_VIOLATIONS_KEY);
        List<Object> files = new ArrayList<>();
        files.add(output);
        assertEquals(files, formatter.getFiles());
    }

    @Test
    public void testDisplaySummary() throws IOException {
        final long files = 5;
        final long skipped = 1;
        final long errors = 7;
        final long warnings = 4;
        formatter.displaySummary(files, skipped, errors, warnings);

        assertThat(outContent.toString(Charset.defaultCharset().name()),
            containsString(String.format(
                "Analyzed %d files, skipped %d file, and detected %d violations (%d errors, %d warnings).",
                files - skipped, skipped, errors + warnings, errors, warnings
            ))
        );
    }

    @Test
    public void testSuccessExitStatus() {
        assertEquals(ExitCode.SUCCESS, formatter.getExitStatus(0));
    }

    @Test
    public void testFailureExitStatus() {
        assertEquals(ExitCode.FAILURE, formatter.getExitStatus(10));
    }

    private List<Object> expectedOutput(List<ViolationMessage> list) throws IOException {
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.PATH_KEY, inputFile.getCanonicalPath());
        output.put(Messages.PARSED_KEY, true);

        List<Map<String, Object>> violations = new ArrayList<>();
        for (ViolationMessage msg : list) {
            Map<String, Object> violation = new HashMap<>();
            Map<String, Object> location = new HashMap<>();
            location.put(Messages.LINE_KEY, msg.getLineNumber());
            location.put(Messages.COLUMN_KEY, msg.getColumnNumber());
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
        output.put(Messages.VIOLATIONS_KEY, violations);
        output.put(Messages.NUM_VIOLATIONS_KEY,
            Formatter.pluralize(violations.size(), Messages.SINGLE_VIOLATION_KEY, Messages.MULTI_VIOLATIONS_KEY));

        List<Object> files = new ArrayList<>();
        files.add(output);
        return files;
    }
}
