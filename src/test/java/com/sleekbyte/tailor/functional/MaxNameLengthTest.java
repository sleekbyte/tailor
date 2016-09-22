package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for max name length rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaxNameLengthTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[] {
            "--max-line-length", "40",
            "--max-name-length", "5",
            "--only=max-line-length,max-name-length"
        };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedNameMsg(1, 7, Messages.CLASS + Messages.NAME, 23, 5);
        addExpectedNameMsg(4, 5, Messages.CONSTANT + Messages.NAME, 22, 5);
        addExpectedLineMsg(4, 41, Messages.LINE, 75, 40);
        addExpectedNameMsg(4, 42, Messages.ELEMENT + Messages.NAME, 19, 5);
        addExpectedNameMsg(6, 6, Messages.ENUM + Messages.NAME, 21, 5);
        addExpectedNameMsg(7, 13, Messages.ENUM_CASE + Messages.NAME, 24, 5);
        addExpectedLineMsg(7, 41, Messages.LINE, 46, 40);
        addExpectedNameMsg(10, 6, Messages.FUNCTION + Messages.NAME, 23, 5);
        addExpectedNameMsg(10, 30, Messages.CONSTANT + Messages.NAME, 25, 5); // external param
        addExpectedLineMsg(10, 41, Messages.LINE, 94, 40);
        addExpectedNameMsg(10, 56, Messages.CONSTANT + Messages.NAME, 23, 5); // local param
        addExpectedNameMsg(15, 1, Messages.LABEL + Messages.NAME, 13, 5);
        addExpectedNameMsg(24, 10, Messages.PROTOCOL + Messages.NAME, 19, 5);
        addExpectedNameMsg(27, 8, Messages.STRUCT + Messages.NAME, 21, 5);
        addExpectedNameMsg(32, 13, Messages.SETTER + Messages.NAME, 16, 5);
        addExpectedNameMsg(38, 11, Messages.TYPEALIAS + Messages.NAME, 19, 5);
        addExpectedNameMsg(38, 33, Messages.TYPE + Messages.NAME, 6, 5);
        addExpectedNameMsg(41, 9, Messages.VARIABLE + Messages.NAME, 19, 5);
        addExpectedNameMsg(48, 6, Messages.FUNCTION + Messages.NAME, 24, 5);
        addExpectedNameMsg(48, 31, Messages.CONSTANT + Messages.NAME, 25, 5); // external param
        addExpectedLineMsg(48, 41, Messages.LINE, 95, 40);
        addExpectedNameMsg(48, 57, Messages.CONSTANT + Messages.NAME, 23, 5); // local param
        addExpectedNameMsg(52, 5, Messages.CONSTANT + Messages.NAME, 23, 5);
        addExpectedNameMsg(53, 5, Messages.VARIABLE + Messages.NAME, 23, 5);
        addExpectedNameMsg(55, 6, Messages.ENUM + Messages.NAME, 8, 5);
        addExpectedNameMsg(56, 10, Messages.ENUM_CASE + Messages.NAME, 20, 5);
        addExpectedNameMsg(57, 18, Messages.TYPE + Messages.NAME, 8, 5);
    }

    private void addExpectedNameMsg(int line, int column, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(Rules.MAX_NAME_LENGTH, inputFile.getName(), line, column,
            Severity.WARNING, msg));
    }

    private void addExpectedLineMsg(int line, int column, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(Rules.MAX_LINE_LENGTH, inputFile.getName(), line, column,
            Severity.WARNING, msg));
    }

}
