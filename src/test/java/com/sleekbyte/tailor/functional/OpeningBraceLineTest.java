package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for opening brace on same line rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class OpeningBraceLineTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(2, 1, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(7, 5, Severity.WARNING, Messages.INITIALIZER_BODY);
        addExpectedMsg(14, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(19, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(27, 9, Severity.WARNING, Messages.IF_STATEMENT);
        addExpectedMsg(32, 9, Severity.WARNING, Messages.IF_STATEMENT);
        addExpectedMsg(37, 9, Severity.WARNING, Messages.ELSE_CLAUSE);
        addExpectedMsg(50, 9, Severity.WARNING, Messages.SWITCH_STATEMENT);
        addExpectedMsg(82, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(87, 9, Severity.WARNING, Messages.FOR_LOOP);
        addExpectedMsg(92, 9, Severity.WARNING, Messages.FOR_IN_LOOP);
        addExpectedMsg(97, 9, Severity.WARNING, Messages.WHILE_STATEMENT);
        addExpectedMsg(102, 9, Severity.WARNING, Messages.REPEAT_WHILE_STATEMENT);
        addExpectedMsg(113, 4, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(118, 4, Severity.WARNING, Messages.STRUCT);
        addExpectedMsg(146, 13, Severity.WARNING, Messages.FOR_LOOP);
        addExpectedMsg(158, 1, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(165, 4, Severity.WARNING, Messages.STRUCT);
        addExpectedMsg(182, 1, Severity.WARNING, Messages.PROTOCOL);
        addExpectedMsg(197, 1, Severity.WARNING, Messages.PROTOCOL);
        addExpectedMsg(210, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(215, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(221, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(241, 1, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(260, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(270, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(279, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(285, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(295, 1, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(300, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(313, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(318, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(329, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(334, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(339, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(358, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(365, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(392, 10, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(397, 10, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(410, 10, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(419, 9, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(443, 9, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(447, 9, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(458, 9, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(462, 9, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(490, 9, Severity.WARNING, Messages.SETTER);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(inputFile.getName(), line, column, severity,
                                           msg + Messages.OPEN_BRACKET_STYLE));
    }

}
