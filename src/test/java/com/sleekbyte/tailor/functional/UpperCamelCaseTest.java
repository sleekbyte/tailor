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
    private File inputFile = new File("src/test/java/com/sleekbyte/tailor/functional/UpperCamelCaseTest.swift");
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

        addExpectedMsg(Messages.CLASS + Messages.NAMES, 3, 7, Messages.ERROR);
        addExpectedMsg(Messages.CLASS + Messages.NAMES, 7, 7, Messages.ERROR);
        addExpectedMsg(Messages.ENUM_CASE + Messages.NAMES, 24, 8, Messages.ERROR);
        addExpectedMsg(Messages.ENUM_CASE + Messages.NAMES, 25, 8, Messages.ERROR);
        addExpectedMsg(Messages.ENUM_CASE + Messages.NAMES, 26, 8, Messages.ERROR);
        addExpectedMsg(Messages.ENUM + Messages.NAMES, 42, 6, Messages.ERROR);
        addExpectedMsg(Messages.ENUM_CASE + Messages.NAMES, 43, 8, Messages.ERROR);
        addExpectedMsg(Messages.ENUM + Messages.NAMES, 46, 6, Messages.ERROR);
        addExpectedMsg(Messages.ENUM_CASE + Messages.NAMES, 47, 8, Messages.ERROR);
        addExpectedMsg(Messages.ENUM + Messages.NAMES, 50, 6, Messages.ERROR);
        addExpectedMsg(Messages.ENUM_CASE + Messages.NAMES, 55, 8, Messages.ERROR);
        addExpectedMsg(Messages.ENUM_CASE + Messages.NAMES, 63, 8, Messages.ERROR);
        addExpectedMsg(Messages.STRUCT + Messages.NAMES, 72, 8, Messages.ERROR);
        addExpectedMsg(Messages.STRUCT + Messages.NAMES, 76, 8, Messages.ERROR);
        addExpectedMsg(Messages.PROTOCOL + Messages.NAMES, 90, 10, Messages.ERROR);
        addExpectedMsg(Messages.PROTOCOL + Messages.NAMES, 94, 10, Messages.ERROR);
        addExpectedMsg(Messages.PROTOCOL + Messages.NAMES, 98, 10, Messages.ERROR);

        Tailor.main(command);

        Set<String> actualOutput = new HashSet<>();
        for (String msg : outContent.toString().split(NEWLINE_REGEX)) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        // TODO: Use `==` once #33 "Buffer output in printer class and remove duplicateMessages.ERROR messages" is complete
        assertTrue(actualOutput.size() >= expectedMessages.size());
        assertTrue(actualOutput.containsAll(expectedMessages));
    }

    private void addExpectedMsg(String msg, int line, int column, String classification) {
        expectedMessages.add(
            Printer.genOutputStringForTest(
                inputFile.getName(), msg + Messages.UPPER_CAMEL_CASE, line, column, classification));
    }

}
