package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for redundant parentheses rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class RedundantParenthesesTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--only=redundant-parentheses" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        int start = 3;
        addExpectedMsg(start, 8, Severity.WARNING, Messages.CONDITION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 3, 13, Severity.WARNING, Messages.CONDITION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 7, 10, Severity.WARNING, Messages.CONDITION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 13, 13, Severity.WARNING, Messages.CONDITION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 15, 11, Severity.WARNING, Messages.CONDITION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 18, 12, Severity.WARNING, Messages.SWITCH_EXPRESSION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 25, 13, Severity.WARNING, Messages.CATCH_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 29, 11, Severity.WARNING, Messages.THROW_STATEMENT + Messages.ENCLOSED_PARENTHESES);

        start = 57;
        addExpectedMsg(start, 35, Severity.WARNING, Messages.ARRAY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start, 45, Severity.WARNING, Messages.ARRAY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 2, 39, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 2, 48, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 2, 69, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 2, 78, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 6, 18, Severity.WARNING,
            Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 7, 21, Severity.WARNING,
            Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 8, 13, Severity.WARNING,
            Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(start + 18, 10, Severity.WARNING,
            Messages.EMPTY_PARENTHESES + Messages.REDUNDANT_METHOD_PARENTHESES);
        addExpectedMsg(start + 32, 15, Severity.WARNING,
            Messages.EMPTY_PARENTHESES + Messages.REDUNDANT_METHOD_PARENTHESES);
    }

    private void addExpectedMsg(int line, int column, Severity classification, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(Rules.REDUNDANT_PARENTHESES, inputFile.getName(), line,
            column, classification, msg));
    }

}
