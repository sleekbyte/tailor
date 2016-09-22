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
        start = 31;
        addExpectedMsg(start + 4, 38);
        addExpectedMsg(start + 25, 22);
        addExpectedMsg(start + 29, 17);
        addExpectedMsg(start + 39, 17);
        addExpectedMsg(start + 43, 26);
        addExpectedMsg(start + 50, 71);

        // classes and structs with generic types
        start = 93;
        addExpectedMsg(start, 6);
        addExpectedMsg(start + 3, 4);

        // protocols
        start = 102;
        addExpectedMsg(start, 24);
        addExpectedMsg(start + 4, 32);
        addExpectedMsg(start + 13, 24);

        // enums
        start = 129;
        addExpectedMsg(start, 16);
        addExpectedMsg(start + 4, 27);
        addExpectedMsg(start + 17, 6);
        addExpectedMsg(start + 26, 24);
        addExpectedMsg(start + 34, 19);

        // closures
        start = 167;
        addExpectedMsg(start, 29);
        addExpectedMsg(start + 14, 74);
        addExpectedMsg(start + 18, 17);

        // extensions
        start = 203;
        addExpectedMsg(start, 26);
        addExpectedMsg(start + 4, 29);
        addExpectedMsg(start + 9, 19);

        // getters and setters
        start = 217;
        addExpectedMsg(start, 8);
        addExpectedMsg(start + 4, 8);
        addExpectedMsg(start + 21, 14);
        addExpectedMsg(start + 32, 21);
        addExpectedMsg(start + 45, 24);
        addExpectedMsg(start + 51, 25);

        // initializers (Issue #405)
        start = 275;
        addExpectedMsg(start, 52);
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
        return new String[] { "--only=brace-style" };
    }
}
