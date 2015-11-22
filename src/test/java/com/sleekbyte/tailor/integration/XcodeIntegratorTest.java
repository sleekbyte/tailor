package com.sleekbyte.tailor.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.sleekbyte.tailor.common.ExitCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Tests for {@link XcodeIntegratorTest}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class XcodeIntegratorTest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws UnsupportedEncodingException {
        outContent.reset();
        System.setErr(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setErr(null);
    }

    @Test(expected = IOException.class)
    public void testGetAbsolutePathEmptyPath() throws  IOException {
        XcodeIntegrator.getAbsolutePath("");
    }

    @Test(expected = IOException.class)
    public void testGetAbsolutePathNotXcodeprojFile() throws  IOException {
        XcodeIntegrator.getAbsolutePath(createFolder("testDir").getPath());
    }

    @Test
    public void testGetAbsolutePathValidXcodeprojFile() throws  IOException {
        String ret = XcodeIntegrator.getAbsolutePath(createFolder("test.xcodeproj").getPath());
        assertNotNull(ret);
    }

    @Test
    public void testSetupXcodeInvalidXcodeprojFile() throws  IOException {
        int ret = XcodeIntegrator.setupXcode(createFolder("testDir").getPath());
        assertEquals(ExitCode.failure(), ret);
    }

    @Test
    public void testCreateTempRubyScriptValidXcodeprojFile() throws IOException {
        File xcodeproj = createFolder("test.xcodeproj");
        File rubyScript = XcodeIntegrator.createTempRubyScript(xcodeproj.getAbsolutePath());

        assertNotNull(rubyScript);
        assertTrue(rubyScript.getName().contains("xcode_integrate"));

        if (!rubyScript.delete()) {
            throw new FileNotFoundException("Failed to delete file " + rubyScript);
        }
    }

    private File createFolder(String fileName) throws IOException {
        return folder.newFolder(fileName);
    }
}
