package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for semicolon rule
 */
@RunWith(MockitoJUnitRunner.class)
public class RedundantParenthesisTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "RedundantParenthesisTest.swift";
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(3, 8, Messages.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(6, 13, Messages.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(10, 10, Messages.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(16, 13, Messages.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(18, 11, Messages.WARNING, Messages.CONDITIONAL_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(21, 12, Messages.WARNING, Messages.SWITCH_EXPRESSION + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(26, 9, Messages.WARNING, Messages.FOR_LOOP + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(31, 13, Messages.WARNING, Messages.CATCH_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        addExpectedMsg(35, 11, Messages.WARNING, Messages.THROW_STATEMENT + Messages.ENCLOSED_PARENTHESIS);
    }

    private void addExpectedMsg(int line, int column, String classification, String msg) {
        expectedMessages.add(
                Printer.genOutputStringForTest(
                        inputFile.getName(), line, column, classification, msg));
    }
}
