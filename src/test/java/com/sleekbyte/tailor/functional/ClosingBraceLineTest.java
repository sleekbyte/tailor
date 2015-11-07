package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
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
    protected String[] getCommandArgs() {
        return new String[]{ "--only=brace-style" };
    }

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
        addExpectedBraceMsg(91, 31, Severity.WARNING, Messages.CLASS);
        addExpectedBraceMsg(96, 4, Severity.WARNING, Messages.CLASS);
        addExpectedBraceMsg(100, 14, Severity.WARNING, Messages.CLASS);
        addExpectedBraceMsg(103, 6, Severity.WARNING, Messages.CLASS);
        addExpectedBraceMsg(108, 25, Severity.WARNING, Messages.SETTER);
        addExpectedBraceMsg(111, 23, Severity.WARNING, Messages.GETTER);
        addExpectedBraceMsg(130, 23, Severity.WARNING, Messages.GETTER);
        addExpectedBraceMsg(139, 25, Severity.WARNING, Messages.GETTER);
        addExpectedBraceMsg(168, 60, Severity.WARNING, Messages.SETTER);
        addExpectedBraceMsg(176, 28, Severity.WARNING, Messages.INITIALIZER_BODY);
        addExpectedBraceMsg(192, 38, Severity.WARNING, Messages.IF_STATEMENT);
        addExpectedBraceMsg(195, 35, Severity.WARNING, Messages.IF_STATEMENT);
        addExpectedBraceMsg(201, 44, Severity.WARNING, Messages.ELSE_CLAUSE);
        addExpectedBraceMsg(209, 24, Severity.WARNING, Messages.FOR_LOOP);
        addExpectedBraceMsg(212, 30, Severity.WARNING, Messages.FOR_IN_LOOP);
        addExpectedBraceMsg(215, 47, Severity.WARNING, Messages.WHILE_STATEMENT);
        addExpectedBraceMsg(217, 38, Severity.WARNING, Messages.REPEAT_WHILE_STATEMENT);
        addExpectedBraceMsg(220, 18, Severity.WARNING, Messages.REPEAT_WHILE_STATEMENT);
        addExpectedBraceMsg(224, 15, Severity.WARNING, Messages.FUNCTION);
        addExpectedBraceMsg(226, 40, Severity.WARNING, Messages.FUNCTION);
        addExpectedBraceMsg(239, 34, Severity.WARNING, Messages.CLOSURE);
        addExpectedBraceMsg(247, 12, Severity.WARNING, Messages.CLOSURE);
        addExpectedBraceMsg(260, 19, Severity.WARNING, Messages.ENUM);
        addExpectedBraceMsg(268, 19, Severity.WARNING, Messages.ENUM);
        addExpectedBraceMsg(275, 25, Severity.WARNING, Messages.ENUM);
        addExpectedEmptyConstructBodyMsg(279, 15, Severity.WARNING);
        addExpectedBraceMsg(285, 16, Severity.WARNING, Messages.CLOSURE);
        addExpectedBraceMsg(295, 35, Severity.WARNING, Messages.SUBSCRIPT);
        addExpectedBraceMsg(303, 10, Severity.WARNING, Messages.SUBSCRIPT);
        addExpectedBraceMsg(329, 11, Severity.WARNING, Messages.GETTER_SETTER_BLOCK);
        addExpectedBraceMsg(329, 11, Severity.WARNING, Messages.SUBSCRIPT);
        addExpectedBraceMsg(335, 68, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedBraceMsg(339, 15, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedBraceMsg(348, 15, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedBraceMsg(355, 68, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedBraceMsg(363, 11, Severity.WARNING, Messages.WILLSET_DIDSET_BLOCK);
    }

    private void addExpectedBraceMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(Rules.BRACE_STYLE, inputFile.getName(), line, column, severity,
                                           msg + Messages.CLOSE_BRACE_STYLE));
    }

    private void addExpectedEmptyConstructBodyMsg(int line, int column, Severity severity) {
        expectedMessages.add(
            Printer.genOutputStringForTest(Rules.BRACE_STYLE, inputFile.getName(), line, column, severity,
                Messages.EMPTY_BODY));
    }

}
