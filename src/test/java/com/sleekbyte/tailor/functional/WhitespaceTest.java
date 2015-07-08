package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WhitespaceTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(3, 18, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(5, 17, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(7, 18, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(7, 18, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(9, 19, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(11, 16, Messages.OPERATOR + Messages.SPACE_AFTER);
    }

    private void addExpectedMsg(int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, Severity.WARNING, msg));
    }
}
