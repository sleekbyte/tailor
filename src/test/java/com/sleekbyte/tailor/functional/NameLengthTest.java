package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for name length rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class NameLengthTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[] {
            "--max-line-length", "40",
            "--max-name-length", "5",
            inputFile.getPath()
        };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1, 7, Severity.WARNING, Messages.CLASS + Messages.NAME, 23, 5);
        addExpectedMsg(4, 5, Severity.WARNING, Messages.CONSTANT + Messages.NAME, 22, 5);
        addExpectedMsg(4, 40, Severity.WARNING, Messages.ELEMENT + Messages.NAME, 19, 5);
        addExpectedMsg(4, 41, Severity.WARNING, Messages.LINE, 72, 40);
        addExpectedMsg(6, 6, Severity.WARNING, Messages.ENUM + Messages.NAME, 21, 5);
        addExpectedMsg(7, 13, Severity.WARNING, Messages.ENUM_CASE + Messages.NAME, 24, 5);
        addExpectedMsg(7, 41, Severity.WARNING, Messages.LINE, 46, 40);
        addExpectedMsg(10, 6, Severity.WARNING, Messages.FUNCTION + Messages.NAME, 23, 5);
        addExpectedMsg(10, 30, Severity.WARNING, Messages.CONSTANT + Messages.NAME, 25, 5); // external param
        addExpectedMsg(10, 41, Severity.WARNING, Messages.LINE, 94, 40);
        addExpectedMsg(10, 56, Severity.WARNING, Messages.CONSTANT + Messages.NAME, 23, 5); // local param
        addExpectedMsg(15, 1, Severity.WARNING, Messages.LABEL + Messages.NAME, 13, 5);
        addExpectedMsg(24, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAME, 19, 5);
        addExpectedMsg(27, 8, Severity.WARNING, Messages.STRUCT + Messages.NAME, 21, 5);
        addExpectedMsg(32, 13, Severity.WARNING, Messages.SETTER + Messages.NAME, 16, 5);
        addExpectedMsg(38, 11, Severity.WARNING, Messages.TYPEALIAS + Messages.NAME, 19, 5);
        addExpectedMsg(38, 33, Severity.WARNING, Messages.TYPE + Messages.NAME, 6, 5);
        addExpectedMsg(41, 9, Severity.WARNING, Messages.VARIABLE + Messages.NAME, 19, 5);
        addExpectedMsg(48, 6, Severity.WARNING, Messages.FUNCTION + Messages.NAME, 24, 5);
        addExpectedMsg(48, 35, Severity.WARNING, Messages.VARIABLE + Messages.NAME, 25, 5); // external param
        addExpectedMsg(48, 41, Severity.WARNING, Messages.LINE, 99, 40);
        addExpectedMsg(48, 61, Severity.WARNING, Messages.VARIABLE + Messages.NAME, 23, 5); // local param
        addExpectedMsg(52, 5, Severity.WARNING, Messages.CONSTANT + Messages.NAME, 23, 5);
        addExpectedMsg(53, 5, Severity.WARNING, Messages.VARIABLE + Messages.NAME, 23, 5);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, severity, msg));
    }

}
