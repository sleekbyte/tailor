package com.sleekbyte.tailor.functional;

import static org.junit.Assert.assertArrayEquals;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sleekbyte.tailor.Tailor;
import com.sleekbyte.tailor.common.ConfigProperties;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.format.CCFormatter;
import com.sleekbyte.tailor.format.Format;
import com.sleekbyte.tailor.format.Formatter;
import com.sleekbyte.tailor.format.HTMLFormatter;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.output.ViolationMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Tests for {@link Tailor} output formats.
 */
@RunWith(MockitoJUnitRunner.class)
public final class FormatTest {

    private static final String TEST_INPUT_DIR = "src/test/swift/com/sleekbyte/tailor/functional";
    private static final String NEWLINE_REGEX = "\\r?\\n";
    private static final String NEWLINE_PATTERN = "\n";
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    protected ByteArrayOutputStream outContent;
    protected File inputFile;
    protected List<String> expectedMessages;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

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

        final String[] command = new String[] {
            "--format", format.getName(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };
        expectedMessages.addAll(getExpectedMsgs().stream().map(msg -> Printer.genOutputStringForTest(
            msg.getRule(),
            inputFile.getName(),
            msg.getLineNumber(),
            msg.getColumnNumber(),
            msg.getSeverity(),
            msg.getMessage())).collect(Collectors.toList()));

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);

        // Skip first four lines for file header, last two lines for summary
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

        final String[] command = new String[] {
            "--format", format.getName(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };

        Map<String, Object> expectedOutput = getJSONMessages();

        Tailor.main(command);

        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        expectedMessages.addAll(
            Arrays.asList((GSON.toJson(expectedOutput) + System.lineSeparator()).split(NEWLINE_REGEX)));

        for (String msg : expectedMessages) {
            String strippedMsg = msg.replaceAll(inputFile.getCanonicalPath(), "");
            expected.add(strippedMsg);
        }

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
        for (String msg : msgs) {
            String strippedMsg = msg.replaceAll(inputFile.getCanonicalPath(), "");
            actual.add(strippedMsg);
        }

        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), expected.toArray(), actual.toArray());
    }

    @Test
    public void testXcodeConfigOption() throws IOException {
        File configurationFile = xcodeFormatConfigFile(".tailor.yml");

        final String[] command = new String[] {
            "--config", configurationFile.getAbsolutePath(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };

        expectedMessages.addAll(getExpectedMsgs().stream().map(msg -> Printer.genOutputStringForTest(
            msg.getRule(),
            inputFile.getName(),
            msg.getLineNumber(),
            msg.getColumnNumber(),
            msg.getSeverity(),
            msg.getMessage())).collect(Collectors.toList()));

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);

        // Skip first four lines for file header, last two lines for summary
        msgs = Arrays.copyOfRange(msgs, 4, msgs.length - 2);

        for (String msg : msgs) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), this.expectedMessages.toArray(),
            actualOutput.toArray());
    }

    public void testCCFormat() throws IOException {
        Format format = Format.CC;

        final String[] command = new String[] {
            "--format", format.getName(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };

        Tailor.main(command);

        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        StringBuilder expectedOutput = new StringBuilder();
        for (Map<String, Object> msg : getCCMessages()) {
            expectedOutput.append(GSON.toJson(msg)).append(CCFormatter.NULL_CHAR).append(System.lineSeparator());
        }
        expectedMessages.addAll(Arrays.asList(expectedOutput.toString().split(NEWLINE_REGEX)));

        for (String msg : expectedMessages) {
            String strippedMsg = msg.replaceAll(inputFile.getCanonicalPath(), "");
            expected.add(strippedMsg);
        }

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
        for (String msg : msgs) {
            String strippedMsg = msg.replaceAll(inputFile.getCanonicalPath(), "");
            actual.add(strippedMsg);
        }

        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), expected.toArray(), actual.toArray());
    }

    @Test
    public void testHTMLFormat() throws IOException {
        Format format = Format.HTML;

        final String[] command = new String[] {
            "--format", format.getName(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };

        Map<String, Object> expectedOutput = getHTMLMessages();

        Tailor.main(command);

        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        Mustache mustache = new DefaultMustacheFactory().compile(
            new InputStreamReader(HTMLFormatter.class.getResourceAsStream("index.html"), Charset.defaultCharset()),
            "index.html"
        );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mustache.execute(new OutputStreamWriter(baos, Charset.defaultCharset()), expectedOutput).flush();
        expectedMessages.addAll(Arrays.asList(baos.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX)));

        for (String msg : expectedMessages) {
            String strippedMsg = msg.replaceAll(inputFile.getCanonicalPath(), "");
            expected.add(strippedMsg);
        }

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
        for (String msg : msgs) {
            String strippedMsg = msg.replaceAll(inputFile.getCanonicalPath(), "");
            actual.add(strippedMsg);
        }

        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), expected.toArray(), actual.toArray());
    }

    protected List<ViolationMessage> getExpectedMsgs() {
        List<ViolationMessage> messages = new ArrayList<>();
        messages.add(createViolationMessage(3, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES));
        messages.add(createViolationMessage(7, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES));
        messages.add(createViolationMessage(42, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES));
        messages.add(createViolationMessage(46, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES));
        messages.add(createViolationMessage(50, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES));
        messages.add(createViolationMessage(72, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES));
        messages.add(createViolationMessage(76, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES));
        messages.add(createViolationMessage(90, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES));
        messages.add(createViolationMessage(94, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES));
        messages.add(createViolationMessage(98, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES));
        messages.add(createViolationMessage(119, 18, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES));
        messages.add(createViolationMessage(119, 23, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES));
        messages.add(createViolationMessage(128, 20, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES));
        messages.add(createViolationMessage(137, 14, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES));
        return messages;
    }

    private ViolationMessage createViolationMessage(int line, int column, Severity severity, String msg) {
        return new ViolationMessage(Rules.UPPER_CAMEL_CASE, line, column, severity, msg + Messages.UPPER_CAMEL_CASE);
    }

    private Map<String, Object> getJSONSummary(long analyzed, long skipped, long errors, long warnings) {
        Map<String, Object> summary = new HashMap<>();
        summary.put(Messages.ANALYZED_KEY, analyzed);
        summary.put(Messages.SKIPPED_KEY, skipped);
        summary.put(Messages.VIOLATIONS_KEY, errors + warnings);
        summary.put(Messages.ERRORS_KEY, errors);
        summary.put(Messages.WARNINGS_KEY, warnings);
        return summary;
    }

    private Map<String, Object> getJSONMessages() {
        List<Map<String, Object>> violations = new ArrayList<>();
        for (ViolationMessage msg : getExpectedMsgs()) {
            Map<String, Object> violation = new HashMap<>();
            Map<String, Object> location = new HashMap<>();
            location.put(Messages.LINE_KEY, msg.getLineNumber());
            location.put(Messages.COLUMN_KEY, msg.getColumnNumber());
            violation.put(Messages.LOCATION_KEY, location);
            violation.put(Messages.SEVERITY_KEY, msg.getSeverity().toString());
            violation.put(Messages.RULE_KEY, Rules.UPPER_CAMEL_CASE.getName());
            violation.put(Messages.MESSAGE_KEY, msg.getMessage());
            violations.add(violation);
        }

        Map<String, Object> file = new HashMap<>();
        file.put(Messages.PATH_KEY, "");
        file.put(Messages.PARSED_KEY, true);
        file.put(Messages.VIOLATIONS_KEY, violations);

        List<Object> files = new ArrayList<>();
        files.add(file);

        Map<String, Object> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(Messages.FILES_KEY, files);
        expectedOutput.put(Messages.SUMMARY_KEY, getJSONSummary(1, 0, 0, violations.size()));
        return expectedOutput;
    }

    private File xcodeFormatConfigFile(String fileName) throws IOException {
        File configFile = folder.newFile(fileName);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(configFile), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("format: xcode");
        streamWriter.close();
        printWriter.close();
        return configFile;
    }

    private List<Map<String, Object>> getCCMessages() {
        List<Map<String, Object>> violations = new ArrayList<>();

        for (ViolationMessage msg : getExpectedMsgs()) {
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

            violations.add(violation);
        }

        return violations;
    }

    private Map<String, Object> getHTMLMessages() throws IOException {
        List<Map<String, Object>> violations = new ArrayList<>();
        for (ViolationMessage msg : getExpectedMsgs()) {
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
            violation.put(Messages.RULE_KEY, Rules.UPPER_CAMEL_CASE.getName());
            violation.put(Messages.MESSAGE_KEY, msg.getMessage());
            violations.add(violation);
        }

        Map<String, Object> file = new HashMap<>();
        file.put(Messages.PATH_KEY, "");
        file.put(Messages.PARSED_KEY, true);
        file.put(Messages.VIOLATIONS_KEY, violations);
        file.put(Messages.NUM_VIOLATIONS_KEY,
            Formatter.pluralize(violations.size(), Messages.SINGLE_VIOLATION_KEY, Messages.MULTI_VIOLATIONS_KEY));

        List<Object> files = new ArrayList<>();
        files.add(file);

        Map<String, Object> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(Messages.FILES_KEY, files);
        expectedOutput.put(Messages.SUMMARY_KEY,
            Formatter.formatSummary(1, 0, 0, violations.size()).replace(NEWLINE_PATTERN, ""));
        expectedOutput.put(Messages.VERSION_LONG_OPT, new ConfigProperties().getVersion());
        return expectedOutput;
    }

}
