package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FunctionNewlinePaddingTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedFunctionMsg(8, 2, Messages.BLANK_LINE_AFTER);
        addExpectedFunctionMsg(9, 1, Messages.BLANK_LINE_BEFORE);
        addExpectedFunctionMsg(45, 3, Messages.BLANK_LINE_BEFORE);
        addExpectedFunctionMsg(51, 4, Messages.BLANK_LINE_AFTER);
        addExpectedFunctionMsg(53, 3, Messages.BLANK_LINE_BEFORE);
        addExpectedFunctionMsg(59, 4, Messages.BLANK_LINE_AFTER);
        addExpectedMsg(70, 4, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedFunctionMsg(87, 2, Messages.BLANK_LINE_AFTER);
        addExpectedFunctionMsg(88, 1, Messages.BLANK_LINE_BEFORE);
    }

    private void addExpectedFunctionMsg(int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, Severity.WARNING,
            Messages.FUNCTION + msg));
    }

    private void addExpectedMsg(int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, Severity.WARNING,
            msg));
    }
}
