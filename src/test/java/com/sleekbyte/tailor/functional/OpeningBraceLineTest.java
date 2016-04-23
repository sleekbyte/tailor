package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
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
    protected String[] getCommandArgs() {
        return new String[]{ "--only=brace-style" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        int start = 2;
        addExpectedMsg(start, 1, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(start + 5, 5, Severity.WARNING, Messages.INITIALIZER_BODY);
        addExpectedMsg(start + 12, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(start + 17, 5, Severity.WARNING, Messages.FUNCTION);

        start = 27;
        addExpectedMsg(start, 9, Severity.WARNING, Messages.IF_STATEMENT);
        addExpectedMsg(start + 5, 9, Severity.WARNING, Messages.IF_STATEMENT);
        addExpectedMsg(start + 10, 9, Severity.WARNING, Messages.ELSE_CLAUSE);
        addExpectedMsg(start + 23, 9, Severity.WARNING, Messages.SWITCH_STATEMENT);

        start = 78;
        addExpectedMsg(start, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(start + 10 - 5, 9, Severity.WARNING, Messages.FOR_IN_LOOP);
        addExpectedMsg(start + 15 - 5, 9, Severity.WARNING, Messages.WHILE_STATEMENT);
        addExpectedMsg(start + 20 - 5, 9, Severity.WARNING, Messages.REPEAT_WHILE_STATEMENT);
        addExpectedMsg(start + 31 - 5, 4, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(start + 36 - 5, 4, Severity.WARNING, Messages.STRUCT);

        start = 138;
        addExpectedMsg(start + 13 - 32, 1, Severity.WARNING, Messages.CLASS);
        addExpectedMsg(start + 20 - 32, 4, Severity.WARNING, Messages.STRUCT);
        addExpectedMsg(start + 37 - 32, 1, Severity.WARNING, Messages.PROTOCOL);
        addExpectedMsg(start + 52 - 32, 1, Severity.WARNING, Messages.PROTOCOL);

        start = 171;
        addExpectedMsg(start, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(start + 5, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(start + 11, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(start + 31, 1, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(start + 50, 1, Severity.WARNING, Messages.CLOSURE);

        start = 231;
        addExpectedMsg(start, 1, Severity.WARNING, Messages.ENUM);
        addExpectedMsg(start + 9, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(start + 15, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(start + 25, 1, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(start + 30, 1, Severity.WARNING, Messages.ENUM);

        start = 274;
        addExpectedMsg(start, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(start + 5, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(start + 16, 1, Severity.WARNING, Messages.EXTENSION);
        addExpectedMsg(start + 21, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(start + 26, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(start + 45, 1, Severity.WARNING, Messages.CLOSURE);
        addExpectedMsg(start + 52, 1, Severity.WARNING, Messages.CLOSURE);

        start = 353;
        addExpectedMsg(start, 10, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(start + 5, 10, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(start + 18, 10, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(start + 27, 9, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(start + 51, 9, Severity.WARNING, Messages.GETTER);

        start = 408;
        addExpectedMsg(start, 9, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(start + 11, 9, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(start + 15, 9, Severity.WARNING, Messages.GETTER);
        addExpectedMsg(start + 43, 9, Severity.WARNING, Messages.SETTER);
        addExpectedMsg(start + 54, 5, Severity.WARNING, Messages.SUBSCRIPT);
        addExpectedMsg(start + 81, 9, Severity.WARNING, Messages.GETTER_SETTER_BLOCK);
        addExpectedMsg(start + 81, 9, Severity.WARNING, Messages.SUBSCRIPT);

        start = 505;
        addExpectedMsg(start, 13, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedMsg(start + 4, 13, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedMsg(start + 15, 13, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedMsg(start + 19, 13, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedMsg(start + 43, 13, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedMsg(start + 52, 13, Severity.WARNING, Messages.DID_SET_CLAUSE);
        addExpectedMsg(start + 62, 13, Severity.WARNING, Messages.WILLSET_DIDSET_BLOCK);
        addExpectedMsg(start + 98, 13, Severity.WARNING, Messages.WILL_SET_CLAUSE);
        addExpectedMsg(start + 103, 13, Severity.WARNING, Messages.DID_SET_CLAUSE);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(Rules.BRACE_STYLE, inputFile.getName(), line, column, severity,
                                           msg + Messages.OPEN_BRACE_STYLE));
    }

}
