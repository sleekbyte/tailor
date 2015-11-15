package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommentWhitespaceTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        // Single line comments
        int start = 2;
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 1, 5, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);

        // Multi line comments
        start = 9;
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

        // Documentation comments
        start = 54;
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 2, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 4, 3, Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 6, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 11, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
        addExpectedMsg(Rules.COMMENT_WHITESPACE, start + 16, 3, Messages.MULTILINE_COMMENT + Messages.START_SPACE);
    }

    private void addExpectedMsg(Rules rule, int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line, column,
            Severity.WARNING, msg));
    }
}
