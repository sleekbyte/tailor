package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for constant naming rule
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstantNamingTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "ConstantNamingTest.swift";
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(2, 5, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(5, 16, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(5, 45, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(11, 13, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(12, 15, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(15, 9, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(20, 5, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(24, 5, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(29, 19, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(29, 33, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(30, 7, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(31, 7, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(33, 7, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(40, 14, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(50, 11, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(56, 5, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(67, 9, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(78, 9, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(87, 17, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(94, 8, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(104, 14, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(111, 51, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(116, 61, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(117, 3, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
    }

    private void addExpectedMsg(int line, int column, String classification, String msg) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }

}
