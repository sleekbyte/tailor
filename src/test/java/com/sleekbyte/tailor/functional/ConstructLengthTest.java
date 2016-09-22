package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
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
    protected String[] getCommandArgs() {
        return new String[] {
            "--max-class-length", "8",
            "--max-closure-length", "6",
            "--max-file-length", "30",
            "--max-function-length", "3",
            "--max-struct-length", "1",
        };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(Rules.MAX_CLASS_LENGTH, 8, 16, Messages.CLASS, 16, 8);
        addExpectedMsg(Rules.MAX_FUNCTION_LENGTH, 11, 65, Messages.FUNCTION, 11, 3);
        addExpectedMsg(Rules.MAX_FUNCTION_LENGTH, 14, 35, Messages.FUNCTION, 5, 3);
        addExpectedMsg(Rules.MAX_CLOSURE_LENGTH, 28, 27, Messages.CLOSURE, 8, 6);
        addExpectedMsg(Rules.MAX_FILE_LENGTH, 31, Messages.FILE, 42, 30);
        addExpectedMsg(Rules.MAX_STRUCT_LENGTH, 39, 19, Messages.STRUCT, 3, 1);
    }

    private void addExpectedMsg(Rules rule, int line, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line,  Severity.WARNING, msg));
    }

    private void addExpectedMsg(Rules rule, int line, int column, String msg, int length,
                                int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(rule, inputFile.getName(), line, column,  Severity.WARNING,
            msg));
    }

}
