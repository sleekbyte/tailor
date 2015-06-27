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
 * Functional tests for name length rule
 */
@RunWith(MockitoJUnitRunner.class)
public class NameLengthTest {

    private static final String NEWLINE_REGEX = "\\r?\\n";

    private ByteArrayOutputStream outContent;
    private File inputFile = new File("src/test/java/com/sleekbyte/tailor/functional/NameLengthTest.swift");
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
    public void testNameLength() throws IOException {
        String[] command = {
            "--max-line-length", "40",
            "--max-name-length", "5",
            inputFile.getPath()
        };

        addExpectedMsg(1, 7, Messages.ERROR, Messages.CLASS + Messages.NAME, 23, 5);
        addExpectedMsg(4, 40, Messages.ERROR, Messages.ELEMENT + Messages.NAME, 19, 5);
        addExpectedMsg(6, 6, Messages.ERROR, Messages.ENUM + Messages.NAME, 21, 5);
        addExpectedMsg(7, 13, Messages.ERROR, Messages.ENUM_CASE + Messages.NAME, 24, 5);
        addExpectedMsg(10, 6, Messages.ERROR, Messages.FUNCTION + Messages.NAME, 23, 5);
        addExpectedMsg(10, 30, Messages.ERROR, Messages.EXTERNAL_PARAMETER + Messages.NAME, 25, 5);
        addExpectedMsg(10, 56, Messages.ERROR, Messages.LOCAL_PARAMETER + Messages.NAME, 23, 5);
        addExpectedMsg(15, 1, Messages.ERROR, Messages.LABEL + Messages.NAME, 13, 5);
        addExpectedMsg(24, 10, Messages.ERROR, Messages.PROTOCOL + Messages.NAME, 19, 5);
        addExpectedMsg(27, 8, Messages.ERROR, Messages.STRUCT + Messages.NAME, 21, 5);
        addExpectedMsg(38, 11, Messages.ERROR, Messages.TYPEALIAS + Messages.NAME, 19, 5);
        addExpectedMsg(38, 33, Messages.ERROR, Messages.TYPE + Messages.NAME, 6, 5);

        addExpectedMsg(4, 41, Messages.ERROR, Messages.LINE, 72, 40);
        addExpectedMsg(7, 41, Messages.ERROR, Messages.LINE, 46, 40);
        addExpectedMsg(10, 41, Messages.ERROR, Messages.LINE, 94, 40);

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

    private void addExpectedMsg(int line, int column, String classification, String msg, int length, int limit) {
        String lengthVersusLimit = " (" + length + "/" + limit + ")";
        msg += Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit;
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }

}
