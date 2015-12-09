package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for force casts.
 */
@RunWith(MockitoJUnitRunner.class)
public class ForceCastTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--only=forced-type-cast" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(7, 18, Severity.WARNING);
        addExpectedMsg(10, 20, Severity.WARNING);
        addExpectedMsg(14, 38, Severity.WARNING);
        addExpectedMsg(19, 97, Severity.WARNING);
        addExpectedMsg(21, 133, Severity.WARNING);
        addExpectedMsg(22, 54, Severity.WARNING);
        addExpectedMsg(23, 53, Severity.WARNING);
    }

    private void addExpectedMsg(int line, int column, Severity classification) {
        expectedMessages.add(Printer.genOutputStringForTest(Rules.FORCED_TYPE_CAST, inputFile.getName(), line, column,
            classification, Messages.FORCE_CAST));
    }

}
