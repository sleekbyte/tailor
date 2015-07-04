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
            "--max-severity", "error",
            "--max-line-length", "40",
            "--max-name-length", "5",
            inputFile.getPath()
        };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1, 7, Severity.ERROR, Messages.CLASS + Messages.NAME, 23, 5);
        addExpectedMsg(4, 40, Severity.ERROR, Messages.ELEMENT + Messages.NAME, 19, 5);
        addExpectedMsg(4, 41, Severity.ERROR, Messages.LINE, 72, 40);
        addExpectedMsg(6, 6, Severity.ERROR, Messages.ENUM + Messages.NAME, 21, 5);
        addExpectedMsg(7, 13, Severity.ERROR, Messages.ENUM_CASE + Messages.NAME, 24, 5);
        addExpectedMsg(7, 41, Severity.ERROR, Messages.LINE, 46, 40);
        addExpectedMsg(10, 6, Severity.ERROR, Messages.FUNCTION + Messages.NAME, 23, 5);
        addExpectedMsg(10, 30, Severity.ERROR, Messages.EXTERNAL_PARAMETER + Messages.NAME, 25, 5);
        addExpectedMsg(10, 41, Severity.ERROR, Messages.LINE, 94, 40);
        addExpectedMsg(10, 56, Severity.ERROR, Messages.LOCAL_PARAMETER + Messages.NAME, 23, 5);
        addExpectedMsg(15, 1, Severity.ERROR, Messages.LABEL + Messages.NAME, 13, 5);
        addExpectedMsg(24, 10, Severity.ERROR, Messages.PROTOCOL + Messages.NAME, 19, 5);
        addExpectedMsg(27, 8, Severity.ERROR, Messages.STRUCT + Messages.NAME, 21, 5);
        addExpectedMsg(32, 13, Severity.ERROR, Messages.SETTER + Messages.NAME, 16, 5);
        addExpectedMsg(38, 11, Severity.ERROR, Messages.TYPEALIAS + Messages.NAME, 19, 5);
        addExpectedMsg(38, 33, Severity.ERROR, Messages.TYPE + Messages.NAME, 6, 5);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, severity, msg));
    }

}
