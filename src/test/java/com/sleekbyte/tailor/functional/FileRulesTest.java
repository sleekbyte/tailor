package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for {@link com.sleekbyte.tailor.listeners.FileListener}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileRulesTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[] {
            "--max-file-length", "5",
            "--max-line-length", "15",
            inputFile.getPath()
        };
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1, 1, Severity.WARNING, Messages.FILE + Messages.LEADING_WHITESPACE);
        addExpectedMsg(3, 16, Severity.WARNING, Messages.LINE + Messages.EXCEEDS_CHARACTER_LIMIT
            + " (" + 18 + "/" + 15 + ")");
        addExpectedMsg(3, 18, Severity.WARNING, Messages.LINE + Messages.TRAILING_WHITESPACE);
        addExpectedMsg(6, Severity.WARNING, Messages.FILE + Messages.EXCEEDS_LINE_LIMIT + " (" + 11 + "/" + 5 + ")");
        addExpectedMsg(6, 16, Severity.WARNING, Messages.LINE + Messages.EXCEEDS_CHARACTER_LIMIT
            + " (" + 37 + "/" + 15 + ")");
        addExpectedMsg(6, 37, Severity.WARNING, Messages.LINE + Messages.TRAILING_WHITESPACE);
        addExpectedMsg(9, 16, Severity.WARNING, Messages.LINE + Messages.EXCEEDS_CHARACTER_LIMIT
            + " (" + 28 + "/" + 15 + ")");
        addExpectedMsg(11, Severity.WARNING, Messages.FILE + Messages.NEWLINE_TERMINATOR);
    }

    private void addExpectedMsg(int line, Severity classification, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, classification, msg));
    }

    private void addExpectedMsg(int line, int column, Severity classification, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }

}
