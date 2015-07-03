package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
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
    protected void addAllExpectedMsgs() {
        addExpectedMsg(3, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES);
        addExpectedMsg(7, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES);
        addExpectedMsg(24, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(25, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(26, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(42, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(43, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(46, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(47, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(50, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES);
        addExpectedMsg(55, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(63, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES);
        addExpectedMsg(72, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES);
        addExpectedMsg(76, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES);
        addExpectedMsg(90, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(94, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
        addExpectedMsg(98, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(
                inputFile.getName(), line, column, severity, msg + Messages.UPPER_CAMEL_CASE));
    }

}
