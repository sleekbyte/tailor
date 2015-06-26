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
    private static final String FILE_DATA = "This is data for a file";
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
        writeNumOfLines(1);
        assertEquals(1, SourceFileUtil.numLinesInFile(inputFile));
    }

    @Test
    public void testNumLinesInFileManyLines() throws IOException {
        writeNumOfLines(4);
        assertEquals(4, SourceFileUtil.numLinesInFile(inputFile));
    }

    @Test
    public void testFileTooLongMaxLengthZeroOrNegative() throws IOException {
        assertFalse(SourceFileUtil.fileTooLong(inputFile, 0));
        assertFalse(SourceFileUtil.fileTooLong(inputFile, -1));

        writeNumOfLines(1);
        assertFalse(SourceFileUtil.fileTooLong(inputFile, 0));
        assertFalse(SourceFileUtil.fileTooLong(inputFile, -1));
    }

    @Test
    public void testFileTooLongMaxLengthLarge() throws IOException {
        assertFalse(SourceFileUtil.fileTooLong(inputFile, 2));

        writeNumOfLines(3);
        assertTrue(SourceFileUtil.fileTooLong(inputFile, 2));
    }

    @Test
    public void testConstructTooLongMaxLengthZeroOrNegative() throws IOException {
        assertFalse(SourceFileUtil.constructTooLong(context, 0));
        assertFalse(SourceFileUtil.constructTooLong(context, -1));

        when(startToken.getLine()).thenReturn(10);
        when(stopToken.getLine()).thenReturn(11);

        assertFalse(SourceFileUtil.constructTooLong(context, 0));
        assertFalse(SourceFileUtil.constructTooLong(context, -1));
    }

    @Test
    public void testConstructTooLongMaxLengthLarge() throws IOException {
        when(startToken.getLine()).thenReturn(1);
        when(stopToken.getLine()).thenReturn(5);
        assertFalse(SourceFileUtil.constructTooLong(context, 10));

        when(startToken.getLine()).thenReturn(1);
        when(stopToken.getLine()).thenReturn(20);
        assertTrue(SourceFileUtil.constructTooLong(context, 12));
    }

    private void writeNumOfLines(int numOfLines) {
        for (int i = 1; i <= numOfLines; i++) {
            writer.println(FILE_DATA);
        }
        writer.close();
    }

}
