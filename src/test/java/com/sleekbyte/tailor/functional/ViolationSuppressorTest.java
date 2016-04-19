package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for ViolationSuppressor.
 */
@RunWith(MockitoJUnitRunner.class)
public final class ViolationSuppressorTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--only=upper-camel-case" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        Severity severity = Severity.WARNING;
        expectedMessages.add(Printer.genOutputStringForTest(Rules.UPPER_CAMEL_CASE, inputFile.getName(), 29, 7,
            severity, Messages.CLASS + Messages.NAMES + Messages.UPPER_CAMEL_CASE));
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), 33, 1, severity,
            Messages.ON_OFF_MISMATCH));
    }
}
