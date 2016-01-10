package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.listeners.OptionalBindingListener;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link OptionalBindingListener} rule.
 */
@RunWith(MockitoJUnitRunner.class)
public final class RedundantOptionalBindingTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMessage(16, 34, "var");
        addExpectedMessage(20, 23, "var");
        addExpectedMessage(20, 34, "var");
        addExpectedMessage(24, 23, "let");
        addExpectedMessage(24, 34, "let");
        addExpectedMessage(36, 10, "let");
    }

    private void addExpectedMessage(int line, int column, String construct) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(
                Rules.REDUNDANT_OPTIONAL_BINDING,
                inputFile.getName(),
                line,
                column,
                Severity.WARNING,
                Messages.CONSECUTIVE + "'" + construct + "' " + Messages.AT_COLUMN + column
                    + Messages.REDUNDANT_OPTIONAL_BINDING
            )
        );
    }
}
