package com.sleekbyte.tailor.format;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * {@link com.sleekbyte.tailor.output.ViolationMessage} output formats.
 */
public enum Format {
    XCODE,
    JSON,
    CC,
    HTML;

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
        for (Format format : Format.values()) {
            if (format.getName().equals(str)) {
                return format;
            }
        }
        throw new IllegalFormatException();
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

        CC.name = "cc";
        CC.description = "Format for integration with Code Climate.";
        CC.className = CCFormatter.class.getName();

        HTML.name = "html";
        HTML.description = "Valid HTML format.";
        HTML.className = HTMLFormatter.class.getName();
    }
}
