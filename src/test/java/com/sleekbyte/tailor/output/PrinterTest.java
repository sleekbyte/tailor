package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Printer}
 */
@RunWith(MockitoJUnitRunner.class)
public class PrinterTest {

    private static final String WARNING_MSG = "this is a warning";
    private static final String ERROR_MSG = "this is an error";
    private static final int LINE_NUMBER = 23;
    private static final int COLUMN_NUMBER = 13;

    private File inputFile = new File("abc.swift");
    private Printer printer = new Printer(inputFile);
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws IOException {
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testWarnWithLocationSuccess() throws IOException {
        printer.warn(WARNING_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        assertEquals(expectedOutput(Messages.WARNING, WARNING_MSG, LINE_NUMBER, COLUMN_NUMBER), outContent.toString());
    }

    @Test
    public void testErrorWithLocationSuccess() throws IOException {
        printer.error(ERROR_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        assertEquals(expectedOutput(Messages.ERROR, ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER), outContent.toString());
    }

    private String expectedOutput(String classification, String msg, int line, int column) throws IOException {
        return Printer.genOutputStringForTest(inputFile.getCanonicalPath(), line, column, classification, msg) + "\n";
    }

}
