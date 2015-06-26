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

    private ByteArrayOutputStream outContent;
    private File inputFile = new File("src/test/java/com/sleekbyte/tailor/functional/UpperCamelCaseTests.swift");
    private Set<String> expectedMessages = new HashSet<>();

    private static final String NEWLINE_REGEX = "\\r?\\n";

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

        addExpectMsg(Messages.CLASS_NAME + Messages.UPPER_CAMEL_CASE, 3, 7, "error");
        addExpectMsg(Messages.CLASS_NAME + Messages.UPPER_CAMEL_CASE, 7, 7, "error");
        addExpectMsg(Messages.ENUM_CASE_NAME + Messages.UPPER_CAMEL_CASE, 24, 8, "error");
        addExpectMsg(Messages.ENUM_CASE_NAME + Messages.UPPER_CAMEL_CASE, 25, 8, "error");
        addExpectMsg(Messages.ENUM_CASE_NAME + Messages.UPPER_CAMEL_CASE, 26, 8, "error");
        addExpectMsg(Messages.ENUM_NAME + Messages.UPPER_CAMEL_CASE, 42, 6, "error");
        addExpectMsg(Messages.ENUM_CASE_NAME + Messages.UPPER_CAMEL_CASE, 43, 8, "error");
        addExpectMsg(Messages.ENUM_NAME + Messages.UPPER_CAMEL_CASE, 46, 6, "error");
        addExpectMsg(Messages.ENUM_CASE_NAME + Messages.UPPER_CAMEL_CASE, 47, 8, "error");
        addExpectMsg(Messages.ENUM_NAME + Messages.UPPER_CAMEL_CASE, 50, 6, "error");
        addExpectMsg(Messages.ENUM_CASE_NAME + Messages.UPPER_CAMEL_CASE, 55, 8, "error");
        addExpectMsg(Messages.ENUM_CASE_NAME + Messages.UPPER_CAMEL_CASE, 63, 8, "error");
        addExpectMsg(Messages.STRUCT_NAME + Messages.UPPER_CAMEL_CASE, 72, 8, "error");
        addExpectMsg(Messages.STRUCT_NAME + Messages.UPPER_CAMEL_CASE, 76, 8, "error");
        addExpectMsg(Messages.PROTOCOL_NAME + Messages.UPPER_CAMEL_CASE, 90, 10, "error");
        addExpectMsg(Messages.PROTOCOL_NAME + Messages.UPPER_CAMEL_CASE, 94, 10, "error");
        addExpectMsg(Messages.PROTOCOL_NAME + Messages.UPPER_CAMEL_CASE, 98, 10, "error");

        Tailor.main(command);

        for(String msg : outContent.toString().split(NEWLINE_REGEX)) {
            assertTrue(expectedMessages.contains(msg.substring(msg.indexOf(inputFile.getName()))));
        }
    }

    private void addExpectMsg(String msg, int line, int column, String classification) {
        expectedMessages.add(
            Printer.genOutputStringForTest(inputFile.getName(), msg, line, column, classification));
    }

}
