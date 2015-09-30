package com.sleekbyte.tailor.utils;

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


/**
 * Parse .tailor.yml config file.
 */
public final class ConfigurationParser {

    /**
     * Parse .tailor.yml config file.
     *
     * @param filePath path of config file
     * @return config file data encapsulated in Configuration object
     */
    public static Configuration parseConfigurationFile(String filePath) throws IOException, YAMLException {
        // Verify that file provided is .tailor.yml
        Path path = Paths.get(filePath);
        Path fileName = path.getFileName();
        if (fileName != null && !fileName.toString().equals(".tailor.yml")) {
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
        configFileStream.close();
        return config;
    }

}
