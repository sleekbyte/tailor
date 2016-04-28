package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class TrailingClosureTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[] { "--only=trailing-closure" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMessage(3, 23);
        addExpectedMessage(5, 41);
        addExpectedMessage(12, 3);
        addExpectedMessage(30, 18);
    }

    private void addExpectedMessage(int line, int column) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(
                Rules.TRAILING_CLOSURE,
                inputFile.getName(),
                line,
                column,
                Severity.WARNING,
                Messages.CLOSURE + Messages.TRAILING_CLOSURE
            )
        );
    }

}
