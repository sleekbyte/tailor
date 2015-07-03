package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Functional tests for constant naming rule.
 */
@RunWith(MockitoJUnitRunner.class)
public class LowerCamelCaseTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "LowerCamelCaseTest.swift";
    }

    @Override
    protected void addAllExpectedMsgs() {
        addExpectedMsg(2, 9, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(7, 5, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(8, 5, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(10, 6, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(10, 10, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(16, 14, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(20,22, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(26, 8, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(26, 108, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(30, 19, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(46, 16, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(50, 20, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(54, 5, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(55, 10, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
        addExpectedMsg(56, 9, Messages.ERROR, Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE);
    }

    private void addExpectedMsg(int line, int column, String classification, String msg) {
        this.expectedMessages.add(
            Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }

}
