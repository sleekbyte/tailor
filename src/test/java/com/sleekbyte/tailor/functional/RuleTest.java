package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.Tailor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Base class for functional rule tests
 */
public abstract class RuleTest {

    protected static final String TEST_INPUT_DIR = "src/test/java/com/sleekbyte/tailor/functional/";
    protected static final String NEWLINE_REGEX = "\\r?\\n";

    protected ByteArrayOutputStream outContent;
    protected File inputFile;
    protected List<String> expectedMessages;

    protected abstract void addAllExpectedMsgs();
    protected abstract String getInputFilePath();

    @Before
    public void setUp() {
        inputFile = new File(TEST_INPUT_DIR + getInputFilePath());
        expectedMessages = new ArrayList<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testRule() throws IOException {
        String[] command = getCommandArgs();
        addAllExpectedMsgs();

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();
        for (String msg : outContent.toString().split(NEWLINE_REGEX)) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        assertArrayEquals(outContent.toString(), this.expectedMessages.toArray(), actualOutput.toArray());
    }

    protected String[] getCommandArgs() {
        return new String[]{ inputFile.getPath() };
    }

}
