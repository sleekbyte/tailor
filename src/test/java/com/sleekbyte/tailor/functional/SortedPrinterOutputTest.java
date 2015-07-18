package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for sorted printer output.
 */
@RunWith(MockitoJUnitRunner.class)
public final class SortedPrinterOutputTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1, 18, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(3, 25, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(6, 15, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(7, 15, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(8, 14, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(9, 14, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(10, 2, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(13, 33, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(14, 29, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(15, 23, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(16, 39, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(17, 2, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(20, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES + Messages.UPPER_CAMEL_CASE);
        addExpectedMsg(21, 18, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(27, 10, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(31, 10, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(33, Severity.WARNING, Messages.FILE + Messages.NEWLINE_TERMINATOR);
        addExpectedMsg(33, 2, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
    }

    private void addExpectedMsg(int line, Severity severity, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, severity, msg));
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, severity, msg));
    }

}
