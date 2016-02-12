package com.sleekbyte.tailor.functional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import com.sleekbyte.tailor.Tailor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Base class for functional rule tests.
 */
public abstract class RuleTest {

    protected static final String TEST_INPUT_DIR = "src/test/swift/com/sleekbyte/tailor/functional/";
    protected static final String NEWLINE_REGEX = "\\r?\\n";

    protected ByteArrayOutputStream outContent;
    protected File inputFile;
    protected List<String> expectedMessages;

    protected abstract void addAllExpectedMsgs();

    protected String getInputFilePath() {
        return String.format("%s.swift", this.getClass().getSimpleName());
    }

    @Before
    public void setUp() throws UnsupportedEncodingException, IOException {
        inputFile = new File(TEST_INPUT_DIR + getInputFilePath());
        expectedMessages = new ArrayList<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testRule() throws IOException {
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
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }
        // Ensure number of warnings in summary equals actual number of warnings in the output
        assertThat(summary, containsString(expectedMessages.size() + " violation"));
        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), this.expectedMessages.toArray(),
            actualOutput.toArray());
    }

    protected String[] getDefaultArgs() {
        return new String[] {"--no-color", inputFile.getPath()};
    }

    protected String[] getCommandArgs() {
        return new String[]{};
    }

}
