package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
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
        addExpectedMsg(1, 18, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(6, 15, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(7, 15, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(8, 14, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(9, 14, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(10, 2, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(13, 33, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(14, 16, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(15, 23, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(16, 39, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(17, 2, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(21, 2, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(24, 18, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(29, 10, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(34, 10, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(39, 21, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(40, 6, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(41, 2, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(46, 28, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(47, 14, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(51, 21, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(52, 10, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(55, 65, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(57, 73, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(59, 59, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(61, 6, Severity.ERROR, Messages.STATEMENTS);
        addExpectedMsg(62, 2, Severity.ERROR, Messages.STATEMENTS);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(
                inputFile.getName(), line, column, severity, msg + Messages.SEMICOLON));
    }

}
