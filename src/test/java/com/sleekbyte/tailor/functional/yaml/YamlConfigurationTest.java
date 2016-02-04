package com.sleekbyte.tailor.functional.yaml;

import static org.junit.Assert.assertArrayEquals;

import com.sleekbyte.tailor.Tailor;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
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
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link Tailor} configuration file flow.
 * Test config file functionality.
 */
@RunWith(MockitoJUnitRunner.class)
public final class YamlConfigurationTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    protected static final String TEST_INPUT_DIR = "src/test/swift/com/sleekbyte/tailor/functional/yaml";
    protected static final String NEWLINE_REGEX = "\\r?\\n";
    private static final String YamlTest1 = "YamlTest1.swift";
    private static final String YamlTest2 = "YamlTest2.swift";

    protected ByteArrayOutputStream outContent;
    protected File inputFile;
    protected List<String> expectedMessages;

    @Before
    public void setUp() throws IOException {
        inputFile = new File(TEST_INPUT_DIR);
        expectedMessages = new ArrayList<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testIncludeOption() throws IOException {
        // Add expected output
        addExpectedMsg(3, 7,
            Rules.UPPER_CAMEL_CASE,
            Messages.CLASS + Messages.NAMES + Messages.UPPER_CAMEL_CASE,
            YamlTest1);

        addExpectedMsg(7, 7,
            Rules.UPPER_CAMEL_CASE,
            Messages.CLASS + Messages.NAMES + Messages.UPPER_CAMEL_CASE,
            YamlTest1);

        addExpectedMsg(12, 15,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(13, 18,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(17, 15,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(18, 18,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(20, 26,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(21, 16,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(25, 19,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(26, 8,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(29, 63,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(31, 71,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(33, 57,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        addExpectedMsg(35, 4,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest1);

        // Create config file that wants to only analyze YamlTest1.swift
        File configurationFile = includeOptionConfig(".tailor.yml");
        runTest(configurationFile.getAbsolutePath());
    }

    @Test
    public void testExceptOption() throws IOException {
        // Add expected output
        addExpectedMsg(8, 33,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest2);

        addExpectedMsg(9, 16,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest2);

        addExpectedMsg(10, 23,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest2);

        addExpectedMsg(11, 39,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest2);

        addExpectedMsg(12, 2,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest2);

        addExpectedMsg(14, 23,
            Rules.TRAILING_CLOSURE,
            Messages.CLOSURE + Messages.TRAILING_CLOSURE,
            YamlTest2);

        addExpectedMsg(18, 2,
            Rules.TERMINATING_SEMICOLON,
            Messages.STATEMENTS + Messages.SEMICOLON,
            YamlTest2);

        // Create config file that will only analyze YamlTest2.swift for violations other than upper-camel-case
        File configurationFile = exceptOptionConfig(".tailor.yml");
        runTest(configurationFile.getAbsolutePath());
    }

    @Test
    public void testOnlyExceptOptionPrecedence() throws IOException {
        // Add expected output
        addExpectedMsg(3, 7,
            Rules.UPPER_CAMEL_CASE,
            Messages.CLASS + Messages.NAMES + Messages.UPPER_CAMEL_CASE,
            YamlTest1);

        addExpectedMsg(7, 7,
            Rules.UPPER_CAMEL_CASE,
            Messages.CLASS + Messages.NAMES + Messages.UPPER_CAMEL_CASE,
            YamlTest1);

        // Create config file that wants to only analyze YamlTest1.swift for upper-camel-case violations
        // Test for precedence of `only` over `except`
        File configurationFile = onlyAndExceptPrecedenceConfig(".tailor.yml");
        runTest(configurationFile.getAbsolutePath());
    }

    private void runTest(String configFilePath) throws UnsupportedEncodingException {
        String[] command = new String[] {
            "--config", configFilePath,
            "--no-color"
        };

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);

        // Skip first three lines for file header, last two lines for summary
        msgs = Arrays.copyOfRange(msgs, 3, msgs.length - 2);

        for (String msg : msgs) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), this.expectedMessages.toArray(),
            actualOutput.toArray());
    }

    private void addExpectedMsg(int line, int column, Rules rule, String msg, String fileName) {
        expectedMessages.add(
            Printer.genOutputStringForTest(rule, inputFile.getName() + "/" + fileName,
                line, column, Severity.WARNING, msg));
    }

    private File includeOptionConfig(String fileName) throws IOException {
        File configFile = folder.newFile(fileName);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(configFile), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("include:");
        printWriter.println("  - '**/" + YamlTest1 + "'");
        streamWriter.close();
        printWriter.close();
        return configFile;
    }

    private File onlyAndExceptPrecedenceConfig(String fileName) throws IOException {
        File configFile = folder.newFile(fileName);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(configFile), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("include:");
        printWriter.println("  - '**/" + YamlTest1 + "'");
        printWriter.println("only:");
        printWriter.println("  - upper-camel-case");
        printWriter.println("except:");
        printWriter.println("  - terminating-semicolon");
        streamWriter.close();
        printWriter.close();
        return configFile;
    }

    private File exceptOptionConfig(String fileName) throws IOException {
        File configFile = folder.newFile(fileName);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(configFile), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("include:");
        printWriter.println("  - '**/" + YamlTest2 + "'");
        printWriter.println("except:");
        printWriter.println("  - upper-camel-case");
        streamWriter.close();
        printWriter.close();
        return configFile;
    }

}