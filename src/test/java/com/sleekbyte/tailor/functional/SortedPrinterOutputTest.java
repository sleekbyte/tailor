package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for sorted printer output
 */
@RunWith(MockitoJUnitRunner.class)
public class SortedPrinterOutputTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "SortedPrinterOutputTest.swift";
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(1, 18, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(3, 25, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(6, 15, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(7, 15, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(8, 14, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(9, 14, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(10, 2, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(13, 33, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(14, 29, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(15, 23, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(16, 39, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(17, 2, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(20, 8, Messages.ERROR, Messages.STRUCT + Messages.NAMES + Messages.UPPER_CAMEL_CASE);
        addExpectedMsg(21, 18, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(27, 10, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(31, 10, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
        addExpectedMsg(33, Messages.ERROR, Messages.FILE + Messages.NEWLINE_TERMINATOR);
        addExpectedMsg(33, 2, Messages.ERROR, Messages.STATEMENTS + Messages.SEMICOLON);
    }

    private void addExpectedMsg(int line, String classification, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, classification, msg));
    }

    private void addExpectedMsg(int line, int column, String classification, String msg) {
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }

}
