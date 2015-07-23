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
        addExpectedColonMessage(start + 8, 16, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 14, 16, Messages.NO_SPACE_BEFORE);

        // structs
        start = 109;
        addExpectedColonMessage(start, 21, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 3, 20, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 7, 18, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 10, 19, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 13, 21, Messages.NO_SPACE_BEFORE);

        // enums
        start = 130;
        addExpectedColonMessage(start, 14, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 4, 18, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 11, 14, Messages.NO_SPACE_BEFORE);

        // protocols
        start = 154;
        addExpectedColonMessage(start, 31, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 3, 32, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 9, 28, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 12, 29, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 15, 28, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 26, 1, Messages.SPACE_AFTER);

        // extensions
        start = 183;
        addExpectedColonMessage(start + 3, 20, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 6, 19, Messages.SPACE_AFTER);

        // ternary operator
        start = 192;
        addExpectedColonMessage(start, 48, Messages.SPACE_AFTER);
        addExpectedColonMessage(start, 48, Messages.SPACE_BEFORE);
        addExpectedColonMessage(start + 1, 14, Messages.SPACE_BEFORE);
        addExpectedColonMessage(start + 2, 15, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 4, 30, Messages.SPACE_BEFORE);
        addExpectedColonMessage(start + 5, 31, Messages.SPACE_AFTER);

        // tuples
        start = 199;
        addExpectedColonMessage(start + 1, 33, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 1, 51, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 2, 32, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 3, 45, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 3, 83, Messages.SPACE_AFTER);

        // generics
        start = 204;
        addExpectedColonMessage(start + 3, 21, Messages.NO_SPACE_BEFORE);
        addExpectedColonMessage(start + 3, 35, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 6, 20, Messages.SPACE_AFTER);
        addExpectedColonMessage(start + 10, 8, Messages.NO_SPACE_BEFORE);

        // Whitespace before open braces

        // functions
        start = 223;
        addExpectedBraceMessage(start, 44);
        addExpectedBraceMessage(start + 10, 34);

        // if statements
        start = 240;
        addExpectedBraceMessage(start, 23);
        addExpectedBraceMessage(start + 3, 26);
        addExpectedBraceMessage(start + 6, 15);

        // loops
        start = 253;
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
        start = 340;
        addExpectedBraceMessage(start, 6);
        addExpectedBraceMessage(start + 3, 4);

        // protocols
        start = 349;
        addExpectedBraceMessage(start, 24);
        addExpectedBraceMessage(start + 4, 32);
        addExpectedBraceMessage(start + 13, 24);

        // enums
        start = 376;
        addExpectedBraceMessage(start, 16);
        addExpectedBraceMessage(start + 4, 27);
        addExpectedBraceMessage(start + 17, 6);
        addExpectedBraceMessage(start + 26, 24);
        addExpectedBraceMessage(start + 34, 19);

        // closures
        start = 414;
        addExpectedBraceMessage(start, 29);
        addExpectedBraceMessage(start + 14, 72);
        addExpectedBraceMessage(start + 18, 17);

        // extensions
        start = 450;
        addExpectedBraceMessage(start, 26);
        addExpectedBraceMessage(start + 4, 29);
        addExpectedBraceMessage(start + 9, 19);

        // getters and setters
        start = 464;
        addExpectedBraceMessage(start, 8);
        addExpectedBraceMessage(start + 4, 8);
        addExpectedBraceMessage(start + 21, 14);
        addExpectedBraceMessage(start + 32, 21);
        addExpectedBraceMessage(start + 51, 25);

        // Single line comments
        start = 523;
        addExpectedMsg(start, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(start + 1, 5, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);

        // Multi line comments
        start = 530;
        addExpectedMsg(start, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(start + 1, 35, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(start + 2, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(start + 2, 17, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(start + 3, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(start + 3, 24, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(start + 8, 10, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(start + 9, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(start + 22, 15, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(start + 25, 8, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(start + 26, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(start + 26, 23, Messages.MULTILINE_COMMENT + Messages.END_SPACE);

        // Function Result arrow
        start = 568;
        addExpectedArrowMessage(start, 18, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 4, 21, Messages.SPACE_AFTER);
        addExpectedArrowMessage(start + 8, 20, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 8, 22, Messages.SPACE_AFTER);

        // Closure result arrow
        start = 581;
        addExpectedArrowMessage(start, 9, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 5, 12, Messages.SPACE_AFTER);
        addExpectedArrowMessage(start + 10, 19, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 15, 20, Messages.SPACE_AFTER);

    }

    private void addExpectedArrowMessage(int line, int column, String msg) {
        addExpectedMsg(line, column, Messages.RETURN_ARROW + msg);
    }

    private void addExpectedBraceMessage(int line, int column) {
        addExpectedMsg(line, column, Messages.OPEN_BRACE + Messages.SPACE_BEFORE);
    }

    private void addExpectedColonMessage(int line, int column, String msg) {
        addExpectedMsg(line, column, Messages.COLON + Messages.AT_COLUMN + column + " " + msg);
    }

    private void addExpectedMsg(int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, Severity.WARNING,
            msg));
    }
}
