package com.sleekbyte.tailor.format;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link com.sleekbyte.tailor.output.ViolationMessage} output formats.
 */
public enum Format {
    XCODE,
    JSON;

    private String name;
    private String className;
    private String description;

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Exception thrown when invalid format is provided.
     */
    public static class IllegalFormatException extends Exception {}

    /**
     * Parse str and convert to appropriate Format.
     *
     * @param str Format string
     * @return Parsed Format value
     * @throws IllegalFormatException if string is not recognized
     */
    public static Format parseFormat(String str) throws IllegalFormatException {
        Set<String> formats = Arrays.asList(values()).stream().map(Format::toString).collect(Collectors.toSet());
        if (formats.contains(str)) {
            return valueOf(str.toUpperCase());
        } else {
            throw new IllegalFormatException();
        }
    }

    public static String getFormats() {
        return String.join("|",
            Arrays.asList(Format.values()).stream().map(Format::getName).collect(Collectors.toList()));
    }

    static {
        XCODE.name = "xcode";
        XCODE.description = "Output that displays directly in the Xcode editor when run from a Build Phase Run Script.";
        XCODE.className = XcodeFormatter.class.getName();

        JSON.name = "json";
        JSON.description = "Valid JSON format.";
        JSON.className = JSONFormatter.class.getName();
    }
}
