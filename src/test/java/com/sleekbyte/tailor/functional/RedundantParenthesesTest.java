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
    protected void addAllExpectedMsgs() {
        addExpectedMsg(3, 8, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(6, 13, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(10, 10, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(16, 13, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(18, 11, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(21, 12, Severity.WARNING, Messages.SWITCH_EXPRESSION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(26, 9, Severity.WARNING, Messages.FOR_LOOP + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(31, 13, Severity.WARNING, Messages.CATCH_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(35, 11, Severity.WARNING, Messages.THROW_STATEMENT + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(60, 35, Severity.WARNING, Messages.ARRAY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(60, 45, Severity.WARNING, Messages.ARRAY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(62, 39, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(62, 48, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(62, 69, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(62, 78, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(66, 18, Severity.WARNING, Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(67, 21, Severity.WARNING, Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESES);
        addExpectedMsg(68, 13, Severity.WARNING, Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESES);
    }

    private void addExpectedMsg(int line, int column, Severity classification, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(Rules.REDUNDANT_PARENTHESES, inputFile.getName(), line,
            column, classification, msg));
    }

}
