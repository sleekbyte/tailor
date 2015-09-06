package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for multiple imports rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class MultipleImportTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1, Severity.WARNING, Messages.IMPORTS + Messages.MULTIPLE_IMPORTS);
        addExpectedMsg(1, 18, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(1, 39, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(3, Severity.WARNING, Messages.IMPORTS + Messages.MULTIPLE_IMPORTS);
        addExpectedMsg(3, 16, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(3, 30, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(3, 41, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(3, 62, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(3, 75, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(4, Severity.WARNING, Messages.IMPORTS + Messages.MULTIPLE_IMPORTS);
        addExpectedMsg(4, 14, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(4, 32, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(7, 11, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(9, Severity.WARNING, Messages.IMPORTS + Messages.MULTIPLE_IMPORTS);
        addExpectedMsg(9, 18, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(14, 1, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(18, Severity.WARNING, Messages.IMPORTS + Messages.MULTIPLE_IMPORTS);
        addExpectedMsg(18, 1, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(18, 14, Severity.WARNING, Messages.STATEMENTS + Messages.SEMICOLON);
    }

    private void addExpectedMsg(int line, int column, Severity severity, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(Rules.TERMINATING_SEMICOLON, inputFile.getName(),
            line, column, severity, msg));
    }

    private void addExpectedMsg(int line, Severity severity, String msg) {
        this.expectedMessages.add(Printer.genOutputStringForTest(Rules.MULTIPLE_IMPORTS, inputFile.getName(), line,
            severity, msg));
    }

}
