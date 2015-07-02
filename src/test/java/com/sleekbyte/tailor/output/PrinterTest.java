package com.sleekbyte.tailor.output;

import static org.junit.Assert.assertEquals;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Severity;
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

/**
 * Tests for {@link Printer}.
 */
@RunWith(MockitoJUnitRunner.class)
public class PrinterTest {

    private static final String WARNING_MSG = "this is a warning";
    private static final String ERROR_MSG = "this is an error";
    private static final int LINE_NUMBER = 23;
    private static final int COLUMN_NUMBER = 13;

    private File inputFile = new File("abc.swift");
    private Printer printer = new Printer(inputFile, Severity.ERROR);
    private Printer warnPrinter = new Printer(inputFile, Severity.WARNING);
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws UnsupportedEncodingException {
        outContent.reset();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testWarnWithLocationSuccess() throws IOException {
        printer.warn(WARNING_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        printer.close();
        assertEquals(expectedOutput(Severity.WARNING, WARNING_MSG, LINE_NUMBER, COLUMN_NUMBER),
            outContent.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void testErrorWithLocationSuccess() throws IOException {
        printer.error(ERROR_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        printer.close();
        assertEquals(expectedOutput(Severity.ERROR, ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER),
            outContent.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void testErrorWithMaxSeverityWarn() throws IOException {
        warnPrinter.error(ERROR_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        warnPrinter.close();
        assertEquals(expectedOutput(Severity.WARNING, ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER),
            outContent.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void testWarnWithMaxSeverityWarn() throws IOException {
        warnPrinter.warn(WARNING_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        warnPrinter.close();
        assertEquals(expectedOutput(Severity.WARNING, WARNING_MSG, LINE_NUMBER, COLUMN_NUMBER),
            outContent.toString(Charset.defaultCharset().name()));
    }

    private String expectedOutput(Severity severity, String msg, int line, int column) throws IOException {
        return Printer.genOutputStringForTest(inputFile.getCanonicalPath(), line, column, severity, msg) + "\n";
    }

}
