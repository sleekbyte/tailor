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
        addExpectedMsg(147, 13, Severity.WARNING, Messages.FOR_LOOP);
        addExpectedMsg(160, 1, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(167, 4, Severity.WARNING, Messages.STRUCT);
        addExpectedMsg(184, 1, Severity.WARNING, Messages.PROTOCOL);
        addExpectedMsg(199, 1, Severity.WARNING, Messages.PROTOCOL);
        addExpectedMsg(212, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(217, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(223, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(243, 1, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(262, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(272, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(281, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(287, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(297, 1, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(302, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(315, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(320, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(331, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(336, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(341, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(360, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(367, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(394, 10, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(399, 10, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(412, 10, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(421, 9, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(445, 9, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(449, 9, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(460, 9, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(464, 9, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(492, 9, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(503, 5, Severity.WARNING, Messages.SUBSCRIPT);
        addExpectedMsg(530, 9, Severity.WARNING, Messages.GETTER_SETTER_BLOCK);
        addExpectedMsg(530, 9, Severity.WARNING, Messages.SUBSCRIPT);
        addExpectedMsg(546, 13, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedMsg(550, 13, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedMsg(561, 13, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedMsg(565, 13, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedMsg(589, 13, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedMsg(598, 13, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedMsg(608, 13, Severity.WARNING, Messages.WILLSET_DIDSET_BLOCK);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(inputFile.getName(), line, column, severity,
                                           msg + Messages.OPEN_BRACE_STYLE));
    }

}
