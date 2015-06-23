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
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrinterTest {

    private static final String WARN_MSG = "this is a warning";
    private static final String ERROR_MSG = "this is an error";
    private static final int LINE_NUMBER = 23;
    private static final int COLUMN_NUMBER = 13;

    private static final Location DEFAULT_LOCATION = new Location(1, 1);
    private static final File INPUT_FILE = new File("abc.swift");

    private static Printer printer;
    private static ByteArrayOutputStream outContent;

    @Mock private static ParserRuleContext context;
    @Mock private static Token token;

    @Before
    public void setUp() {
        printer = new Printer(INPUT_FILE);
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
    public void testWarnWithContextOnly() {
        printer.warn(WARN_MSG, Optional.of(context), Optional.empty());
        assertEquals(expectedOutput("warning", WARN_MSG, LINE_NUMBER, COLUMN_NUMBER + 1), outContent.toString());
    }

    @Test
    public void testErrorWithContextOnly() {
        printer.error(ERROR_MSG, Optional.of(context), Optional.empty());
        assertEquals(expectedOutput("error", ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER + 1), outContent.toString());
    }

    @Test
    public void testPrintWithLocationOnly() {
        Location location = new Location(LINE_NUMBER, COLUMN_NUMBER);
        printer.error(ERROR_MSG, Optional.empty(), Optional.of(location));
        assertEquals(expectedOutput("error", ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER), outContent.toString());
    }

    @Test
    public void testPrintWithAbsentContextAbsentLocation() {
        printer.error(ERROR_MSG, Optional.empty(), Optional.empty());
        assertEquals(expectedOutput("error", ERROR_MSG, DEFAULT_LOCATION.line, DEFAULT_LOCATION.column),
            outContent.toString());
    }

    @Test
    public void testPrintWithContextAndLocation() {
        Location location = new Location(LINE_NUMBER + 1, COLUMN_NUMBER + 1);
        printer.error(ERROR_MSG, Optional.of(context), Optional.of(location));
        // Output should print line and column number of context
        assertEquals(expectedOutput("error", ERROR_MSG, LINE_NUMBER, COLUMN_NUMBER + 1), outContent.toString());
    }

    private String expectedOutput(String classification, String msg, int line, int column) {
        return INPUT_FILE.getName() + ":" + line + ":" + column + ": " + classification + ": " + msg + "\n";
    }

}
