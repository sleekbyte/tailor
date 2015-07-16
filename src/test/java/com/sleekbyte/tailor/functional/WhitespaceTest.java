package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WhitespaceTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        // Operator declarations
        addExpectedMsg(3, 18, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(5, 17, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(7, 18, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(7, 18, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(9, 19, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(11, 16, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(18, 16, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(21, 1, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(26, 5, Messages.OPERATOR + Messages.SPACE_AFTER);

        // Colons in type annotations
        int start = 35;
        addExpectedColonMessage(start, 7, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 1, 6, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 4, 11, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 5, 17, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 8, 13, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 9, 14, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 12, 44, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 12, 44, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 13, 17, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 16, 13, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 16, 13, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 16, 37, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 20, 33, Messages.NO_SPACE_BEFORE);

        // Colons in dictionaries
        start = 58;
        addExpectedColonMessage(start, 41, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 1, 25, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 3, 26, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 4, 19, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 5, 41, Messages.NO_SPACE_BEFORE);

        // Switch case colons
        start = 66;
        addExpectedColonMessage(start + 1, 17, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 2, 16, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 4, 9, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 9, 34, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 10, 8, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 20, 11, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 22, 10, Messages.SPACE_AFTER);

        // Type Inheritance clause colons

        // classes
        start = 91;
        addExpectedColonMessage(start, 18, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 6, 16, Messages.NO_SPACE_BEFORE);

        // structs
        start = 103;
        addExpectedColonMessage(start, 21, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 3, 20, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 7, 18, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 10, 19, Messages.SPACE_AFTER);

        // enums
        start = 116;
        addExpectedColonMessage(start, 14, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 4, 18, Messages.SPACE_AFTER);

        // protocols
        start = 127;
        addExpectedColonMessage(start, 31, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 3, 32, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 9, 28, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 12, 29, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 15, 28, Messages.SPACE_AFTER);

        // extensions
        start = 148;
        addExpectedColonMessage(start + 3, 20, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 6, 19, Messages.SPACE_AFTER);

        // ternary operator
        start = 157;
        addExpectedColonMessage(start, 48, Messages.SPACE_AFTER);
        addExpectedColonMessage(start, 48, Messages.SPACE_BEFORE);
        addExpectedColonMessage(start + 1, 14, Messages.SPACE_BEFORE);
        addExpectedColonMessage(start + 2, 15, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 4, 30, Messages.SPACE_BEFORE);
        addExpectedColonMessage(start + 5, 31, Messages.SPACE_AFTER);

        // tuples
        start = 164;
        addExpectedColonMessage(start + 1, 33, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 1, 51, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 2, 32, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 3, 45, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 3, 83, Messages.SPACE_AFTER);
    }

    private void addExpectedColonMessage(int line, int column, String msg) {
        addExpectedMsg(line, column, Messages.COLON + Messages.AT_COLUMN + column + " " + msg);
    }

    private void addExpectedMsg(int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, Severity.WARNING,
            msg));
    }
}
