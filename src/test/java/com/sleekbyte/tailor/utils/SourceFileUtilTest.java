package com.sleekbyte.tailor.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
 * Tests for {@link SourceFileUtil}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SourceFileUtilTest {

    @Rule
    public TestName testName = new TestName();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private static final String INPUT_FILE = "inputFile.swift";
    private static final String NORMAL_LINE = "This is data for a file";
    private static final String NAME = "variableName";

    private File inputFile;
    private PrintWriter writer;

    @Mock private ParserRuleContext context;
    @Mock private Token startToken;
    @Mock private Token stopToken;

    @Before
    public void setUp() throws NoSuchMethodException, IOException {
        Method method = this.getClass().getMethod(testName.getMethodName());
        inputFile = folder.newFile(method.getName() + "-" + INPUT_FILE);
        writer = new PrintWriter(inputFile, Charset.defaultCharset().name());
        when(context.getStart()).thenReturn(startToken);
        when(context.getStop()).thenReturn(stopToken);
    }

    @Test
    public void testFileTooLongMaxLengthZeroOrNegativeEmptyFile() {
        assertFalse(SourceFileUtil.fileTooLong(0, 0));
        assertFalse(SourceFileUtil.fileTooLong(-1, -1));
    }

    @Test
    public void testFileTooLongMaxLengthZeroOrNegative() {
        assertFalse(SourceFileUtil.fileTooLong(1, 0));
        assertFalse(SourceFileUtil.fileTooLong(1, -1));
    }

    @Test
    public void testFileTooLongMaxLengthValidEmptyFile() {
        assertFalse(SourceFileUtil.fileTooLong(0, 2));
    }

    @Test
    public void testFileTooLongMaxLengthValid() {
        assertTrue(SourceFileUtil.fileTooLong(3, 2));
        assertFalse(SourceFileUtil.fileTooLong(3, 3));
    }

    @Test
    public void testLineTooLongMaxLengthZeroOrNegative() {
        assertFalse(SourceFileUtil.lineTooLong(20, 0));
        assertFalse(SourceFileUtil.lineTooLong(20, -1));
    }

    @Test
    public void testLineTooLongMaxLengthValid() {
        assertFalse(SourceFileUtil.lineTooLong(25, 25));
        assertTrue(SourceFileUtil.lineTooLong(26, 25));
        assertTrue(SourceFileUtil.lineTooLong(50, 25));
    }

    @Test
    public void testLineHasTrailingWhitespaceInvalid() {
        String line = "";
        assertFalse(SourceFileUtil.lineHasTrailingWhitespace(line.length(), line));
        line = NORMAL_LINE;
        assertFalse(SourceFileUtil.lineHasTrailingWhitespace(line.length(), line));
    }

    @Test
    public void testLineHasTrailingWhitespaceValid() {
        String line = "    ";
        assertTrue(SourceFileUtil.lineHasTrailingWhitespace(line.length(), line));
        line = "\t\t";
        assertTrue(SourceFileUtil.lineHasTrailingWhitespace(line.length(), line));
        line = NORMAL_LINE + "    ";
        assertTrue(SourceFileUtil.lineHasTrailingWhitespace(line.length(), line));
        line = NORMAL_LINE + "\t\t";
        assertTrue(SourceFileUtil.lineHasTrailingWhitespace(line.length(), line));
    }

    @Test
    public void testConstructTooLongMaxLengthZeroOrNegative() {
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
    public void testNameTooLongMaxLengthZeroOrNegative() {
        assertFalse(SourceFileUtil.nameTooLong(context, 0));
        assertFalse(SourceFileUtil.nameTooLong(context, -1));
    }

    @Test
    public void testNameTooLongMaxLengthValid() {
        when(context.getText()).thenReturn(NAME);
        assertFalse(SourceFileUtil.nameTooLong(context, NAME.length()));
        assertFalse(SourceFileUtil.nameTooLong(context, NAME.length() + 1));
        assertTrue(SourceFileUtil.nameTooLong(context, NAME.length() - 10));

        when(context.getText()).thenReturn("");
        assertFalse(SourceFileUtil.nameTooLong(context, NAME.length()));
    }

    @Test
    public void testNewlineTerminatedBlankFile() throws IOException {
        assertTrue(SourceFileUtil.singleNewlineTerminated(inputFile));
    }

    @Test
    public void testNewlineTerminatedNoNewline() throws IOException {
        writer.print("Line without a terminating newline.");
        writer.close();
        assertFalse(SourceFileUtil.singleNewlineTerminated(inputFile));
    }

    @Test
    public void testNewlineTerminatedOnlyNewline() throws IOException {
        writeNumOfLines(1, "");
        assertTrue(SourceFileUtil.singleNewlineTerminated(inputFile));
    }

    @Test
    public void testNewlineTerminatedWithNewline() throws IOException {
        writeNumOfLines(3, NORMAL_LINE);
        assertTrue(SourceFileUtil.singleNewlineTerminated(inputFile));
    }

    @Test
    public void testNewlineTerminatedWithNoContentAndMultipleNewlines() throws IOException {
        writeNumOfLines(2, "");
        assertFalse(SourceFileUtil.singleNewlineTerminated(inputFile));
    }

    @Test
    public void testNewlineTerminatedWithSomeContentAndMultipleNewlines() throws IOException {
        writeNumOfLines(1, NORMAL_LINE + "\n");
        assertFalse(SourceFileUtil.singleNewlineTerminated(inputFile));
    }

    @Test
    public void testHasLeadingWhitespaceBlankFile() throws IOException {
        assertFalse(SourceFileUtil.hasLeadingWhitespace(inputFile));
    }

    @Test
    public void testHasLeadingWhitespaceOnlyNewline() throws IOException {
        writeNumOfLines(1, "");
        assertTrue(SourceFileUtil.hasLeadingWhitespace(inputFile));
    }

    @Test
    public void testHasLeadingWhitespaceWithSingleLine() throws IOException {
        writeNumOfLines(1, NORMAL_LINE);
        assertFalse(SourceFileUtil.hasLeadingWhitespace(inputFile));
    }

    @Test
    public void testHasLeadingWhitespaceWithSingleLineAndPrecedingNewline() throws IOException {
        writeNumOfLines(1, "\n" + NORMAL_LINE);
        assertTrue(SourceFileUtil.hasLeadingWhitespace(inputFile));
    }

    @Test
    public void testHasLeadingWhitespaceWithSingleLineAndPrecedingSpace() throws IOException {
        writeNumOfLines(1, " " + NORMAL_LINE);
        assertTrue(SourceFileUtil.hasLeadingWhitespace(inputFile));
    }

    @Test
    public void testHasLeadingWhitespaceWithSingleLineAndPrecedingTab() throws IOException {
        writeNumOfLines(1, "\t" + NORMAL_LINE);
        assertTrue(SourceFileUtil.hasLeadingWhitespace(inputFile));
    }

    private void writeNumOfLines(int numOfLines, String data) {
        for (int i = 1; i <= numOfLines; i++) {
            writer.println(data);
        }
        writer.close();
    }

}
