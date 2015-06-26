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
 * Functional tests for Semicolon rule
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

        addExpectedMsg(Messages.STATEMENTS, 1, 18, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 10, 2, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 6, 15, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 7, 15, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 8, 14, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 9, 14, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 17, 2, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 13, 33, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 14, 16, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 15, 23, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 16, 39, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 21, 2, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 42, 2, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 24, 18, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 41, 6, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 30, 10, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 35, 10, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 40, 23, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 64, 2, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 47, 28, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 48, 14, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 63, 6, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 54, 10, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 53, 21, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 57, 65, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 59, 73, Messages.ERROR);
        addExpectedMsg(Messages.STATEMENTS, 61, 59, Messages.ERROR);

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
                inputFile.getName(), msg + Messages.SEMICOLON, line, column, classification));
    }

}
