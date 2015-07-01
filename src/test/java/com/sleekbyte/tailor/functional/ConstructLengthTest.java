package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for construct length rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructLengthTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "ConstructLengthTest.swift";
    }

    @Override
    protected String[] getCommandArgs() {
        return new String[] {
            "--max-class-length", "8",
            "--max-closure-length", "6",
            "--max-file-length", "30",
            "--max-function-length", "3",
            "--max-struct-length", "1",
            inputFile.getPath()
        };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(8, 16, Severity.ERROR, Messages.CLASS, 12, 8);
        addExpectedMsg(10, 67, Severity.ERROR, Messages.FUNCTION, 9, 3);
        addExpectedMsg(12, 35, Severity.ERROR, Messages.FUNCTION, 5, 3);
        addExpectedMsg(24, 27, Severity.ERROR, Messages.CLOSURE, 8, 6);
        addExpectedMsg(31, Severity.ERROR, Messages.FILE, 38, 30);
        addExpectedMsg(35, 19, Severity.ERROR, Messages.STRUCT, 3, 1);
    }

    private void addExpectedMsg(int line, Severity severity, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, severity, msg));
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, severity, msg));
    }

}
