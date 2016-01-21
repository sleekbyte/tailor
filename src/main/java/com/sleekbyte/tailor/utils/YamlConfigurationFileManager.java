package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.YamlConfiguration;
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
public final class YamlConfigurationFileManager {

    private static final String DEFAULT_CONFIG_PATH = ".tailor.yml";

    /**
     * Find and parse config file.
     *
     * @param configPath path passed in as configuration file option argument
     * @return config file data encapsulated in YamlConfiguration object. If no config file found, then default config.
     * @throws IOException if unable to open/read config file
     */
    public static Optional<YamlConfiguration> getConfiguration(Optional<String> configPath) throws IOException {
        File configFile = new File(configPath.orElse(DEFAULT_CONFIG_PATH));

        // Extract information from config file
        Yaml yaml = new Yaml(new Constructor(YamlConfiguration.class));
        Optional<YamlConfiguration> configData;

        if (!configFile.exists()) {
            return Optional.empty();
        }

        InputStream configFileStream = new FileInputStream(configFile);
        configData = Optional.ofNullable((YamlConfiguration) yaml.load(configFileStream));
        configFileStream.close();

        YamlConfiguration defaultConfig = new YamlConfiguration();
        YamlConfiguration config = configData.orElse(defaultConfig);
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
