package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WhitespaceTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        // Operator declarations
        addExpectedOperatorMsg(3, 18, Messages.SPACE_BEFORE);
        addExpectedOperatorMsg(5, 17, Messages.SPACE_AFTER);
        addExpectedOperatorMsg(7, 18, Messages.SPACE_AFTER);
        addExpectedOperatorMsg(7, 18, Messages.SPACE_BEFORE);
        addExpectedOperatorMsg(9, 19, Messages.SPACE_BEFORE);
        addExpectedOperatorMsg(11, 16, Messages.SPACE_AFTER);
        addExpectedOperatorMsg(18, 16, Messages.SPACE_BEFORE);
        addExpectedOperatorMsg(21, 1, Messages.SPACE_AFTER);
        addExpectedOperatorMsg(26, 5, Messages.SPACE_AFTER);

        // Whitespace before open braces

        // functions
        int start = 39;
        addExpectedBraceMessage(start, 44);
        addExpectedBraceMessage(start + 10, 34);

        // if statements
        start = 56;
        addExpectedBraceMessage(start, 23);
        addExpectedBraceMessage(start + 3, 26);
        addExpectedBraceMessage(start + 6, 15);

        // loops
        start = 69;
        addExpectedBraceMessage(start, 53);
        addExpectedBraceMessage(start + 4, 38);
        addExpectedBraceMessage(start + 29, 22);
        addExpectedBraceMessage(start + 33, 17);
        addExpectedBraceMessage(start + 43, 17);
        addExpectedBraceMessage(start + 47, 26);
        addExpectedBraceMessage(start + 54, 22);
        addExpectedBraceMessage(start + 70, 20);
        addExpectedBraceMessage(start + 75, 18);

        // classes and structs with generic types
        start = 156;
        addExpectedBraceMessage(start, 6);
        addExpectedBraceMessage(start + 3, 4);

        // protocols
        start = 165;
        addExpectedBraceMessage(start, 24);
        addExpectedBraceMessage(start + 4, 32);
        addExpectedBraceMessage(start + 13, 24);

        // enums
        start = 192;
        addExpectedBraceMessage(start, 16);
        addExpectedBraceMessage(start + 4, 27);
        addExpectedBraceMessage(start + 17, 6);
        addExpectedBraceMessage(start + 26, 24);
        addExpectedBraceMessage(start + 34, 19);

        // closures
        start = 230;
        addExpectedBraceMessage(start, 29);
        addExpectedBraceMessage(start + 14, 72);
        addExpectedBraceMessage(start + 18, 17);

        // extensions
        start = 266;
        addExpectedBraceMessage(start, 26);
        addExpectedBraceMessage(start + 4, 29);
        addExpectedBraceMessage(start + 9, 19);

        // getters and setters
        start = 280;
        addExpectedBraceMessage(start, 8);
        addExpectedBraceMessage(start + 4, 8);
        addExpectedBraceMessage(start + 21, 14);
        addExpectedBraceMessage(start + 32, 21);
        addExpectedBraceMessage(start + 45, 24);
        addExpectedBraceMessage(start + 51, 25);
    }

    private void addExpectedOperatorMsg(int line, int column, String msg) {
        addExpectedMsg(Rules.OPERATOR_WHITESPACE, line, column, Messages.OPERATOR + msg);
    }

    private void addExpectedBraceMessage(int line, int column) {
        addExpectedMsg(Rules.BRACE_STYLE, line, column, Messages.OPEN_BRACE + Messages.SPACE_BEFORE);
    }

    private void addExpectedMsg(Rules rule, int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line, column,
            Severity.WARNING, msg));
    }
}
