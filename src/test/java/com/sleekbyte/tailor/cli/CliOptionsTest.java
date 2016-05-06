package com.sleekbyte.tailor.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.sleekbyte.tailor.Tailor;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;

/**
 * Tests for {@link Tailor} CLI options.
 */
@RunWith(MockitoJUnitRunner.class)
public final class CliOptionsTest {

    private static final String NEWLINE_REGEX = "\\r?\\n";
    private static final String TEST_DIR = "src/test/swift/com/sleekbyte/tailor/functional/";
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void setUp() throws IOException {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
        System.setErr(new PrintStream(errContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
        System.setErr(null);
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

    @Test
    public void testVersionOutput() throws IOException {
        exit.expectSystemExitWithStatus(ExitCode.success());
        String[] command = { "--version" };

        exit.checkAssertionAfterwards(() -> {
                String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
                String actualVersion = msgs[0];
                assertTrue("Version number should match MAJOR.MINOR.PATCH format from http://semver.org.",
                    actualVersion.matches("\\d+\\.\\d+\\.\\d+"));
            });

        Tailor.main(command);
    }

    @Test
    public void testShowRules() throws IOException {
        exit.expectSystemExitWithStatus(ExitCode.success());
        String[] command = { "--show-rules" };

        Tailor.main(command);

        assertTrue(Rules.values().length > 0);
    }

    @Test
    public void testNoSourceInputAndNoConfigFile() throws IOException {
        exit.expectSystemExitWithStatus(ExitCode.failure());
        String[] command = { "" };

        exit.checkAssertionAfterwards(() -> {
                String[] msgs = errContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
                String actualErrorMessage = msgs[0];
                String expectedErrorMessage = Messages.NO_SWIFT_FILES_FOUND;
                assertEquals(expectedErrorMessage, actualErrorMessage);
            });

        Tailor.main(command);
    }

    @Test
    public void testTwoSourceInputFiles() throws IOException {
        File inputFile1 = new File(TEST_DIR + "/UpperCamelCaseTest.swift");
        File inputFile2 = new File(TEST_DIR + "/LowerCamelCaseTest.swift");
        String[] command = {"--no-color",
            inputFile1.getPath(),
            inputFile2.getPath()
        };

        Tailor.main(command);

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
        String actualSummary = msgs[msgs.length - 1];
        assertTrue(actualSummary.startsWith("Analyzed 2 files, skipped 0 files"));
    }

    @Test
    public void testListFiles() throws IOException {
        exit.expectSystemExitWithStatus(ExitCode.success());
        String inputPath = Paths.get(TEST_DIR).toString();
        String[] command = { "--list-files", inputPath };

        exit.checkAssertionAfterwards(() -> {
                String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
                assertTrue(msgs.length > 1);
                String actualOutput = msgs[0];
                String expectedOutput = Messages.FILES_TO_BE_ANALYZED;
                assertEquals(expectedOutput, actualOutput);
            });

        Tailor.main(command);
    }

    @Test
    public void testPurgeWithInvalidInput() throws IOException {
        exit.expectSystemExitWithStatus(ExitCode.failure());
        String inputPath = Paths.get(TEST_DIR).toString();
        String[] command = { "--purge=-1", inputPath };

        exit.checkAssertionAfterwards(() ->
            assertTrue(errContent.toString().startsWith("Invalid number of files specified for purge")));

        Tailor.main(command);
    }

}
