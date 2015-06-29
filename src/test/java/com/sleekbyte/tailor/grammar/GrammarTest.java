package com.sleekbyte.tailor.grammar;

import com.sleekbyte.tailor.Tailor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class GrammarTest {

    protected static final String TEST_INPUT_DIR = "src/test/java/com/sleekbyte/tailor/grammar/";

    protected ByteArrayOutputStream errContent;
    protected ByteArrayOutputStream outContent;
    protected File[] swiftFiles;

    @Before
    public void setUp() {
        File curDir = new File(TEST_INPUT_DIR);
        swiftFiles = curDir.listFiles((File file, String name) -> name.endsWith(".swift"));
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testGrammar() {
        for (File swiftFile: swiftFiles) {
            errContent = new ByteArrayOutputStream();
            System.setErr(new PrintStream(errContent));
            String[] command = { (TEST_INPUT_DIR + swiftFile.getName()) };
            Tailor.main(command);
            assertEquals("", errContent.toString());
            System.setErr(null);
        }
    }

}
