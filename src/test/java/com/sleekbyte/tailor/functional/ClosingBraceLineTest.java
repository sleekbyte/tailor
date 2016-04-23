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

        int start = 209;
        addExpectedBraceMsg(start, 30, Severity.WARNING, Messages.FOR_IN_LOOP);
        addExpectedBraceMsg(start + 3, 47, Severity.WARNING, Messages.WHILE_STATEMENT);
        addExpectedBraceMsg(start + 5, 38, Severity.WARNING, Messages.REPEAT_WHILE_STATEMENT);
        addExpectedBraceMsg(start + 8, 18, Severity.WARNING, Messages.REPEAT_WHILE_STATEMENT);

        start = 221;
        addExpectedBraceMsg(start, 15, Severity.WARNING, Messages.FUNCTION);
        addExpectedBraceMsg(start + 2, 40, Severity.WARNING, Messages.FUNCTION);
        addExpectedBraceMsg(start + 15, 34, Severity.WARNING, Messages.CLOSURE);
        addExpectedBraceMsg(start + 23, 12, Severity.WARNING, Messages.CLOSURE);

        start = 257;
        addExpectedBraceMsg(start, 19, Severity.WARNING, Messages.ENUM);
        addExpectedBraceMsg(start + 8, 19, Severity.WARNING, Messages.ENUM);
        addExpectedBraceMsg(start + 15, 25, Severity.WARNING, Messages.ENUM);
        addExpectedEmptyConstructBodyMsg(start + 19, 15, Severity.WARNING);
        addExpectedBraceMsg(start + 25, 16, Severity.WARNING, Messages.CLOSURE);
        addExpectedBraceMsg(start + 35, 35, Severity.WARNING, Messages.SUBSCRIPT);
        addExpectedBraceMsg(start + 43, 10, Severity.WARNING, Messages.SUBSCRIPT);

        start = 326;
        addExpectedBraceMsg(start, 11, Severity.WARNING, Messages.GETTER_SETTER_BLOCK);
        addExpectedBraceMsg(start, 11, Severity.WARNING, Messages.SUBSCRIPT);
        addExpectedBraceMsg(start + 6, 68, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedBraceMsg(start + 10, 15, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedBraceMsg(start + 19, 15, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedBraceMsg(start + 26, 68, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedBraceMsg(start + 34, 11, Severity.WARNING, Messages.WILLSET_DIDSET_BLOCK);
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
