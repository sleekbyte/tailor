package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OperatorWhitespaceTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        // Operator declarations
        addExpectedMsg(3, 18, Messages.SPACE_BEFORE);
        addExpectedMsg(5, 17, Messages.SPACE_AFTER);
        addExpectedMsg(7, 18, Messages.SPACE_AFTER);
        addExpectedMsg(7, 18, Messages.SPACE_BEFORE);
        addExpectedMsg(9, 19, Messages.SPACE_BEFORE);
        addExpectedMsg(11, 16, Messages.SPACE_AFTER);
        addExpectedMsg(18, 16, Messages.SPACE_BEFORE);
        addExpectedMsg(21, 1, Messages.SPACE_AFTER);
        addExpectedMsg(26, 5, Messages.SPACE_AFTER);
    }

    private void addExpectedMsg(int line, int column, String msg) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(
                Rules.OPERATOR_WHITESPACE,
                inputFile.getName(),
                line,
                column,
                Severity.WARNING,
                Messages.OPERATOR + msg
            )
        );
    }
}
