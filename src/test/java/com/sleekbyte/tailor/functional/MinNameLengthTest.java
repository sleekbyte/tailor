package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for min name length rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class MinNameLengthTest extends RuleTest {

    private static final int NAME_LENGTH_LIMIT = 3;

    @Override
    protected String[] getCommandArgs() {
        return new String[] {
            "--min-name-length", String.valueOf(NAME_LENGTH_LIMIT),
            "--only=min-name-length"
        };
    }

    @Override
    protected void addAllExpectedMsgs() {

        addExpectedNameMsg(1, 7, Messages.CLASS + Messages.NAME, 2);
        addExpectedNameMsg(7, 54, Messages.ENUM_CASE + Messages.NAME, 1);
        addExpectedNameMsg(10, 6, Messages.FUNCTION + Messages.NAME, 1);
        addExpectedNameMsg(10, 9, Messages.CONSTANT + Messages.NAME, 2);
        addExpectedNameMsg(10, 12, Messages.CONSTANT + Messages.NAME, 2);
        addExpectedNameMsg(15, 1, Messages.LABEL + Messages.NAME, 2);
        addExpectedNameMsg(24, 10, Messages.PROTOCOL + Messages.NAME, 1);
        addExpectedNameMsg(27, 8, Messages.STRUCT + Messages.NAME, 1);
        addExpectedNameMsg(38, 11, Messages.TYPEALIAS + Messages.NAME, 2);
        addExpectedNameMsg(41, 9, Messages.VARIABLE + Messages.NAME, 1);
        addExpectedNameMsg(48, 82, Messages.TYPE + Messages.NAME, 2);
        addExpectedNameMsg(52, 5, Messages.CONSTANT + Messages.NAME, 1);
        addExpectedNameMsg(56, 10, Messages.ENUM_CASE + Messages.NAME, 1);

    }

    private void addExpectedNameMsg(int line, int column, String msg, int length) {
        String lengthVersusLimit = " (" + length + "/" + NAME_LENGTH_LIMIT + ")";
        msg += Messages.VIOLATES_MINIMUM_CHARACTER_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(Rules.MIN_NAME_LENGTH, inputFile.getName(), line, column,
            Severity.WARNING, msg));
    }

}
