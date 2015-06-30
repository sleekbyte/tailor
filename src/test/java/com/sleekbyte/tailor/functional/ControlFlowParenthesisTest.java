package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for semicolon rule
 */
@RunWith(MockitoJUnitRunner.class)
public class ControlFlowParenthesisTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "ControlFlowParenthesisTest.swift";
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(3, 8, Messages.WARNING, Messages.CONDITIONAL + Messages.STARTS_WITH_PARENTHESIS);
        addExpectedMsg(3, 15, Messages.WARNING, Messages.CONDITIONAL + Messages.ENDS_WITH_PARENTHESIS);
        addExpectedMsg(6, 13, Messages.WARNING, Messages.CONDITIONAL + Messages.STARTS_WITH_PARENTHESIS);
        addExpectedMsg(6, 20, Messages.WARNING, Messages.CONDITIONAL + Messages.ENDS_WITH_PARENTHESIS);
        addExpectedMsg(10, 10, Messages.WARNING, Messages.CONDITIONAL + Messages.STARTS_WITH_PARENTHESIS);
        addExpectedMsg(10, 15, Messages.WARNING, Messages.CONDITIONAL + Messages.ENDS_WITH_PARENTHESIS);
        addExpectedMsg(16, 13, Messages.WARNING, Messages.CONDITIONAL + Messages.STARTS_WITH_PARENTHESIS);
        addExpectedMsg(16, 18, Messages.WARNING, Messages.CONDITIONAL + Messages.ENDS_WITH_PARENTHESIS);
        addExpectedMsg(16, 13, Messages.WARNING, Messages.CONDITIONAL + Messages.STARTS_WITH_PARENTHESIS);
        addExpectedMsg(16, 18, Messages.WARNING, Messages.CONDITIONAL + Messages.ENDS_WITH_PARENTHESIS);
        addExpectedMsg(18, 11, Messages.WARNING, Messages.CONDITIONAL + Messages.STARTS_WITH_PARENTHESIS);
        addExpectedMsg(18, 16, Messages.WARNING, Messages.CONDITIONAL + Messages.ENDS_WITH_PARENTHESIS);
        addExpectedMsg(21, 12, Messages.WARNING, Messages.SWITCH_EXPRESSION + Messages.STARTS_WITH_PARENTHESIS);
        addExpectedMsg(21, 14, Messages.WARNING, Messages.SWITCH_EXPRESSION + Messages.ENDS_WITH_PARENTHESIS);
        addExpectedMsg(26, 8, Messages.WARNING, Messages.FOR_LOOP + Messages.ENDS_WITH_PARENTHESIS);
    }

    private void addExpectedMsg(int line, int column, String classification, String msg) {
        expectedMessages.add(
                Printer.genOutputStringForTest(
                        inputFile.getName(), line, column, classification, msg));
    }
}
