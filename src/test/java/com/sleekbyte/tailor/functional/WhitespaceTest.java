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
        addExpectedOperatorMsg(3, 18, Messages.SPACE_BEFORE);
        addExpectedOperatorMsg(5, 17, Messages.SPACE_AFTER);
        addExpectedOperatorMsg(7, 18, Messages.SPACE_AFTER);
        addExpectedOperatorMsg(7, 18, Messages.SPACE_BEFORE);
        addExpectedOperatorMsg(9, 19, Messages.SPACE_BEFORE);
        addExpectedOperatorMsg(11, 16, Messages.SPACE_AFTER);
        addExpectedOperatorMsg(18, 16, Messages.SPACE_BEFORE);
        addExpectedOperatorMsg(21, 1, Messages.SPACE_AFTER);
        addExpectedOperatorMsg(26, 5, Messages.SPACE_AFTER);
    }

    private void addExpectedOperatorMsg(int line, int column, String msg) {
        addExpectedMsg(Rules.OPERATOR_WHITESPACE, line, column, Messages.OPERATOR + msg);
    }

    private void addExpectedMsg(Rules rule, int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line, column,
            Severity.WARNING, msg));
    }
}
