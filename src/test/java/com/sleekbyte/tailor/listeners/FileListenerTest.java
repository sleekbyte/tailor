package com.sleekbyte.tailor.listeners;

import static org.junit.Assert.assertEquals;

import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * Tests for {@link FileListener}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileListenerTest {

    @Rule
    public TestName testName = new TestName();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private static final String INPUT_FILE = "inputFile.swift";
    private static final String NORMAL_LINE = "This is data for a file";

    private File inputFile;
    private PrintWriter writer;
    private Printer printer;

    @Mock
    private ParserRuleContext context;
    @Mock private Token startToken;
    @Mock private Token stopToken;

    @Before
    public void setUp() throws NoSuchMethodException, IOException {
        Method method = this.getClass().getMethod(testName.getMethodName());
        inputFile = folder.newFile(method.getName() + "-" + INPUT_FILE);
        printer = new Printer(inputFile, Severity.WARNING);
        writer = new PrintWriter(inputFile, Charset.defaultCharset().name());
    }

    @Test
    public void testNumLinesInFileZeroLines() throws IOException {
        try (FileListener fileListener = new FileListener(printer, inputFile, new MaxLengths())) {
            fileListener.verify();
            assertEquals(0, fileListener.getNumOfLines());
        }
    }

    @Test
    public void testNumLinesInFileOneLine() throws IOException {
        writeNumOfLines(1, NORMAL_LINE);
        try (FileListener fileListener = new FileListener(printer, inputFile, new MaxLengths())) {
            fileListener.verify();
            assertEquals(1, fileListener.getNumOfLines());
        }
    }

    @Test
    public void testNumLinesInFileMultipleLines() throws IOException {
        writeNumOfLines(4, NORMAL_LINE);
        try (FileListener fileListener = new FileListener(printer, inputFile, new MaxLengths())) {
            fileListener.verify();
            assertEquals(4, fileListener.getNumOfLines());
        }
    }

    private void writeNumOfLines(int numOfLines, String data) {
        for (int i = 1; i <= numOfLines; i++) {
            writer.println(data);
        }
        writer.close();
    }
}
