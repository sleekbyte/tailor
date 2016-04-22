package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for sorted printer output.
 */
@RunWith(MockitoJUnitRunner.class)
public class SortedPrinterOutputTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 1, 18, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 3, 25, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 6, 15, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 7, 15, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 8, 14, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 9, 14, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 10, 2, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 13, 33, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 14, 29, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 15, 23, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 16, 39, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 17, 2, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.UPPER_CAMEL_CASE, 20, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES
            + Messages.UPPER_CAMEL_CASE);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 21, 18, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 27, 10, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(Rules.TERMINATING_NEWLINE, 30, Severity.WARNING, Messages.FILE + Messages.NEWLINE_TERMINATOR);
        addExpectedMsg(Rules.TERMINATING_SEMICOLON, 30, 2, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
    }

    private void addExpectedMsg(Rules rule, int line, Severity severity, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line, severity, msg));
    }

    private void addExpectedMsg(Rules rule, int line, int column, Severity severity, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line, column, severity, msg));
    }

}
