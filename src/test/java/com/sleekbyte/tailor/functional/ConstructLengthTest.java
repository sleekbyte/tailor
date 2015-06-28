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
 * Functional tests for construct length rule
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructLengthTest {

    private static final String NEWLINE_REGEX = "\\r?\\n";

    private ByteArrayOutputStream outContent;
    private File inputFile = new File("src/test/java/com/sleekbyte/tailor/functional/ConstructLengthTest.swift");
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
    public void testConstructLength() throws IOException {
        String[] command = {
            "--max-class-length", "8",
            "--max-closure-length", "6",
            "--max-file-length", "30",
            "--max-function-length", "3",
            "--max-struct-length", "1",
            inputFile.getPath()
        };

        addExpectedMsg(8, 16, Messages.ERROR, Messages.CLASS, 12, 8);
        addExpectedMsg(24, 27, Messages.ERROR, Messages.CLOSURE, 8, 6);
        addExpectedMsg(10, 67, Messages.ERROR, Messages.FUNCTION, 9, 3);
        addExpectedMsg(12, 35, Messages.ERROR, Messages.FUNCTION, 5, 3);
        addExpectedMsg(35, 19, Messages.ERROR, Messages.STRUCT, 3, 1);

        addExpectedMsg(31, Messages.ERROR, Messages.FILE, 38, 30);

        Tailor.main(command);
System.err.println(outContent.toString());
        Set<String> actualOutput = new HashSet<>();
        for (String msg : outContent.toString().split(NEWLINE_REGEX)) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        assertEquals(actualOutput.size(), expectedMessages.size());
        assertTrue(actualOutput.containsAll(expectedMessages));
    }

    private void addExpectedMsg(int line, String classification, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, classification, msg));
    }

    private void addExpectedMsg(int line, int column, String classification, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }

}
