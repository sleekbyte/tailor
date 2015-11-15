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
        addExpectedMsg(Rules.WHITESPACE, 3, 18, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(Rules.WHITESPACE, 5, 17, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(Rules.WHITESPACE, 7, 18, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(Rules.WHITESPACE, 7, 18, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(Rules.WHITESPACE, 9, 19, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(Rules.WHITESPACE, 11, 16, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(Rules.WHITESPACE, 18, 16, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(Rules.WHITESPACE, 21, 1, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(Rules.WHITESPACE, 26, 5, Messages.OPERATOR + Messages.SPACE_AFTER);

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
        start = 464 - 184;
        addExpectedBraceMessage(start, 8);
        addExpectedBraceMessage(start + 4, 8);
        addExpectedBraceMessage(start + 21, 14);
        addExpectedBraceMessage(start + 32, 21);
        addExpectedBraceMessage(start + 45, 24);
        addExpectedBraceMessage(start + 51, 25);

        // Single line comments
        start = 523 - 184;
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 1, 5, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);

        // Multi line comments
        start = 530 - 184;
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 1, 35, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 2, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 2, 17, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 3, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 3, 24, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 8, 10, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 9, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 22, 15, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 25, 8, Messages.MULTILINE_COMMENT + Messages.END_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 26, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 26, 23, Messages.MULTILINE_COMMENT + Messages.END_SPACE);

        // Function Result arrow
        start = 568 - 184;
        addExpectedArrowMessage(start, 18, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 4, 21, Messages.SPACE_AFTER);
        addExpectedArrowMessage(start + 8, 20, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 8, 22, Messages.SPACE_AFTER);

        // Closure result arrow
        start = 581 - 184;
        addExpectedArrowMessage(start, 9, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 5, 12, Messages.SPACE_AFTER);
        addExpectedArrowMessage(start + 10, 19, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 15, 20, Messages.SPACE_AFTER);

        // Function type arrow
        start = 600 - 184;
        addExpectedArrowMessage(start, 19, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 4, 22, Messages.SPACE_AFTER);
        addExpectedArrowMessage(start + 8, 40, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 12, 34, Messages.SPACE_AFTER);

        // Subscript result arrow
        start = 623 - 184;
        addExpectedArrowMessage(start, 26, Messages.SPACE_BEFORE);
        addExpectedArrowMessage(start + 4, 29, Messages.SPACE_AFTER);

        // Documentation comments
        start = 643 - 184;
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 2, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 4, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 6, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 11, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 16, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);

        // Type inheritance commas
        start = 672 - 184;
        addExpectedCommaMessage(start, 24, Messages.NO_SPACE_BEFORE);
        addExpectedCommaMessage(start + 4, 23, Messages.SPACE_AFTER);
        addExpectedCommaMessage(start + 8, 23, Messages.SPACE_AFTER);
        addExpectedCommaMessage(start + 16, 33, Messages.NO_SPACE_BEFORE);
        addExpectedCommaMessage(start + 29, 22, Messages.NO_SPACE_BEFORE);
        addExpectedCommaMessage(start + 37, 17, Messages.SPACE_AFTER);
        addExpectedCommaMessage(start + 42, 53, Messages.SPACE_AFTER);
        addExpectedCommaMessage(start + 46, 22, Messages.NO_SPACE_BEFORE);

        // Generic list commas
        start = 726 - 184;
        addExpectedCommaMessage(start, 31, Messages.SPACE_AFTER);
        addExpectedCommaMessage(start + 4, 31, Messages.SPACE_AFTER);
        addExpectedCommaMessage(start + 8, 32, Messages.NO_SPACE_BEFORE);

        // Requirement list commas
        start = 745 - 184;
        addExpectedCommaMessage(start, 37, Messages.SPACE_AFTER);
        addExpectedCommaMessage(start + 4, 43, Messages.SPACE_AFTER);
        addExpectedCommaMessage(start + 4, 43, Messages.NO_SPACE_BEFORE);
        addExpectedCommaMessage(start + 8, 1, Messages.NO_SPACE_BEFORE);
    }

    private void addExpectedArrowMessage(int line, int column, String msg) {
        addExpectedMsg(Rules.WHITESPACE, line, column, Messages.RETURN_ARROW + msg);
    }

    private void addExpectedBraceMessage(int line, int column) {
        addExpectedMsg(Rules.BRACE_STYLE, line, column, Messages.OPEN_BRACE + Messages.SPACE_BEFORE);
    }

    private void addExpectedColonMessage(int line, int column, String msg) {
        addExpectedMsg(Rules.COLON_WHITESPACE, line, column, Messages.COLON + Messages.AT_COLUMN + column + " " + msg);
    }

    private void addExpectedCommaMessage(int line, int column, String msg) {
        addExpectedMsg(Rules.WHITESPACE, line, column, Messages.COMMA + Messages.AT_COLUMN + column + " " + msg);
    }

    private void addExpectedMsg(Rules rule, int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line, column,
            Severity.WARNING, msg));
    }
}
