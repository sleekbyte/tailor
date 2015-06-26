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
 * Functional tests for ConstructLength rule
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

        addExpectedMsg(Messages.CLASS, 8, 16, Messages.ERROR, 12, 8);
        addExpectedMsg(Messages.CLOSURE, 24, 27, Messages.ERROR, 8, 6);
        addExpectedMsg(Messages.FILE, 31, 1, Messages.ERROR, 39, 30);
        addExpectedMsg(Messages.FUNCTION, 10, 67, Messages.ERROR, 9, 3);
        addExpectedMsg(Messages.FUNCTION, 12, 35, Messages.ERROR, 5, 3);
        addExpectedMsg(Messages.STRUCT, 35, 19, Messages.ERROR, 3, 1);

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

    private void addExpectedMsg(String msg, int line, int column, String classification, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        expectedMessages.add(
            Printer.genOutputStringForTest(
                inputFile.getName(), msg + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, line, column, classification));
    }

}
