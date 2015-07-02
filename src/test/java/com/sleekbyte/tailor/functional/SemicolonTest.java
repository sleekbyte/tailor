package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for semicolon rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class SemicolonTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "SemicolonTest.swift";
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1, 18, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(10, 2, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(6, 15, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(7, 15, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(8, 14, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(9, 14, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(17, 2, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(13, 33, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(14, 16, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(15, 23, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(16, 39, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(21, 2, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(42, 2, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(24, 18, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(41, 6, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(30, 10, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(35, 10, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(40, 23, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(64, 2, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(47, 28, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(48, 14, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(63, 6, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(54, 10, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(53, 21, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(57, 65, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(59, 73, Messages.ERROR, Messages.STATEMENTS);
        addExpectedMsg(61, 59, Messages.ERROR, Messages.STATEMENTS);
    }

    private void addExpectedMsg(int line, int column, String classification, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(
                inputFile.getName(), line, column, classification, msg + Messages.SEMICOLON));
    }

}
