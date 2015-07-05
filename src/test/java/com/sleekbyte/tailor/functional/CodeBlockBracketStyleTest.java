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
        addExpectedMsg(14, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(19, 5, Severity.WARNING, Messages.FUNCTION);
        addExpectedMsg(82, 5, Severity.WARNING, Messages.FUNCTION);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, severity,
                                                               msg + Messages.BRACKET_STYLE));
    }
}

