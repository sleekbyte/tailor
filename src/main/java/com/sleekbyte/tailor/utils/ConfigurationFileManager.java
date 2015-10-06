package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.Configuration;
import com.sleekbyte.tailor.common.Messages;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Retrieve and parse .tailor.yml config file.
 */
public final class ConfigurationFileManager {
    private static final String[] DEFAULT_INCLUDE_LIST = new String [] {"**/*"};
    private static final String[] DEFAULT_EXCLUDE_LIST = new String[] {"**.{svn,git}",
        "**/*.{lproj,xcassets,framework,xcodeproj}"};

    /**
     * Search and process .tailor.yml file.
     *
     * @return .tailor.yml data encapsulated in Configuration object. If no .tailor.yml is found, then return
     *     null
     * @throws IOException if unable to open/read config file
     */
    public static Configuration getConfigurationFile(String configFilePath) throws IOException {
        Configuration config = null;
        // Check whether config file passed via CLI
        if (configFilePath != null) {
            System.out.println(Messages.TAILOR_CONFIG_LOCATION + configFilePath);
            // Parse config file
            config = ConfigurationFileManager.parseConfigurationFile(configFilePath);
        } else {
            // Search for .tailor.yml config file in directory from where Tailor is invoked from
            File currentDirectory = Paths.get(".").toFile();
            File[] files = currentDirectory.listFiles((dir, name) -> { return name.equals(".tailor.yml"); });

            if (files != null && files.length > 0) {
                // .tailor.yml exists => parse config file
                System.out.println(Messages.TAILOR_CONFIG_LOCATION + Paths.get(".").toAbsolutePath());
                config = ConfigurationFileManager.parseConfigurationFile(files[0].getAbsolutePath());
            }
        }

        return config;
    }

    /**
     * Produce a universal configuration object.
     *
     * @return configuration object that will scan everything
     */
    public static Configuration getDefaultConfigurationFile() {
        Configuration config = new Configuration();
        config.setInclude(new HashSet<>(Arrays.asList(DEFAULT_INCLUDE_LIST)));
        return config;
    }

    /**
     * Parse .tailor.yml config file.
     *
     * @param filePath path of config file
     * @return config file data encapsulated in Configuration object
     */
    static Configuration parseConfigurationFile(String filePath) throws IOException, YAMLException {
        // Verify that file provided is .tailor.yml
        Path path = Paths.get(filePath);
        Path fileName = path.getFileName();
        if (fileName == null || !fileName.toString().equals(".tailor.yml")) {
            throw new YAMLException("Supplied file is not a .tailor.yml file.");
        }

        // Extract information from .tailor.yml
        Constructor constructor = new Constructor(Configuration.class);
        TypeDescription configDescription = new TypeDescription(Configuration.class);
        configDescription.putListPropertyType("include", String.class);
        configDescription.putListPropertyType("exclude", String.class);
        constructor.addTypeDescription(configDescription);
        Yaml yaml = new Yaml(constructor);

        InputStream configFileStream = new FileInputStream(new File(filePath));
        Configuration config = (Configuration) yaml.load(configFileStream);
        config.setFileLocation(filePath);
        // Ignore items in DEFAULT_EXCLUDE_LIST
        config.setExclude(new HashSet<>(Arrays.asList(DEFAULT_EXCLUDE_LIST)));
        // Lint all files and directories by default
        if (config.getInclude().isEmpty()) {
            config.setInclude(new HashSet<>(Arrays.asList(DEFAULT_INCLUDE_LIST)));
        }
        configFileStream.close();
        return config;
    }

}
