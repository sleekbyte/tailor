package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for closing brace by itself on a line.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClosingBraceLineTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(13, 54, Severity.WARNING, Messages.SWITCH_STATEMENT);
        addExpectedMsg(17, 12, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(24, 21, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(27, 12, Severity.WARNING, Messages.STRUCT);
        addExpectedMsg(30, 7, Severity.WARNING, Messages.STRUCT);
        addExpectedMsg(32, 20, Severity.WARNING, Messages.STRUCT);
        addExpectedMsg(51, 25, Severity.WARNING, Messages.PROTOCOL);
        addExpectedMsg(54, 24, Severity.WARNING, Messages.PROTOCOL);
        addExpectedMsg(57, 7, Severity.WARNING, Messages.PROTOCOL);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(inputFile.getName(), line, column, severity,
                                           msg + Messages.CLOSE_BRACKET_STYLE));
    }

}
