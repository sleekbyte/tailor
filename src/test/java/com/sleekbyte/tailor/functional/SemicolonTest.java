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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Functional tests for semicolon rule
 */
@RunWith(MockitoJUnitRunner.class)
public class SemicolonTest {

    private static final String NEWLINE_REGEX = "\\r?\\n";

    private ByteArrayOutputStream outContent;
    private File inputFile = new File("src/test/java/com/sleekbyte/tailor/functional/SemicolonTest.swift");
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
    public void testSemicolon() throws IOException {
        String[] command = { inputFile.getPath() };

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

        Tailor.main(command);

        Set<String> actualOutput = new HashSet<>();
        for (String msg : outContent.toString().split(NEWLINE_REGEX)) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        assertEquals(actualOutput.size(), expectedMessages.size());
        assertTrue(actualOutput.containsAll(expectedMessages));
    }

    private void addExpectedMsg(int line, int column, String classification, String msg) {
        expectedMessages.add(
            Printer.genOutputStringForTest(
                inputFile.getName(), line, column, classification, msg + Messages.SEMICOLON));
    }

}
