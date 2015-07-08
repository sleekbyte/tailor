package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for ternary operator parentheses rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class TernaryOperatorParenthesesTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(5, 35, Severity.WARNING, Messages.TERNARY_OPERATOR_CONDITION + Messages.TERNARY_PARENTHESES);
        addExpectedMsg(8, 35, Severity.WARNING, Messages.TERNARY_OPERATOR_CONDITION + Messages.TERNARY_PARENTHESES);
        addExpectedMsg(11, 35, Severity.WARNING, Messages.TERNARY_OPERATOR_CONDITION + Messages.TERNARY_PARENTHESES);
        addExpectedMsg(14, 43, Severity.WARNING, Messages.TERNARY_OPERATOR_CONDITION + Messages.TERNARY_PARENTHESES);
        addExpectedMsg(17, 32, Severity.WARNING, Messages.TERNARY_OPERATOR_CONDITION + Messages.TERNARY_PARENTHESES);
        addExpectedMsg(20, 19, Severity.WARNING, Messages.TERNARY_OPERATOR_CONDITION + Messages.TERNARY_PARENTHESES);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, severity, msg));
    }

}
