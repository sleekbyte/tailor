package com.sleekbyte.tailor.format;

import static org.junit.Assert.assertEquals;

import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.ExitCode;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
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
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public final class XcodeFormatterTest {

    private static final String WARNING_MSG = "this is a warning";
    private static final String ERROR_MSG = "this is an error";
    private static final ColorSettings colorSettings = new ColorSettings(false, false);

    private File inputFile = new File("abc.swift");
    private Formatter formatter = new XcodeFormatter(colorSettings);
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws UnsupportedEncodingException {
        outContent.reset();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() throws IOException {
        System.setOut(null);
    }

    @Test
    public void testDisplayMessages() throws IOException {
        List<ViolationMessage> messages = new ArrayList<>();
        messages.add(new ViolationMessage(Rules.LOWER_CAMEL_CASE, inputFile.getCanonicalPath(), 10, 12,
            Severity.WARNING, WARNING_MSG));
        messages.add(new ViolationMessage(Rules.UPPER_CAMEL_CASE, inputFile.getCanonicalPath(),  11, 14,
            Severity.ERROR, ERROR_MSG));
        Collections.sort(messages);
        formatter.displayViolationMessages(messages, inputFile);
        assertEquals(expectedOutput(messages), outContent.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void testDisplayParseErrorMessage() throws IOException {
        formatter.displayParseErrorMessage(inputFile);
        String expectedOutput = XcodeFormatter.getHeader(inputFile, colorSettings) + "\n" + inputFile
            + Messages.COULD_NOT_BE_PARSED + "\n";
        assertEquals(expectedOutput, outContent.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void testDisplaySummary() throws IOException {
        final long files = 5;
        final long skipped = 1;
        final long errors = 7;
        final long warnings = 4;
        final long analyzed = files - skipped;
        final long violations = errors + warnings;
        formatter.displaySummary(files, skipped, errors, warnings);

        String expectedOutput = String.format(
            "%nAnalyzed %d files, skipped %d file, and detected %d violations (%d errors, %d warnings).%n%n",
            analyzed, skipped, violations, errors, warnings);

        assertEquals(expectedOutput, outContent.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void testSuccessExitStatus() {
        assertEquals(ExitCode.SUCCESS, formatter.getExitStatus(0));
    }

    @Test
    public void testFailureExitStatus() {
        assertEquals(ExitCode.FAILURE, formatter.getExitStatus(10));
    }

    private String expectedOutput(List<ViolationMessage> list) throws IOException {
        StringBuffer expected = new StringBuffer(XcodeFormatter.getHeader(inputFile, colorSettings) + "\n");
        for (ViolationMessage message : list) {
            expected.append(Printer.genOutputStringForTest(message.getRule(), inputFile.getCanonicalPath(),
                message.getLineNumber(), message.getColumnNumber(), message.getSeverity(), message.getMessage())
                + "\n");
        }
        return expected.toString();
    }
}
