package com.sleekbyte.tailor.functional;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public final class PurgeTest extends RuleTest {

    @Override
    protected String getInputFilePath() {
        return "ColonWhitespaceTest.swift";
    }

    @Override
    protected void addAllExpectedMsgs() {
        ColonWhitespaceTest colonWhitespaceTest = new ColonWhitespaceTest();
        colonWhitespaceTest.inputFile = inputFile;
        colonWhitespaceTest.expectedMessages = new ArrayList<>();
        colonWhitespaceTest.addAllExpectedMsgs();
        this.expectedMessages.addAll(colonWhitespaceTest.expectedMessages);
    }

    @Override
    protected String[] getCommandArgs() {
        return new String[]{ "--purge", "1", "--only=colon-whitespace" };
    }
}
