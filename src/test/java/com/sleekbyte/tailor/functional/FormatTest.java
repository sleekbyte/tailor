package com.sleekbyte.tailor.functional;

import static org.junit.Assert.assertArrayEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sleekbyte.tailor.Tailor;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.format.Format;
import com.sleekbyte.tailor.output.Printer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Tests for {@link Tailor} output formats.
 */
@RunWith(MockitoJUnitRunner.class)
public final class FormatTest {

    protected static final String TEST_INPUT_DIR = "src/test/swift/com/sleekbyte/tailor/functional";
    protected static final String NEWLINE_REGEX = "\\r?\\n";
    protected static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    protected ByteArrayOutputStream outContent;
    protected File inputFile;
    protected List<String> expectedMessages;

    @Before
    public void setUp() throws IOException {
        inputFile = new File(TEST_INPUT_DIR + "/UpperCamelCaseTest.swift");
        expectedMessages = new ArrayList<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testXcodeFormat() throws IOException {
        Format format = Format.XCODE;

        String[] command = new String[] {
            "--format", format.getName(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };
        addAllExpectedMsgs(format);

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);

        // Skip first two lines for file header, last two lines for summary
        msgs = Arrays.copyOfRange(msgs, 4, msgs.length - 2);

        for (String msg : msgs) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), this.expectedMessages.toArray(),
            actualOutput.toArray());
    }

    @Test
    public void testJSONFormat() throws IOException {
        Format format = Format.JSON;

        String[] command = new String[] {
            "--format", format.getName(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };

        addAllExpectedMsgs(format);
        expectedMessages.addAll(Arrays.asList(getJSONSummary(1, 0, 0, 22).split(NEWLINE_REGEX)));

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);

        // Skip first three lines for path and JSON array start, three lines before summary
        String[] violations = Arrays.copyOfRange(msgs, 5, msgs.length - 12);
        String[] summary = Arrays.copyOfRange(msgs, msgs.length - 9, msgs.length);
        msgs = Stream.concat(Arrays.stream(violations), Arrays.stream(summary)).toArray(String[]::new);

        List<String> expectedOutput = new ArrayList<>();
        for (String msg : expectedMessages) {
            String trimmedMsg = msg.trim().replaceAll(",", "");
            expectedOutput.add(trimmedMsg);
        }
        for (String msg : msgs) {
            String trimmedMsg = msg.trim().replaceAll(",", "");
            actualOutput.add(trimmedMsg);
        }
        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), expectedOutput.toArray(),
            actualOutput.toArray());
    }

    protected void addAllExpectedMsgs(Format format) {
        addExpectedMsg(format, 3, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES);
        addExpectedMsg(format, 7, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES);
        addExpectedMsg(format, 24, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(format, 25, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(format, 26, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(format, 42, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(format, 43, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(format, 46, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(format, 47, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(format, 50, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(format, 55, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(format, 63, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(format, 72, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES);
        addExpectedMsg(format, 76, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES);
        addExpectedMsg(format, 90, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(format, 94, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(format, 98, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(format, 107, 10, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(format, 119, 18, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(format, 119, 23, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(format, 128, 20, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(format, 137, 14, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
    }

    private void addExpectedMsg(Format format, int line, int column, Severity severity, String msg) {
        switch (format) {
            case XCODE:
                expectedMessages.add(
                    Printer.genOutputStringForTest(Rules.UPPER_CAMEL_CASE, inputFile.getName(),
                        line, column, severity, msg + Messages.UPPER_CAMEL_CASE));
                break;
            case JSON:
                Map<String, Object> violation = new HashMap<>();
                Map<String, Object> location = new HashMap<>();
                location.put(Messages.LINE_KEY, line);
                location.put(Messages.COLUMN_KEY, column);
                violation.put(Messages.LOCATION_KEY, location);
                violation.put(Messages.SEVERITY_KEY, severity.toString());
                violation.put(Messages.RULE_KEY, Rules.UPPER_CAMEL_CASE.getName());
                violation.put(Messages.MESSAGE_KEY, msg + Messages.UPPER_CAMEL_CASE);
                String violationString = GSON.toJson(violation) + System.lineSeparator();
                expectedMessages.addAll(Arrays.asList(violationString.split(NEWLINE_REGEX)));
                break;
            default:
                break;
        }
    }

    private String getJSONSummary(long analyzed, long skipped, long errors, long warnings) {
        Map<String, Object> summary = new HashMap<>();
        summary.put(Messages.ANALYZED_KEY, analyzed);
        summary.put(Messages.SKIPPED_KEY, skipped);
        summary.put(Messages.VIOLATIONS_KEY, errors + warnings);
        summary.put(Messages.ERRORS_KEY, errors);
        summary.put(Messages.WARNINGS_KEY, warnings);
        Map<String, Object> output = new HashMap<>();
        output.put(Messages.SUMMARY_KEY, summary);
        return GSON.toJson(output) + System.lineSeparator();
    }

}
