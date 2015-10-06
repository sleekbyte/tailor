package com.sleekbyte.tailor.utils;

import static org.junit.Assert.assertEquals;

import com.sleekbyte.tailor.common.Configuration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests for {@link ConfigurationFileManager}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class ConfigurationFileManagerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = YAMLException.class)
    public void testParseConfigurationFileInvalidName() throws IOException {
        ConfigurationFileManager.parseConfigurationFile(createConfigFile(".invalid-name.yml").getAbsolutePath());
    }

    @Test(expected = YAMLException.class)
    public void testParseConfigurationFileEmptyFolder() throws IOException {
        ConfigurationFileManager.parseConfigurationFile(folder.toString());
    }

    @Test(expected = YAMLException.class)
    public void testParseConfigurationFileEmptyFileName() throws IOException {
        ConfigurationFileManager.parseConfigurationFile("");
    }

    @Test
    public void testParseConfigurationFileValidConfigFile() throws IOException {
        Configuration config = ConfigurationFileManager.parseConfigurationFile(createConfigFile(".tailor.yml")
            .getAbsolutePath());

        Set<String> actualIncludeList = config.getInclude();
        Set<String> actualExcludeList = config.getExclude();
        Set<String> expectedIncludeList = new HashSet<String>(Arrays.asList("Source"));
        Set<String> expectedExcludeList = new HashSet<String>(Arrays.asList("Carthage", "Pods", "**.{svn,git}",
            "**/*.{lproj,xcassets,framework,xcodeproj}"));
        assertEquals(expectedIncludeList, actualIncludeList);
        assertEquals(expectedExcludeList, actualExcludeList);
    }

    private File createConfigFile(String fileName) throws IOException {
        File configFile = folder.newFile(fileName);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(configFile), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("include:");
        printWriter.println("  - Source");
        printWriter.println("exclude:");
        printWriter.println("  - Carthage");
        printWriter.println("  - Pods");
        streamWriter.close();
        printWriter.close();
        return configFile;
    }

}
