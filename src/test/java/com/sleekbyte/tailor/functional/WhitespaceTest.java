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
        addExpectedMsg(21, 1, Messages.OPERATOR + Messages.SPACE_AFTER);
        addExpectedMsg(26, 5, Messages.OPERATOR + Messages.SPACE_AFTER);

        // Colons in type annotations
        int start = 35;
        addExpectedMsg(start, 7, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(start + 1, 6, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(start + 4, 11, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(start + 5, 17, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(start + 8, 13, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(start + 9, 14, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(start + 12, 44, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(start + 12, 44, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(start + 13, 17, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(start + 16, 13, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(start + 16, 13, Messages.COLON + Messages.NO_SPACE_BEFORE);
        addExpectedMsg(start + 16, 37, Messages.COLON + Messages.SPACE_AFTER);
        addExpectedMsg(start + 20, 33, Messages.COLON + Messages.NO_SPACE_BEFORE);

        // Operator declarations

    }

    private void addExpectedMsg(int line, int column, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, Severity.WARNING,
            msg));
    }
}
