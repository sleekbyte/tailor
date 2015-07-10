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
        // Operator declarations
        addExpectedMsg(3, 18, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(5, 17, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(7, 18, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(7, 18, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(9, 19, Messages.OPERATOR + Messages.SPACE_BEFORE);
        addExpectedMsg(11, 16, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(18, 16, Messages.OPERATOR + Messages.SPACE_BEFORE);

        // Colons in type annotations
        addExpectedMsg(20, 7, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(21, 6, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(24, 11, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(25, 17, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(28, 13, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(29, 14, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(32, 44, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(32, 44, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(33, 17, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(36, 13, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(36, 13, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(36, 37, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(40, 33, Messages.COLON + Messages.NO_SPACE_BEFORE);
    }

    private void addExpectedMsg(int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, Severity.WARNING,
            msg));
    }
}
