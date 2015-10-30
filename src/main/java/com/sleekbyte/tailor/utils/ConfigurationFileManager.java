package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.Configuration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Retrieve and parse config file.
 */
public final class ConfigurationFileManager {

    private static final String DEFAULT_CONFIG_PATH = ".tailor.yml";

    /**
     * Find and parse config file.
     *
     * @param configPath path passed in as configuration file option argument
     * @return config file data encapsulated in Configuration object. If no config file found, then default config.
     * @throws IOException if unable to open/read config file
     */
    public static Optional<Configuration> getConfiguration(Optional<String> configPath) throws IOException {
        File configFile = new File(configPath.orElse(DEFAULT_CONFIG_PATH));

        // Extract information from config file
        Yaml yaml = new Yaml(new Constructor(Configuration.class));
        Optional<Configuration> configData;

        if (!configFile.exists()) {
            return Optional.empty();
        }

        InputStream configFileStream = new FileInputStream(configFile);
        configData = Optional.ofNullable((Configuration) yaml.load(configFileStream));
        configFileStream.close();

        Configuration defaultConfig = new Configuration();
        Configuration config = configData.orElse(defaultConfig);
        config.setFileLocation(configFile.getCanonicalPath());
        if (config.getInclude() == null || config.getInclude().isEmpty()) {
            config.setInclude(defaultConfig.getInclude());
        }
        if (config.getExclude() == null || config.getExclude().isEmpty()) {
            config.setExclude(defaultConfig.getExclude());
        }

        return Optional.of(config);
    }

}
