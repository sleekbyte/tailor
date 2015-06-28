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
 * Functional tests for constant naming rule
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstantNamingTest {

    private static final String NEWLINE_REGEX = "\\r?\\n";

    private ByteArrayOutputStream outContent;
    private File inputFile = new File("src/test/java/com/sleekbyte/tailor/functional/ConstantNamingTest.swift");
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
    public void testConstantNaming() throws IOException {
        String[] command = { inputFile.getPath() };

        addExpectedMsg(2, 5, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(11, 13, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(12, 15, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(20, 5, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(24, 5, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(56, 5, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(67, 9, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);
        addExpectedMsg(78, 9, Messages.ERROR, Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING);

        addExpectedMsg(5, 16, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(5, 45, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(15, 9, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(29, 33, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(29, 19, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(30, 7, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(31, 7, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(33, 7, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(40, 14, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(50, 11, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(87, 17, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(94, 8, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);
        addExpectedMsg(104, 14, Messages.ERROR, Messages.CONSTANT + Messages.CONSTANT_NAMING);

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
        expectedMessages.add(Printer.genOutputStringForTest(inputFile.getName(), line, column, classification, msg));
    }

}
