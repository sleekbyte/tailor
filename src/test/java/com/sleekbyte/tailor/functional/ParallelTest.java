package com.sleekbyte.tailor.functional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import com.sleekbyte.tailor.Tailor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Tests for parallel analysis.
 */
@RunWith(MockitoJUnitRunner.class)
public final class ParallelTest {

    private static final String TEST_INPUT_DIR = "src/test/swift/com/sleekbyte/tailor/functional/";
    private static final String NEWLINE_REGEX = "\\r?\\n";

    private ByteArrayOutputStream outContent;
    private List<File> inputFiles;
    private List<String> expectedMessages;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        inputFiles = new ArrayList<>();
        inputFiles.add(new File(TEST_INPUT_DIR + "AngleBracketWhitespaceTest.swift"));
        inputFiles.add(new File(TEST_INPUT_DIR + "BraceWhitespaceTest.swift"));
        inputFiles.add(new File(TEST_INPUT_DIR + "SemicolonTest.swift"));
        inputFiles.add(new File(TEST_INPUT_DIR + "UpperCamelCaseTest.swift"));

        expectedMessages = new ArrayList<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testRule() throws ReflectiveOperationException, UnsupportedEncodingException {
        String[] command = Stream.concat(Arrays.stream(this.getCommandArgs()), Arrays.stream(this.getDefaultArgs()))
            .toArray(String[]::new);
        addAllExpectedMsgs();

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
        String summary = msgs[msgs.length - 1];
        // Skip first four lines for progress and file header, last two lines for summary
        msgs = Arrays.copyOfRange(msgs, 4, msgs.length - 2);

        for (String msg : msgs) {
            // Ignore empty lines and file headers
            if (msg.isEmpty() || msg.contains("**********")) {
                continue;
            }
            String truncatedMsg = msg.substring(msg.indexOf(TEST_INPUT_DIR) + TEST_INPUT_DIR.length());
            actualOutput.add(truncatedMsg);
        }

        // Ensure number of warnings in summary equals actual number of warnings in the output
        assertThat(summary, containsString(expectedMessages.size() + " violation"));
        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), this.expectedMessages.toArray(),
            actualOutput.toArray());
    }

    private void addAllExpectedMsgs() throws ReflectiveOperationException {
        for (File file : inputFiles) {
            String fileClassName = this.getClass().getPackage().getName() + "."
                + file.getName().substring(0, file.getName().indexOf(".swift"));
            RuleTest ruleTest = (RuleTest) Class.forName(fileClassName).getConstructor().newInstance();
            ruleTest.inputFile = new File(TEST_INPUT_DIR + ruleTest.getInputFilePath());
            ruleTest.expectedMessages = new ArrayList<>();
            ruleTest.addAllExpectedMsgs();
            this.expectedMessages.addAll(ruleTest.expectedMessages);
        }
    }

    private String[] getCommandArgs() {
        return new String[] { "--only=angle-bracket-whitespace,brace-style,terminating-semicolon,upper-camel-case" };
    }

    private String[] getDefaultArgs() {
        return new String[] {"--no-color", inputFiles.get(0).getPath(), inputFiles.get(1).getPath(),
            inputFiles.get(2).getPath(), inputFiles.get(3).getPath()};
    }
}
