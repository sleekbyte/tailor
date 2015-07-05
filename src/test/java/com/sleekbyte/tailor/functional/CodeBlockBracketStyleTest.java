package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional test for code block bracket style.
 */
@RunWith(MockitoJUnitRunner.class)
public class CodeBlockBracketStyleTest extends RuleTest {
    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(7, 5, Severity.WARNING, Messages.INITIALIZER_BODY);
        addExpectedMsg(14, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(19, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(27, 9, Severity.WARNING, Messages.IF_STATEMENT);
        addExpectedMsg(32, 9, Severity.WARNING, Messages.IF_STATEMENT);
        addExpectedMsg(37, 9, Severity.WARNING, Messages.ELSE_CLAUSE);
        addExpectedMsg(50, 9, Severity.WARNING, Messages.SWITCH_STATEMENT);
        addExpectedMsg(82, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(97, 9, Severity.WARNING, Messages.WHILE_STATEMENT);
        addExpectedMsg(102, 9, Severity.WARNING, Messages.REPEAT_WHILE_STATEMENT);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, severity,
                                                               msg + Messages.BRACKET_STYLE));
    }
}

