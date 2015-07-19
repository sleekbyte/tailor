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
        addExpectedBraceMsg(13, 54, Severity.WARNING, Messages.SWITCH_STATEMENT);
        addExpectedBraceMsg(17, 12, Severity.WARNING, Messages.CLASS);
        addExpectedEmptyConstructBodyMsg(24, 19, Severity.WARNING);
        addExpectedBraceMsg(27, 12, Severity.WARNING, Messages.STRUCT);
        addExpectedEmptyConstructBodyMsg(30, 5, Severity.WARNING);
        addExpectedEmptyConstructBodyMsg(32, 18, Severity.WARNING);
        addExpectedEmptyConstructBodyMsg(51, 23, Severity.WARNING);
        addExpectedEmptyConstructBodyMsg(54, 22, Severity.WARNING);
        addExpectedEmptyConstructBodyMsg(57, 5, Severity.WARNING);
        addExpectedEmptyConstructBodyMsg(63, 28, Severity.WARNING);
        addExpectedEmptyConstructBodyMsg(68, 28, Severity.WARNING);
        addExpectedEmptyConstructBodyMsg(71, 18, Severity.WARNING);
        addExpectedBraceMsg(85, 16, Severity.WARNING, Messages.FUNCTION);
        addExpectedEmptyConstructBodyMsg(89, 28, Severity.WARNING);
        addExpectedBraceMsg(91, 29, Severity.WARNING, Messages.CLASS);
        addExpectedBraceMsg(96, 4, Severity.WARNING, Messages.CLASS);
        addExpectedBraceMsg(100, 14, Severity.WARNING, Messages.CLASS);
        addExpectedBraceMsg(103, 6, Severity.WARNING, Messages.CLASS);
    }

    private void addExpectedBraceMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(inputFile.getName(), line, column, severity,
                                           msg + Messages.CLOSE_BRACKET_STYLE));
    }

    private void addExpectedEmptyConstructBodyMsg(int line, int column, Severity severity) {
        expectedMessages.add(
            Printer.genOutputStringForTest(inputFile.getName(), line, column, severity, Messages.EMPTY_BODY));
    }

}
