package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BraceWhitespaceTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        // Whitespace before open braces

        // functions
        int start = 5;
        addExpectedMsg(start, 44);
        addExpectedMsg(start + 10, 34);

        // if statements
        start = 22;
        addExpectedMsg(start, 23);
        addExpectedMsg(start + 3, 26);
        addExpectedMsg(start + 6, 15);

        // loops
        start = 35;
        addExpectedMsg(start, 53);
        addExpectedMsg(start + 4, 38);
        addExpectedMsg(start + 29, 22);
        addExpectedMsg(start + 33, 17);
        addExpectedMsg(start + 43, 17);
        addExpectedMsg(start + 47, 26);
        addExpectedMsg(start + 54, 22);
        addExpectedMsg(start + 70, 20);
        addExpectedMsg(start + 75, 18);

        // classes and structs with generic types
        start = 122;
        addExpectedMsg(start, 6);
        addExpectedMsg(start + 3, 4);

        // protocols
        start = 131;
        addExpectedMsg(start, 24);
        addExpectedMsg(start + 4, 32);
        addExpectedMsg(start + 13, 24);

        // enums
        start = 158;
        addExpectedMsg(start, 16);
        addExpectedMsg(start + 4, 27);
        addExpectedMsg(start + 17, 6);
        addExpectedMsg(start + 26, 24);
        addExpectedMsg(start + 34, 19);

        // closures
        start = 196;
        addExpectedMsg(start, 29);
        addExpectedMsg(start + 14, 72);
        addExpectedMsg(start + 18, 17);

        // extensions
        start = 232;
        addExpectedMsg(start, 26);
        addExpectedMsg(start + 4, 29);
        addExpectedMsg(start + 9, 19);

        // getters and setters
        start = 246;
        addExpectedMsg(start, 8);
        addExpectedMsg(start + 4, 8);
        addExpectedMsg(start + 21, 14);
        addExpectedMsg(start + 32, 21);
        addExpectedMsg(start + 45, 24);
        addExpectedMsg(start + 51, 25);
    }

    private void addExpectedMsg(int line, int column) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(
                Rules.BRACE_STYLE,
                inputFile.getName(),
                line,
                column,
                Severity.WARNING,
                Messages.OPEN_BRACE + Messages.SPACE_BEFORE
            )
        );
    }

    @Override
    protected String[] getCommandArgs() {
        return new String[] { "--only=brace-whitespace" };
    }
}
