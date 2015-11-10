package com.sleekbyte.tailor.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class ConfigPropertiesTest {

    @Test
    public void testGetVersion() throws IOException {
        ConfigProperties configProperties = new ConfigProperties();
        Properties prop = new Properties();
        try (InputStream in = getClass().getResourceAsStream(ConfigProperties.CONFIG_RESOURCE_PATH)) {
            prop.load(in);
        }
        assertEquals(prop.getProperty(ConfigProperties.VERSION_PROPERTY), configProperties.getVersion());
    }

    @Test
    public void testMissingConfigVersion() throws IOException {
        ConfigProperties configProperties = spy(ConfigProperties.class);
        when(configProperties.getConfigResource()).thenReturn(null);
        assertEquals(ConfigProperties.DEFAULT_VERSION, configProperties.getVersion());
    }

    @Test
    public void testVersionNumberMatchesSemanticVersionFormat() throws IOException {
        ConfigProperties configProperties = new ConfigProperties();
        assertTrue("Version number should match MAJOR.MINOR.PATCH format from http://semver.org.",
            configProperties.getVersion().matches("\\d+\\.\\d+\\.\\d+"));
    }
}
