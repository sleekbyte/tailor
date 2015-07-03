package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for redundant parenthesis rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class RedundantParenthesisTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "RedundantParenthesisTest.swift";
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(3, 8, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(6, 13, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(10, 10, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(16, 13, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(18, 11, Severity.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(21, 12, Severity.WARNING, Messages.SWITCH_EXPRESSION + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(26, 9, Severity.WARNING, Messages.FOR_LOOP + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(31, 13, Severity.WARNING, Messages.CATCH_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(35, 11, Severity.WARNING, Messages.THROW_STATEMENT + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(60, 35, Severity.WARNING, Messages.ARRAY_LITERAL + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(60, 45, Severity.WARNING, Messages.ARRAY_LITERAL + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(62, 39, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(62, 48, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(62, 69, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(62, 78, Severity.WARNING, Messages.DICTIONARY_LITERAL + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(66, 18, Severity.WARNING, Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(67, 21, Severity.WARNING, Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(68, 13, Severity.WARNING, Messages.INITIALIZER_EXPRESSION + Messages.ENCLOSED_PARENTHESIS);
    }

    private void addExpectedMsg(int line, int column, Severity classification, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }
}
