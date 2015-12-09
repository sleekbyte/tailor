package com.sleekbyte.tailor.functional;


import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for {@link com.sleekbyte.tailor.listeners.RedundantSelfListener}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class RedundantSelfTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[] {
            "--only", "redundant-self"
        };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(13, 12);
        addExpectedMsg(14, 19);
        addExpectedMsg(22, 15);
        addExpectedMsg(27, 9);
        addExpectedMsg(27, 19);
        addExpectedMsg(40, 9);
    }

    private void addExpectedMsg(int line, int column) {
        expectedMessages.add(Printer.genOutputStringForTest(Rules.REDUNDANT_SELF, inputFile.getName(), line, column,
            Severity.WARNING, Messages.EXPLICIT_CALL_TO_SELF));
    }
}
