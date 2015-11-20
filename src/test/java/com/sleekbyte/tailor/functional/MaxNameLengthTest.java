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
        };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedNameMsg(1, 7, Severity.WARNING, Messages.CLASS + Messages.NAME, 23, 5);
        addExpectedNameMsg(4, 5, Severity.WARNING, Messages.CONSTANT + Messages.NAME, 22, 5);
        addExpectedLineMsg(4, 41, Severity.WARNING, Messages.LINE, 75, 40);
        addExpectedNameMsg(4, 42, Severity.WARNING, Messages.ELEMENT + Messages.NAME, 19, 5);
        addExpectedNameMsg(6, 6, Severity.WARNING, Messages.ENUM + Messages.NAME, 21, 5);
        addExpectedNameMsg(7, 13, Severity.WARNING, Messages.ENUM_CASE + Messages.NAME, 24, 5);
        addExpectedLineMsg(7, 41, Severity.WARNING, Messages.LINE, 46, 40);
        addExpectedNameMsg(10, 6, Severity.WARNING, Messages.FUNCTION + Messages.NAME, 23, 5);
        addExpectedNameMsg(10, 30, Severity.WARNING, Messages.CONSTANT + Messages.NAME, 25, 5); // external param
        addExpectedLineMsg(10, 41, Severity.WARNING, Messages.LINE, 94, 40);
        addExpectedNameMsg(10, 56, Severity.WARNING, Messages.CONSTANT + Messages.NAME, 23, 5); // local param
        addExpectedNameMsg(15, 1, Severity.WARNING, Messages.LABEL + Messages.NAME, 13, 5);
        addExpectedNameMsg(24, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAME, 19, 5);
        addExpectedNameMsg(27, 8, Severity.WARNING, Messages.STRUCT + Messages.NAME, 21, 5);
        addExpectedNameMsg(32, 13, Severity.WARNING, Messages.SETTER + Messages.NAME, 16, 5);
        addExpectedNameMsg(38, 11, Severity.WARNING, Messages.TYPEALIAS + Messages.NAME, 19, 5);
        addExpectedNameMsg(38, 33, Severity.WARNING, Messages.TYPE + Messages.NAME, 6, 5);
        addExpectedNameMsg(41, 9, Severity.WARNING, Messages.VARIABLE + Messages.NAME, 19, 5);
        addExpectedNameMsg(48, 6, Severity.WARNING, Messages.FUNCTION + Messages.NAME, 24, 5);
        addExpectedNameMsg(48, 35, Severity.WARNING, Messages.VARIABLE + Messages.NAME, 25, 5); // external param
        addExpectedLineMsg(48, 41, Severity.WARNING, Messages.LINE, 99, 40);
        addExpectedNameMsg(48, 61, Severity.WARNING, Messages.VARIABLE + Messages.NAME, 23, 5); // local param
        addExpectedNameMsg(52, 5, Severity.WARNING, Messages.CONSTANT + Messages.NAME, 23, 5);
        addExpectedNameMsg(53, 5, Severity.WARNING, Messages.VARIABLE + Messages.NAME, 23, 5);
        addExpectedNameMsg(55, 6, Severity.WARNING, Messages.ENUM + Messages.NAME, 8, 5);
        addExpectedNameMsg(56, 10, Severity.WARNING, Messages.ENUM_CASE + Messages.NAME, 20, 5);
        addExpectedNameMsg(57, 18, Severity.WARNING, Messages.TYPE + Messages.NAME, 8, 5);
    }

    private void addExpectedNameMsg(int line, int column, Severity severity, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(Rules.MAX_NAME_LENGTH, inputFile.getName(), line, column,
            severity, msg));
    }

    private void addExpectedLineMsg(int line, int column, Severity severity, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(Rules.MAX_LINE_LENGTH, inputFile.getName(), line, column,
            severity, msg));
    }

}
