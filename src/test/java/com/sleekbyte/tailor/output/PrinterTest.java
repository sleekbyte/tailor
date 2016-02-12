package com.sleekbyte.tailor.output;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.format.Formatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    private Formatter formatter = mock(Formatter.class);
    private Printer printer = new Printer(inputFile, Severity.ERROR, formatter);
    private Printer warnPrinter = new Printer(inputFile, Severity.WARNING, formatter);

    @Test
    public void testFormatterDisplayMessage() throws IOException {
        printer.error(Rules.LOWER_CAMEL_CASE, ERROR_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        List<ViolationMessage> violationsMessage = printer.getViolationMessages();
        printer.printAllMessages();
        verify(formatter).displayViolationMessages(violationsMessage, inputFile);
    }

    @Test
    public void testFormatterParseErrorMessage() throws IOException {
        printer.setShouldPrintParseErrorMessage(true);
        printer.printAllMessages();
        verify(formatter).displayParseErrorMessage(inputFile);
    }

    @Test
    public void testError() throws IOException {
        printer.error(Rules.LOWER_CAMEL_CASE, ERROR_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        ViolationMessage message = printer.getViolationMessages().get(0);
        assertEquals(message.getSeverity(), Severity.ERROR);
        validateViolationMessage(message, Rules.LOWER_CAMEL_CASE, ERROR_MSG, LINE_NUMBER,
            COLUMN_NUMBER);
        printer.printAllMessages();
    }

    @Test
    public void testWarn() throws IOException {
        printer.warn(Rules.LOWER_CAMEL_CASE, WARNING_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        ViolationMessage message = printer.getViolationMessages().get(0);
        assertEquals(message.getSeverity(), Severity.WARNING);
        validateViolationMessage(message, Rules.LOWER_CAMEL_CASE, WARNING_MSG, LINE_NUMBER,
            COLUMN_NUMBER);
        printer.printAllMessages();
    }

    @Test
    public void testWarnWithLocationSuccess() throws IOException {
        printer.warn(Rules.LOWER_CAMEL_CASE, WARNING_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        ViolationMessage message = printer.getViolationMessages().get(0);
        assertEquals(Severity.WARNING, message.getSeverity());
        printer.printAllMessages();
    }

    @Test
    public void testErrorWithLocationSuccess() throws IOException {
        printer.error(Rules.LOWER_CAMEL_CASE, ERROR_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        ViolationMessage message = printer.getViolationMessages().get(0);
        assertEquals(Severity.ERROR, message.getSeverity());
        printer.printAllMessages();
    }

    @Test
    public void testErrorWithMaxSeverityWarn() throws IOException {
        warnPrinter.error(Rules.LOWER_CAMEL_CASE, ERROR_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        ViolationMessage message = warnPrinter.getViolationMessages().get(0);
        assertEquals(Severity.WARNING, message.getSeverity());
        warnPrinter.printAllMessages();
    }

    @Test
    public void testWarnWithMaxSeverityWarn() throws IOException {
        warnPrinter.warn(Rules.LOWER_CAMEL_CASE, WARNING_MSG, new Location(LINE_NUMBER, COLUMN_NUMBER));
        ViolationMessage message = warnPrinter.getViolationMessages().get(0);
        assertEquals(Severity.WARNING, message.getSeverity());
        warnPrinter.printAllMessages();
    }

    private void validateViolationMessage(ViolationMessage message, Rules rule, String msg,
                                             int line, int column) {
        assertEquals(rule, message.getRule());
        assertEquals(msg, message.getMessage());
        assertEquals(line, message.getLineNumber());
        assertEquals(column, message.getColumnNumber());
    }

}
