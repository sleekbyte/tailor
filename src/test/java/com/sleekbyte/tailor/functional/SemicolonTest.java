package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for semicolon rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class SemicolonTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--only=terminating-semicolon" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        int start = 1;
        addExpectedMsg(start, 18, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 5, 15, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 6, 15, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 7, 14, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 8, 14, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 9, 2, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 12, 33, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 13, 16, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 14, 23, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 15, 39, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 16, 2, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 20, 2, Severity.WARNING, Messages.STATEMENTS);

        start = 24;
        addExpectedMsg(start, 18, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 5, 10, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 15 - 5, 21, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 16 - 5, 6, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 18 - 5, 2, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 23 - 5, 28, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 24 - 5, 14, Severity.WARNING, Messages.STATEMENTS);

        start = 47;
        addExpectedMsg(start, 65, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 2, 73, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 4, 59, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 6, 6, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(start + 8, 2, Severity.WARNING, Messages.STATEMENTS);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(Rules.TERMINATING_SEMICOLON, inputFile.getName(), line, column, severity,
                msg + Messages.SEMICOLON));
    }

}
