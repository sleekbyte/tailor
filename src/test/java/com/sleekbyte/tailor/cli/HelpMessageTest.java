package com.sleekbyte.tailor.cli;

import static org.junit.Assert.assertEquals;

import com.sleekbyte.tailor.Tailor;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * Tests for {@link Tailor} CLI Help message.
 */
@RunWith(MockitoJUnitRunner.class)
public final class HelpMessageTest {

    protected ByteArrayOutputStream outContent;
    protected static final String NEWLINE_REGEX = "\\r?\\n";

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void setUp() throws IOException {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testHelpMessage() throws IOException {
        exit.expectSystemExitWithStatus(ExitCode.success());
        String[] command = { "--help" };

        exit.checkAssertionAfterwards(() -> {
                String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
                String actualUsageMessage = msgs[0];
                String expectedUsageMessage = "Usage: " + Messages.CMD_LINE_SYNTAX;
                assertEquals(expectedUsageMessage, actualUsageMessage);
            });

        Tailor.main(command);
    }
}
