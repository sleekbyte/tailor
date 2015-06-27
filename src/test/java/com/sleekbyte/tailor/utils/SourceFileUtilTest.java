package com.sleekbyte.tailor.utils;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link SourceFileUtil}
 */
@RunWith(MockitoJUnitRunner.class)
public class SourceFileUtilTest {

    private static final String INPUT_FILE = "inputFile.swift";
    private static final String NORMAL_LINE = "This is data for a file";
    private static final String LONG_LINE = "This is really really really really long, it should not be this long";
    private static final String NAME = "variableName";
    private TemporaryFolder folder = new TemporaryFolder();
    private File inputFile;
    private PrintWriter writer;

    @Mock private ParserRuleContext context;
    @Mock private Token startToken;
    @Mock private Token stopToken;

    @Before
    public void setUp() throws Exception {
        folder.create();
        inputFile = folder.newFile(INPUT_FILE);
        writer = new PrintWriter(inputFile);
        when(context.getStart()).thenReturn(startToken);
        when(context.getStop()).thenReturn(stopToken);
    }

    @After
    public void tearDown() throws IOException {
        folder.delete();
    }

    @Test
    public void testNumLinesInFileZeroLines() throws IOException {
        assertEquals(0, SourceFileUtil.numLinesInFile(inputFile));
    }

    @Test
    public void testNumLinesInFileOneLine() throws IOException {
        writeNumOfLines(1, NORMAL_LINE);
        assertEquals(1, SourceFileUtil.numLinesInFile(inputFile));
    }

    @Test
    public void testNumLinesInFileMultipleLines() throws IOException {
        writeNumOfLines(4, NORMAL_LINE);
        assertEquals(4, SourceFileUtil.numLinesInFile(inputFile));
    }

    @Test
    public void testFileTooLongMaxLengthZeroOrNegative() throws IOException {
        assertFalse(SourceFileUtil.fileTooLong(inputFile, 0));
        assertFalse(SourceFileUtil.fileTooLong(inputFile, -1));

        writeNumOfLines(1, NORMAL_LINE);
        assertFalse(SourceFileUtil.fileTooLong(inputFile, 0));
        assertFalse(SourceFileUtil.fileTooLong(inputFile, -1));
    }

    @Test
    public void testFileTooLongMaxLengthValid() throws IOException {
        assertFalse(SourceFileUtil.fileTooLong(inputFile, 2));

        writeNumOfLines(3, NORMAL_LINE);
        assertTrue(SourceFileUtil.fileTooLong(inputFile, 2));
        assertFalse(SourceFileUtil.fileTooLong(inputFile, 3));
    }

    @Test
    public void testConstructTooLongMaxLengthZeroOrNegative() {
        assertFalse(SourceFileUtil.constructTooLong(context, 0));
        assertFalse(SourceFileUtil.constructTooLong(context, -1));

        when(startToken.getLine()).thenReturn(10);
        when(stopToken.getLine()).thenReturn(11);

        assertFalse(SourceFileUtil.constructTooLong(context, 0));
        assertFalse(SourceFileUtil.constructTooLong(context, -1));
    }

    @Test
    public void testConstructTooLongMaxLengthValid() {
        when(startToken.getLine()).thenReturn(1);
        when(stopToken.getLine()).thenReturn(5);
        assertFalse(SourceFileUtil.constructTooLong(context, 10));

        when(startToken.getLine()).thenReturn(1);
        when(stopToken.getLine()).thenReturn(20);
        assertTrue(SourceFileUtil.constructTooLong(context, 12));
        assertFalse(SourceFileUtil.constructTooLong(context, 19));
    }

    @Test
    public void testLinesTooLongMaxLengthZeroOrNegative() throws IOException {
        assertTrue(SourceFileUtil.linesTooLong(inputFile, 0).isEmpty());
        assertTrue(SourceFileUtil.linesTooLong(inputFile, -1).isEmpty());

        writeNumOfLines(4, LONG_LINE);
        assertTrue(SourceFileUtil.linesTooLong(inputFile, 0).isEmpty());
        assertTrue(SourceFileUtil.linesTooLong(inputFile, -1).isEmpty());
    }

    @Test
    public void testLinesTooLongMaxLengthValid() throws IOException {
        assertTrue(SourceFileUtil.linesTooLong(inputFile, 10).isEmpty());

        writeNumOfLines(4, LONG_LINE);
        assertTrue(SourceFileUtil.linesTooLong(inputFile, LONG_LINE.length() + 10).isEmpty());
        Map<Integer, Integer> longLines = SourceFileUtil.linesTooLong(inputFile, LONG_LINE.length() - 1);
        assertFalse(longLines.isEmpty());
        assertThat(longLines, hasEntry(1, LONG_LINE.length()));
        assertThat(longLines, hasEntry(2, LONG_LINE.length()));
        assertThat(longLines, hasEntry(3, LONG_LINE.length()));
        assertThat(longLines, hasEntry(4, LONG_LINE.length()));
        assertEquals(longLines.entrySet().size(), 4);
    }

    @Test
    public void testNameTooLongMaxLengthZeroOrNegative() {
        assertFalse(SourceFileUtil.nameTooLong(context, 0));
        assertFalse(SourceFileUtil.nameTooLong(context, -1));

        when(context.getText()).thenReturn(NAME);
        assertFalse(SourceFileUtil.nameTooLong(context, 0));
        assertFalse(SourceFileUtil.nameTooLong(context, -1));
    }

    @Test
    public void testNameTooLongMaxLengthValid() {
        when(context.getText()).thenReturn(NAME);
        assertFalse(SourceFileUtil.nameTooLong(context, NAME.length() + 1));
        assertTrue(SourceFileUtil.nameTooLong(context, NAME.length() - 10));

        when(context.getText()).thenReturn("");
        assertFalse(SourceFileUtil.nameTooLong(context, NAME.length()));
    }

    private void writeNumOfLines(int numOfLines, String data) {
        for (int i = 1; i <= numOfLines; i++) {
            writer.println(data);
        }
        writer.close();
    }

}
