package com.sleekbyte.tailor.format;

import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public final class CCFormatterTest {

    private static final String WARNING_MSG = "this is a warning";
    private static final String ERROR_MSG = "this is an error";
    private static final ColorSettings colorSettings = new ColorSettings(false, false);
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    protected ByteArrayOutputStream outContent;
    private File inputFile = new File("abc.swift");
    private CCFormatter formatter;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        formatter = new CCFormatter(colorSettings);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        formatter = null;
        System.setOut(null);
    }

    @Test
    public void testDisplayMessages() throws IOException {
        List<ViolationMessage> messages = new ArrayList<>();
        messages.add(new ViolationMessage(Rules.MULTIPLE_IMPORTS, inputFile.getCanonicalPath(), 1, 0,
            Severity.WARNING, WARNING_MSG));
        messages.add(new ViolationMessage(Rules.TERMINATING_SEMICOLON, inputFile.getCanonicalPath(),  1, 18,
            Severity.ERROR, ERROR_MSG));
        Collections.sort(messages);

        formatter.displayViolationMessages(messages, inputFile);
        assertEquals(expectedOutput(messages), outContent.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void testDisplayParseErrorMessage() throws IOException {
        formatter.displayParseErrorMessage(inputFile);
        assertThat(outContent.toString(Charset.defaultCharset().name()), isEmptyString());
    }

    @Test
    public void testDisplaySummary() throws IOException {
        final long files = 5;
        final long skipped = 1;
        final long errors = 7;
        final long warnings = 4;
        formatter.displaySummary(files, skipped, errors, warnings);

        assertThat(outContent.toString(Charset.defaultCharset().name()), isEmptyString());
    }

    @Test
    public void testExitStatusWithNoErrors() {
        assertEquals(ExitCode.SUCCESS, formatter.getExitStatus(0));
    }

    @Test
    public void testExitStatusWithSomeErrors() {
        assertEquals(ExitCode.SUCCESS, formatter.getExitStatus(10));
    }

    private String expectedOutput(List<ViolationMessage> list) throws IOException {
        String violations = "";

        for (ViolationMessage msg : list) {
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

            violations += GSON.toJson(violation) + CCFormatter.NULL_CHAR + System.lineSeparator();
        }

        return violations;
    }
}
