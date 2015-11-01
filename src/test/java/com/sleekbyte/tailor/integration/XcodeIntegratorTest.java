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
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Tests for {@link XcodeIntegratorTest}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class XcodeIntegratorTest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws UnsupportedEncodingException {
        outContent.reset();
        System.setErr(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setErr(null);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

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
        assertEquals(ExitCode.FAILURE, ret);
    }

    @Test
    public void testCreateTempRubyScriptValidXcodeprojFile() throws IOException {
        File xcodeproj = createFolder("test.xcodeproj");
        File rubyScript = XcodeIntegrator.createTempRubyScript(xcodeproj.getAbsolutePath());

        assertNotNull(rubyScript);
        assertTrue(rubyScript.getName().contains("xcode_integrate"));

        String expectedRubyScriptContent = String.join(System.getProperty("line.separator"),
            "#!/usr/bin/env ruby",
            "require 'pathname'",
            "# http://stackoverflow.com/a/5471032",
            "def which(cmd)",
            "  exts = ENV['PATHEXT'] ? ENV['PATHEXT'].split(';') : ['']",
            "  ENV['PATH'].split(File::PATH_SEPARATOR).each do |path|",
            "    exts.each { |ext|",
            "      exe = File.join(path, \"#{cmd}#{ext}\")",
            "      return exe if File.executable?(exe) && !File.directory?(exe)",
            "    }",
            "  end",
            "  return nil",
            "end",
            "def find_tailor()",
            "    return Pathname.new(File.realpath(which('tailor'))) + '../..'",
            "end",
            "tailor_gems_dir = find_tailor + 'gems/installed'",
            "tailor_cache_dir = find_tailor + 'gems/vendor/cache'",
            "ENV['GEM_HOME'] = tailor_gems_dir.to_s",
            "ENV['GEM_PATH'] = tailor_gems_dir.to_s",
            "cmd = \"gem install --local --no-rdoc --no-ri xcodeproj-*.gem\"",
            "Dir.chdir(tailor_cache_dir.to_s){ %x[#{cmd}] }",
            "require 'xcodeproj'",
            "begin",
            String.format("  project = Xcodeproj::Project.open(\"%s\")", xcodeproj.getAbsoluteFile()),
            "rescue",
            "  abort(\"Integration Error: Invalid .xcodeproj file\")",
            "end",
            "main_target = project.targets.first",
            "phase = main_target.new_shell_script_build_phase(\"Tailor\")",
            "phase.shell_script = \"/usr/local/bin/tailor\"",
            "project.save()"
        );
        String actualRubyScriptContent = String.join("\n", Files.readAllLines(Paths.get(rubyScript.getPath())));
        assertEquals(expectedRubyScriptContent, actualRubyScriptContent);

        if (!rubyScript.delete()) {
            throw new FileNotFoundException("Failed to delete file " + rubyScript);
        }
    }

    private File createFolder(String fileName) throws IOException {
        return folder.newFolder(fileName);
    }
}
