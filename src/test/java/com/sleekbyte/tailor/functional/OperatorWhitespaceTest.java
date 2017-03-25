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
        addExpectedMsg(7, 18, Messages.SPACE_BEFORE);
        addExpectedMsg(12, 16, Messages.SPACE_BEFORE);
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
