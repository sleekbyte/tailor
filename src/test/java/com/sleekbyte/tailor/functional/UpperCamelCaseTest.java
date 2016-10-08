package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for UpperCamelCase rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpperCamelCaseTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--only=upper-camel-case" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(3, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES);
        addExpectedMsg(7, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES);
        addExpectedMsg(42, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(46, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(50, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(72, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES);
        addExpectedMsg(76, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES);
        addExpectedMsg(90, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(94, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(98, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(119, 18, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(119, 23, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(128, 20, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
        addExpectedMsg(137, 14, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(Rules.UPPER_CAMEL_CASE, inputFile.getName(), line, column, severity,
                msg + Messages.UPPER_CAMEL_CASE));
    }

}
