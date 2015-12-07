package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.listeners.whitespace.ParenthesisWhitespaceListener;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link ParenthesisWhitespaceListener} rule.
 */
@RunWith(MockitoJUnitRunner.class)
public final class ParenthesisWhitespaceTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[] { "--only=parenthesis-whitespace" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMessage(3, 5, Messages.PARENTHESES_CONTENT + Messages.LEADING_WHITESPACE);
        addExpectedMessage(3, 10, Messages.PARENTHESES_CONTENT + Messages.NOT_END_SPACE);
        addExpectedMessage(7, 5, Messages.PARENTHESES_CONTENT + Messages.LEADING_WHITESPACE);
        addExpectedMessage(15, 11, Messages.EMPTY_PARENTHESES + Messages.ILLEGAL_WHITESPACE);
        addExpectedMessage(21, 11, Messages.EMPTY_PARENTHESES + Messages.ILLEGAL_WHITESPACE);
        addExpectedMessage(23, 11, Messages.PARENTHESES_CONTENT + Messages.LEADING_WHITESPACE);
        addExpectedMessage(23, 18, Messages.PARENTHESES_CONTENT + Messages.NOT_END_SPACE);
        addExpectedMessage(25, 12, Messages.PARENTHESES_CONTENT + Messages.LEADING_WHITESPACE);
        addExpectedMessage(27, 17, Messages.PARENTHESES_CONTENT + Messages.NOT_END_SPACE);
        addExpectedMessage(29, 16, Messages.EMPTY_PARENTHESES + Messages.ILLEGAL_WHITESPACE);
        addExpectedMessage(33, 11, Messages.PARENTHESES_CONTENT + Messages.NOT_END_SPACE);
        addExpectedMessage(35, 14, Messages.PARENTHESES_CONTENT + Messages.LEADING_WHITESPACE);
        addExpectedMessage(44, 45, Messages.PARENTHESES_CONTENT + Messages.LEADING_WHITESPACE);
        addExpectedMessage(52, 11, Messages.PARENTHESES_CONTENT + Messages.NOT_END_SPACE);
    }

    private void addExpectedMessage(int line, int column, String msg) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(
                Rules.PARENTHESIS_WHITESPACE,
                inputFile.getName(),
                line,
                column,
                Severity.WARNING,
                msg
            )
        );
    }
}
