package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Functional tests for construct length rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructLengthTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return Stream.concat(Arrays.stream(super.getCommandArgs()), Arrays.stream(new String[] {
            "--max-class-length", "8",
            "--max-closure-length", "6",
            "--max-file-length", "30",
            "--max-function-length", "3",
            "--max-struct-length", "1",
        })).toArray(String[]::new);
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(8, 16, Severity.WARNING, Messages.CLASS, 16, 8);
        addExpectedMsg(11, 67, Severity.WARNING, Messages.FUNCTION, 11, 3);
        addExpectedMsg(14, 35, Severity.WARNING, Messages.FUNCTION, 5, 3);
        addExpectedMsg(28, 27, Severity.WARNING, Messages.CLOSURE, 8, 6);
        addExpectedMsg(31, Severity.WARNING, Messages.FILE, 42, 30);
        addExpectedMsg(39, 19, Severity.WARNING, Messages.STRUCT, 3, 1);
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
