package com.sleekbyte.tailor.listeners;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.sleekbyte.tailor.common.MaxLengths;
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
import java.util.Map;

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
    private static final String LONG_LINE = "This is really really really really long, it should not be this long";

    private File inputFile;
    private PrintWriter writer;

    @Mock
    private ParserRuleContext context;
    @Mock private Token startToken;
    @Mock private Token stopToken;

    @Before
    public void setUp() throws NoSuchMethodException, IOException {
        Method method = this.getClass().getMethod(testName.getMethodName());
        inputFile = folder.newFile(method.getName() + "-" + INPUT_FILE);
        writer = new PrintWriter(inputFile, Charset.defaultCharset().name());
    }

    @Test
    public void testNumLinesInFileZeroLines() throws IOException {
        try (FileListener fileListener = new FileListener(null, inputFile, new MaxLengths())) {
            assertEquals(0, fileListener.getNumOfLines());
        }
    }

    @Test
    public void testNumLinesInFileOneLine() throws IOException {
        writeNumOfLines(1, NORMAL_LINE);
        try (FileListener fileListener = new FileListener(null, inputFile, new MaxLengths())) {
            assertEquals(1, fileListener.getNumOfLines());
        }
    }

    @Test
    public void testNumLinesInFileMultipleLines() throws IOException {
        writeNumOfLines(4, NORMAL_LINE);
        try (FileListener fileListener = new FileListener(null, inputFile, new MaxLengths())) {
            assertEquals(4, fileListener.getNumOfLines());
        }
    }

    @Test
    public void testLinesTooLongMaxLengthZeroOrNegative() throws IOException {
        MaxLengths maxLengths = new MaxLengths();
        maxLengths.setMaxLineLength(0);
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            assertTrue(fileListener.getLongLines().isEmpty());
        }
        maxLengths.setMaxLineLength(-1);
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            assertTrue(fileListener.getLongLines().isEmpty());
        }

        writeNumOfLines(4, LONG_LINE);
        maxLengths.setMaxLineLength(0);
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            assertTrue(fileListener.getLongLines().isEmpty());
        }
        maxLengths.setMaxLineLength(-1);
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            assertTrue(fileListener.getLongLines().isEmpty());
        }
    }

    @Test
    public void testLinesTooLongMaxLengthValid() throws IOException {
        MaxLengths maxLengths = new MaxLengths();
        maxLengths.setMaxLineLength(-1);
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            assertTrue(fileListener.getLongLines().isEmpty());
        }

        writeNumOfLines(4, LONG_LINE);
        maxLengths.setMaxLineLength(LONG_LINE.length() + 10);
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            assertTrue(fileListener.getLongLines().isEmpty());
        }
        maxLengths.setMaxLineLength(LONG_LINE.length() - 1);
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            Map<Integer, Integer> longLines = fileListener.getLongLines();
            assertFalse(longLines.isEmpty());
            assertThat(longLines, hasEntry(1, LONG_LINE.length()));
            assertThat(longLines, hasEntry(2, LONG_LINE.length()));
            assertThat(longLines, hasEntry(3, LONG_LINE.length()));
            assertThat(longLines, hasEntry(4, LONG_LINE.length()));
            assertEquals(longLines.entrySet().size(), 4);
        }
    }

    @Test
    public void testLinesNoTrailingWhitespace() throws IOException {
        MaxLengths maxLengths = new MaxLengths();
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            assertTrue(fileListener.getTrailingLines().isEmpty());
        }

        writeNumOfLines(4, "");
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            assertTrue(fileListener.getTrailingLines().isEmpty());
        }
    }

    @Test
    public void testLinesWithTrailingWhitespace() throws IOException {
        MaxLengths maxLengths = new MaxLengths();
        writeNumOfLines(4, NORMAL_LINE + "    ");
        try (FileListener fileListener = new FileListener(null, inputFile, maxLengths)) {
            Map<Integer, Integer> trailingLines = fileListener.getTrailingLines();
            assertFalse(trailingLines.isEmpty());
            assertThat(trailingLines, hasEntry(1, NORMAL_LINE.length() + 4));
            assertThat(trailingLines, hasEntry(2, NORMAL_LINE.length() + 4));
            assertThat(trailingLines, hasEntry(3, NORMAL_LINE.length() + 4));
            assertThat(trailingLines, hasEntry(4, NORMAL_LINE.length() + 4));
            assertEquals(trailingLines.entrySet().size(), 4);
        }
    }

    private void writeNumOfLines(int numOfLines, String data) {
        for (int i = 1; i <= numOfLines; i++) {
            writer.println(data);
        }
        writer.close();
    }
}
