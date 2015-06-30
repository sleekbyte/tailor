package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for construct length rule
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
        addExpectedMsg(8, 16, Messages.ERROR, Messages.CLASS, 12, 8);
        addExpectedMsg(10, 67, Messages.ERROR, Messages.FUNCTION, 9, 3);
        addExpectedMsg(12, 35, Messages.ERROR, Messages.FUNCTION, 5, 3);
        addExpectedMsg(24, 27, Messages.ERROR, Messages.CLOSURE, 8, 6);
        addExpectedMsg(31, Messages.ERROR, Messages.FILE, 38, 30);
        addExpectedMsg(35, 19, Messages.ERROR, Messages.STRUCT, 3, 1);
    }

    private void addExpectedMsg(int line, String classification, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, classification, msg));
    }

    private void addExpectedMsg(int line, int column, String classification, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }

}
