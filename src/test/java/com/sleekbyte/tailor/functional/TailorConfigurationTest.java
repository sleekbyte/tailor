package com.sleekbyte.tailor.functional;

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
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link Tailor} configuration file flow.
 * Create config file that will only lint UpperCamelCaseTest.swift functional test file.
 */
@RunWith(MockitoJUnitRunner.class)
public final class TailorConfigurationTest {
    private File configurationFile;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    protected static final String TEST_INPUT_DIR = "src/test/swift/com/sleekbyte/tailor/functional";
    protected static final String NEWLINE_REGEX = "\\r?\\n";

    protected ByteArrayOutputStream outContent;
    protected File inputFile;
    protected List<String> expectedMessages;

    @Before
    public void setUp() throws IOException {
        configurationFile = createConfigFile(".tailor.yml");
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
    public void testConfigInfrastructure() throws IOException {
        String[] command = new String[] {
            "--config", configurationFile.getAbsolutePath(),
            "--no-color",
            "--only=upper-camel-case"
        };
        addAllExpectedMsgs();

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

    protected void addAllExpectedMsgs() {
        addExpectedMsg(3, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES);
        addExpectedMsg(7, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES);
        addExpectedMsg(24, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(25, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(26, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(42, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(43, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(46, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(47, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(50, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(55, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(63, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(72, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES);
        addExpectedMsg(76, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES);
        addExpectedMsg(90, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(94, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(98, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(107, 10, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(119, 18, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(119, 23, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(128, 20, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(137, 14, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(Rules.UPPER_CAMEL_CASE, inputFile.getName() + "/UpperCamelCaseTest.swift",
                line, column, severity, msg + Messages.UPPER_CAMEL_CASE));
    }

    private File createConfigFile(String fileName) throws IOException {
        File configFile = folder.newFile(fileName);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(configFile), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("include:");
        printWriter.println("  - '**/UpperCamelCaseTest.swift'");
        printWriter.println("exclude:");
        printWriter.println("  - '**/MaxNameLengthTest.swift'");
        streamWriter.close();
        printWriter.close();
        return configFile;
    }

}
