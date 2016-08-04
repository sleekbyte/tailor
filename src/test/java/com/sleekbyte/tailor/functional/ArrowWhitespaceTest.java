package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArrowWhitespaceTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[] { "--only=arrow-whitespace" };
    }

    @Override
    protected void addAllExpectedMsgs() {

        // Function Result arrow
        int start = 5;
        addExpectedMessage(start, 18, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 4, 19, Messages.SPACE_AFTER);
        addExpectedMessage(start + 8, 20, Messages.SPACE_AFTER);
        addExpectedMessage(start + 8, 20, Messages.SPACE_BEFORE);

        // Closure result arrow
        start = 18;
        addExpectedMessage(start, 9, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 5, 10, Messages.SPACE_AFTER);
        addExpectedMessage(start + 10, 19, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 15, 18, Messages.SPACE_AFTER);

        // Function type arrow
        start = 37;
        addExpectedMessage(start, 21, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 4, 22, Messages.SPACE_AFTER);
        addExpectedMessage(start + 8, 40, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 12, 32, Messages.SPACE_AFTER);

        // Subscript result arrow
        start = 60;
        addExpectedMessage(start, 26, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 4, 27, Messages.SPACE_AFTER);
    }

    private void addExpectedMessage(int line, int column, String msg) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(
                Rules.ARROW_WHITESPACE,
                inputFile.getName(),
                line,
                column,
                Severity.WARNING,
                Messages.RETURN_ARROW + Messages.AT_COLUMN + column + " " + msg
            )
        );
    }

}
