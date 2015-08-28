package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for constant naming rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstantNamingTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(2, 5, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(2, 22, Severity.WARNING, Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED);
        addExpectedMsg(2, 37, Severity.WARNING, Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED);
        addExpectedMsg(5, 16, Severity.WARNING, Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED);
        addExpectedMsg(5, 16, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(5, 45, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(11, 13, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(12, 15, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(15, 9, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(20, 5, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(24, 5, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(29, 19, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(29, 33, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(30, 7, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(31, 7, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(33, 7, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(40, 14, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(50, 11, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(56, 5, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(67, 9, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(79, 9, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(80, 16, Severity.WARNING, Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED);
        addExpectedMsg(88, 17, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(95, 8, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(105, 14, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(112, 51, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(116, 51, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(117, 3, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(117, 61, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(118, 3, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(122, 8, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(122, 57, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(127, 16, Severity.WARNING, Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED);
        addExpectedMsg(128, 16, Severity.WARNING, Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED);
        addExpectedMsg(133, 20, Severity.WARNING, Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED);
        addExpectedMsg(134, 20, Severity.WARNING, Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED);
        addExpectedMsg(134, 20, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, severity, msg));
    }

}
