package com.sleekbyte.tailor.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Properties container.
 */
public class ConfigProperties {

    public static final String VERSION_PROPERTY = "version";
    protected static final String CONFIG_RESOURCE_PATH = "/config.properties";
    protected static final String DEFAULT_VERSION = "0.0.0";
    protected final Properties prop = new Properties();

    public String getVersion() throws IOException {
        return getProperty(VERSION_PROPERTY, DEFAULT_VERSION);
    }

    protected InputStream getConfigResource() {
        return ConfigProperties.class.getResourceAsStream(CONFIG_RESOURCE_PATH);
    }

    private String getProperty(String key, String defaultValue) throws IOException {
        InputStream in = getConfigResource();
        if (in != null) {
            try {
                prop.load(in);
            } finally {
                in.close();
            }
        }
        return prop.getProperty(key, defaultValue);
    }

}
