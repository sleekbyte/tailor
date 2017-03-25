package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ColonWhitespaceTest extends RuleTest {

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--only=colon-whitespace" };
    }

    @Override
    protected void addAllExpectedMsgs() {

        // Colons in type annotations
        int start = 1;
        addExpectedMessage(start, 7, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 1, 6, Messages.SPACE_AFTER);
        addExpectedMessage(start + 4, 11, Messages.SPACE_AFTER);
        addExpectedMessage(start + 5, 17, Messages.SPACE_AFTER);
        addExpectedMessage(start + 8, 13, Messages.SPACE_AFTER);
        addExpectedMessage(start + 9, 14, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 12, 44, Messages.SPACE_AFTER);
        addExpectedMessage(start + 12, 44, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 13, 17, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 16, 13, Messages.SPACE_AFTER);
        addExpectedMessage(start + 16, 13, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 16, 37, Messages.SPACE_AFTER);
        addExpectedMessage(start + 20, 33, Messages.NO_SPACE_BEFORE);

        // Colons in dictionaries
        start = 24;
        addExpectedMessage(start, 41, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 1, 25, Messages.SPACE_AFTER);
        addExpectedMessage(start + 3, 26, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 4, 19, Messages.SPACE_AFTER);
        addExpectedMessage(start + 5, 41, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 6, 1, Messages.NO_SPACE_BEFORE);

        // Switch case colons
        start = 32;
        addExpectedMessage(start + 1, 17, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 2, 16, Messages.SPACE_AFTER);
        addExpectedMessage(start + 4, 9, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 9, 34, Messages.SPACE_AFTER);
        addExpectedMessage(start + 10, 8, Messages.SPACE_AFTER);
        addExpectedMessage(start + 20, 11, Messages.SPACE_AFTER);
        addExpectedMessage(start + 22, 10, Messages.SPACE_AFTER);

        // Type Inheritance clause colons

        // classes
        start = 57;
        addExpectedMessage(start, 18, Messages.SPACE_AFTER);
        addExpectedMessage(start + 8, 16, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 14, 16, Messages.NO_SPACE_BEFORE);

        // structs
        start = 75;
        addExpectedMessage(start, 21, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 3, 20, Messages.SPACE_AFTER);
        addExpectedMessage(start + 7, 18, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 10, 19, Messages.SPACE_AFTER);
        addExpectedMessage(start + 13, 21, Messages.NO_SPACE_BEFORE);

        // enums
        start = 96;
        addExpectedMessage(start, 14, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 4, 18, Messages.SPACE_AFTER);
        addExpectedMessage(start + 11, 14, Messages.NO_SPACE_BEFORE);

        // protocols
        start = 120;
        addExpectedMessage(start, 31, Messages.SPACE_AFTER);
        addExpectedMessage(start + 3, 32, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 9, 28, Messages.SPACE_AFTER);
        addExpectedMessage(start + 12, 29, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 15, 28, Messages.SPACE_AFTER);
        addExpectedMessage(start + 22, 1, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 26, 1, Messages.SPACE_AFTER);
        addExpectedMessage(start + 26, 1, Messages.NO_SPACE_BEFORE);

        // extensions
        start = 149;
        addExpectedMessage(start + 3, 20, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 6, 19, Messages.SPACE_AFTER);

        // ternary operator
        start = 158;
        addExpectedMessage(start, 48, Messages.SPACE_AFTER);
        addExpectedMessage(start, 48, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 1, 14, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 2, 15, Messages.SPACE_AFTER);
        addExpectedMessage(start + 4, 30, Messages.SPACE_BEFORE);
        addExpectedMessage(start + 5, 31, Messages.SPACE_AFTER);
        addExpectedMessage(start + 7, 52, Messages.SPACE_BEFORE);

        // tuples
        start = 167;
        addExpectedMessage(start + 1, 33, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 1, 51, Messages.SPACE_AFTER);
        addExpectedMessage(start + 2, 32, Messages.SPACE_AFTER);
        addExpectedMessage(start + 3, 45, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 3, 83, Messages.SPACE_AFTER);

        // generics
        start = 172;
        addExpectedMessage(start + 3, 21, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 3, 35, Messages.SPACE_AFTER);
        addExpectedMessage(start + 6, 20, Messages.SPACE_AFTER);
        addExpectedMessage(start + 10, 8, Messages.NO_SPACE_BEFORE);

        // functionCallExpression / explicitMemberExpression
        start = 188;
        addExpectedMessage(start, 28, Messages.SPACE_AFTER);
        addExpectedMessage(start + 1, 29, Messages.NO_SPACE_BEFORE);
        addExpectedMessage(start + 3, 27, Messages.NO_SPACE_BEFORE);

    }

    private void addExpectedMessage(int line, int column, String msg) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(
                Rules.COLON_WHITESPACE,
                inputFile.getName(),
                line,
                column,
                Severity.WARNING,
                Messages.COLON + Messages.AT_COLUMN + column + " " + msg
            )
        );
    }
}
