package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link Printer}
 */
@RunWith(MockitoJUnitRunner.class)
public class PrinterTest {

    private static final String WARNING_MSG = "this is a warning";
    private static final String ERROR_MSG = "this is an error";
    private static final int LINE_NUMBER = 23;
    private static final int COLUMN_NUMBER = 13;

    private static final Location DEFAULT_LOCATION = new Location(1, 1);

    private static Printer printer;
    private static ByteArrayOutputStream outContent;

    @Mock private ParserRuleContext context;
    @Mock private Token token;

    private File inputFile;

    @Before
    public void setUp() throws IOException {
        inputFile = new File("abc.swift");
        printer = new Printer(inputFile);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        when(context.getStart()).thenReturn(token);
        when(token.getLine()).thenReturn(LINE_NUMBER);
        when(token.getCharPositionInLine()).thenReturn(COLUMN_NUMBER);
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testWarnWithContextOnly() throws IOException {
        printer.warn(WARNING_MSG, Optional.of(context), Optional.empty());
        assertEquals(expectedOutput("warning", WARNING_MSG, LINE_NUMBER, COLUMN_NUMBER + 1), outContent.toString());
    }

    @Test
    public void testErrorWithContextOnly() throws IOException {
        printer.error(ERROR_MSG, Optional.of(context), Optional.empty());
        assertEquals(expectedOutput("error", ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER + 1), outContent.toString());
    }

    @Test
    public void testPrintWithLocationOnly() throws IOException {
        Location location = new Location(LINE_NUMBER, COLUMN_NUMBER);
        printer.error(ERROR_MSG, Optional.empty(), Optional.of(location));
        assertEquals(expectedOutput("error", ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER), outContent.toString());
    }

    @Test
    public void testPrintWithAbsentContextAbsentLocation() throws IOException {
        printer.error(ERROR_MSG, Optional.empty(), Optional.empty());
        assertEquals(expectedOutput("error", ERROR_MSG, DEFAULT_LOCATION.line, DEFAULT_LOCATION.column),
            outContent.toString());
    }

    @Test
    public void testPrintWithContextAndLocation() throws IOException {
        Location location = new Location(LINE_NUMBER + 1, COLUMN_NUMBER + 1);
        printer.error(ERROR_MSG, Optional.of(context), Optional.of(location));
        // Output should have line and column number of context
        assertEquals(expectedOutput("error", ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER + 1), outContent.toString());
    }

    private String expectedOutput(String classification, String msg, int line, int column) throws IOException {
        return inputFile.getCanonicalPath() + ":" + line + ":" + column + ": " + classification + ": " + msg + "\n";
    }

}
