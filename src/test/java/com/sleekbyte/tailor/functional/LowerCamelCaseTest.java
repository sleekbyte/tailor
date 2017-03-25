package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for lower camel case rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class LowerCamelCaseTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--only=lower-camel-case" };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(7, 5, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(8, 5, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(16, 14, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(20,22, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(26, 8, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(30, 19, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(46, 16, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(55, 5, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(57, 9, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(64, 5, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(64, 8, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(70, 9, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(71, 9, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(74, 5, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(77, 9, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(80, 17, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(85, 17, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(91, 9, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(163, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(164, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(165, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(176, 10, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(177, 10, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(190, 10, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(200, 10, Severity.WARNING, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(Rules.LOWER_CAMEL_CASE, inputFile.getName(), line,
            column, severity, msg));
    }

}
