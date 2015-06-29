package com.sleekbyte.tailor.grammar;

import com.sleekbyte.tailor.Tailor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GrammarTest {

    private static final String TEST_INPUT_DIR = "src/test/java/com/sleekbyte/tailor/grammar/";

    private ByteArrayOutputStream errContent;
    private ByteArrayOutputStream outContent;
    private File[] swiftFiles;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        File curDir = new File(TEST_INPUT_DIR);
        swiftFiles = curDir.listFiles((File file, String name) -> name.endsWith(".swift"));
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testGrammar() throws UnsupportedEncodingException {
        for (File swiftFile: swiftFiles) {
            errContent = new ByteArrayOutputStream();
            System.setErr(new PrintStream(errContent, false, Charset.defaultCharset().name()));
            String[] command = { (TEST_INPUT_DIR + swiftFile.getName()) };
            Tailor.main(command);
            assertEquals("", errContent.toString(Charset.defaultCharset().name()));
            System.setErr(null);
        }
    }

}
