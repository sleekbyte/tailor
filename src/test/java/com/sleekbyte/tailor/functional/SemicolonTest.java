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
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1, 18, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(6, 15, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(7, 15, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(8, 14, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(9, 14, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(10, 2, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(13, 33, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(14, 16, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(15, 23, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(16, 39, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(17, 2, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(21, 2, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(24, 18, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(29, 10, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(34, 10, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(39, 21, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(40, 6, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(42, 2, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(47, 28, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(48, 14, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(52, 21, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(53, 10, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(56, 65, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(58, 73, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(60, 59, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(62, 6, Severity.WARNING, Messages.STATEMENTS);
        addExpectedMsg(64, 2, Severity.WARNING, Messages.STATEMENTS);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(Rules.TERMINATING_SEMICOLON, inputFile.getName(), line, column, severity,
                msg + Messages.SEMICOLON));
    }

}
