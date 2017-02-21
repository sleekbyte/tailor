package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.listeners.whitespace.AngleBracketWhitespaceListener;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link AngleBracketWhitespaceListener} rule.
 */
@RunWith(MockitoJUnitRunner.class)
public final class AngleBracketWhitespaceTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[] { "--only=angle-bracket-whitespace" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMessage(8, 29, Messages.CHEVRONS + Messages.NO_WHITESPACE_BEFORE);
        addExpectedMessage(8, 31, Messages.CHEVRONS + Messages.CONTENT + Messages.LEADING_WHITESPACE);
        addExpectedMessage(8, 45, Messages.CHEVRONS + Messages.CONTENT + Messages.NOT_END_SPACE);
        addExpectedMessage(16, 33, Messages.CHEVRONS + Messages.CONTENT + Messages.NOT_END_SPACE);
        addExpectedMessage(20, 19, Messages.CHEVRONS + Messages.NO_WHITESPACE_BEFORE);
        addExpectedMessage(20, 21, Messages.CHEVRONS + Messages.CONTENT + Messages.LEADING_WHITESPACE);
        addExpectedMessage(25, 8, Messages.OPERATOR_OVERLOADING_ONE_SPACE);
    }

    private void addExpectedMessage(int line, int column, String msg) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(
                Rules.ANGLE_BRACKET_WHITESPACE,
                inputFile.getName(),
                line,
                column,
                Severity.WARNING,
                msg
            )
        );
    }
}

