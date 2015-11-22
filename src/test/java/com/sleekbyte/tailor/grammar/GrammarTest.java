package com.sleekbyte.tailor.grammar;

import static org.junit.Assert.assertThat;

import com.sleekbyte.tailor.Tailor;
import org.hamcrest.text.IsEmptyString;
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

@RunWith(MockitoJUnitRunner.class)
public class GrammarTest {

    private static final String TEST_INPUT_DIR = "src/test/swift/com/sleekbyte/tailor/grammar/";

    private File[] swiftFiles;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        File inputDir = new File(TEST_INPUT_DIR);
        swiftFiles = inputDir.listFiles((File file, String name) -> name.endsWith(".swift"));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testGrammar() throws UnsupportedEncodingException {
        for (File swiftFile : swiftFiles) {
            ByteArrayOutputStream errContent = new ByteArrayOutputStream();
            System.setErr(new PrintStream(errContent, false, Charset.defaultCharset().name()));
            String[] command = { "--debug", (TEST_INPUT_DIR + swiftFile.getName()) };
            Tailor.main(command);
            assertThat(errContent.toString(Charset.defaultCharset().name()), IsEmptyString.isEmptyString());
            System.setErr(null);
        }
    }

}
