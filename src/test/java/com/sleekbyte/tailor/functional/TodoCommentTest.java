package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for TodoCommentListener.
 */
@RunWith(MockitoJUnitRunner.class)
public class TodoCommentTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1);
        addExpectedMsg(4);
        addExpectedMsg(6);
        addExpectedMsg(9);
        addExpectedMsg(27);
        addExpectedMsg(29);
        addExpectedMsg(31);
        addExpectedMsg(33);
        addExpectedMsg(35);
        addExpectedMsg(37);
        addExpectedMsg(39);
        addExpectedMsg(42);
    }

    private void addExpectedMsg(int line) {
        expectedMessages.add(
            Printer.genOutputStringForTest(Rules.TODO_SYNTAX, inputFile.getName(), line, Severity.WARNING,
                Messages.TODOS + Messages.TODO_SYNTAX));
    }

}
