package com.sleekbyte.tailor.utils;

import static org.junit.Assert.assertEquals;

import com.sleekbyte.tailor.common.YamlConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Tests for {@link YamlConfigurationFileManager}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class YamlConfigurationFileManagerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = IOException.class)
    public void testParseConfigurationFileEmptyFolder() throws IOException {
        YamlConfigurationFileManager.getConfiguration(Optional.of(folder.getRoot().getPath()));
    }

    @Test
    public void testParseConfigurationFileValidConfigFile() throws IOException {
        YamlConfiguration config = YamlConfigurationFileManager
                .getConfiguration(Optional.of(createConfigFile(".tailor.yml").toString()))
                .orElse(new YamlConfiguration());

        Set<String> actualIncludeList = config.getInclude();
        Set<String> actualExcludeList = config.getExclude();
        Set<String> expectedIncludeList = new HashSet<>(Arrays.asList("Source"));
        Set<String> expectedExcludeList = new HashSet<>(Arrays.asList("Carthage", "Pods"));
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
