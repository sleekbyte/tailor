package com.sleekbyte.tailor.functional;

import com.sleekbyte.tailor.Tailor;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Functional tests for UpperCamelCase rule
 */
@RunWith(MockitoJUnitRunner.class)
public class UpperCamelCaseTest {

    private static final String NEWLINE_REGEX = "\\r?\\n";

    private ByteArrayOutputStream outContent;
    private File inputFile = new File("src/test/java/com/sleekbyte/tailor/functional/UpperCamelCaseTests.swift");
    private Set<String> expectedMessages = new HashSet<>();

    @Before
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testUpperCamelCase() throws IOException {
        String[] command = { inputFile.getPath() };

        addExpectedMsg(Messages.CLASS_NAME, 3, 7, "error");
        addExpectedMsg(Messages.CLASS_NAME, 7, 7, "error");
        addExpectedMsg(Messages.ENUM_CASE_NAME, 24, 8, "error");
        addExpectedMsg(Messages.ENUM_CASE_NAME, 25, 8, "error");
        addExpectedMsg(Messages.ENUM_CASE_NAME, 26, 8, "error");
        addExpectedMsg(Messages.ENUM_NAME, 42, 6, "error");
        addExpectedMsg(Messages.ENUM_CASE_NAME, 43, 8, "error");
        addExpectedMsg(Messages.ENUM_NAME, 46, 6, "error");
        addExpectedMsg(Messages.ENUM_CASE_NAME, 47, 8, "error");
        addExpectedMsg(Messages.ENUM_NAME, 50, 6, "error");
        addExpectedMsg(Messages.ENUM_CASE_NAME, 55, 8, "error");
        addExpectedMsg(Messages.ENUM_CASE_NAME, 63, 8, "error");
        addExpectedMsg(Messages.STRUCT_NAME, 72, 8, "error");
        addExpectedMsg(Messages.STRUCT_NAME, 76, 8, "error");
        addExpectedMsg(Messages.PROTOCOL_NAME, 90, 10, "error");
        addExpectedMsg(Messages.PROTOCOL_NAME, 94, 10, "error");
        addExpectedMsg(Messages.PROTOCOL_NAME, 98, 10, "error");

        Tailor.main(command);

        Set<String> actualOutput = new HashSet<>();
        for (String msg : outContent.toString().split(NEWLINE_REGEX)) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        assertTrue(actualOutput.size() == expectedMessages.size());
        assertTrue(actualOutput.containsAll(expectedMessages));
    }

    private void addExpectedMsg(String msg, int line, int column, String classification) {
        expectedMessages.add(
            Printer.genOutputStringForTest(
                inputFile.getName(), msg + Messages.UPPER_CAMEL_CASE, line, column, classification));
    }

}