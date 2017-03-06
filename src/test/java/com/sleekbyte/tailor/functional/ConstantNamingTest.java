package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
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
        addExpectedMsg(Rules.CONSTANT_NAMING, 2, 5, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT
            + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(Rules.CONSTANT_K_PREFIX, 2, 22, Severity.WARNING, Messages.CONSTANT + Messages.NAME
            + Messages.K_PREFIXED);
        addExpectedMsg(Rules.CONSTANT_K_PREFIX, 2, 37, Severity.WARNING, Messages.CONSTANT + Messages.NAME
            + Messages.K_PREFIXED);
        addExpectedMsg(Rules.CONSTANT_K_PREFIX, 5, 16, Severity.WARNING, Messages.CONSTANT + Messages.NAME
            + Messages.K_PREFIXED);
        addExpectedMsg(Rules.CONSTANT_NAMING, 5, 45, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 11, 13, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT
            + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(Rules.CONSTANT_NAMING, 12, 15, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT
            + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(Rules.CONSTANT_NAMING, 15, 9, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 20, 5, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT
            + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(Rules.CONSTANT_NAMING, 24, 5, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT
            + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(Rules.CONSTANT_NAMING, 29, 19, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 29, 33, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 30, 7, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 31, 7, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 33, 7, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 40, 14, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 50, 11, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 56, 5, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT
            + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(Rules.CONSTANT_NAMING, 67, 9, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT
            + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(Rules.CONSTANT_NAMING, 79, 9, Severity.WARNING, Messages.GLOBAL + Messages.CONSTANT
            + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(Rules.CONSTANT_K_PREFIX, 80, 16, Severity.WARNING, Messages.CONSTANT + Messages.NAME
            + Messages.K_PREFIXED);
        addExpectedMsg(Rules.CONSTANT_NAMING, 88, 17, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 95, 8, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 105, 14, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 112, 55, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 117, 65, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_NAMING, 118, 7, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(Rules.CONSTANT_K_PREFIX, 127, 16, Severity.WARNING, Messages.CONSTANT + Messages.NAME
            + Messages.K_PREFIXED);
        addExpectedMsg(Rules.CONSTANT_K_PREFIX, 128, 16, Severity.WARNING, Messages.CONSTANT + Messages.NAME
            + Messages.K_PREFIXED);
        addExpectedMsg(Rules.CONSTANT_K_PREFIX, 133, 20, Severity.WARNING, Messages.CONSTANT + Messages.NAME
            + Messages.K_PREFIXED);
        addExpectedMsg(Rules.CONSTANT_K_PREFIX, 134, 20, Severity.WARNING, Messages.CONSTANT + Messages.NAME
            + Messages.K_PREFIXED);
        addExpectedMsg(Rules.CONSTANT_NAMING, 152, 14, Severity.WARNING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE);
    }

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--only=constant-naming,constant-k-prefix" };
    }

    private void addExpectedMsg(Rules rule, int line, int column, Severity severity, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line, column, severity,
            msg));
    }

}
