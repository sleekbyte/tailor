package com.sleekbyte.tailor.common;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Helper to retrieve rule examples.
 */
public class RuleExamples {

    private static final String MARKDOWN_EXT = ".md";
    private static final String BEGINNING_OF_INPUT_PATTERN = "\\A";

    /**
     * Load rule examples for a given rule from Markdown documentation.
     * @param rule name of the rule for which to load documentation
     * @return Markdown documentation for a given rule
     */
    public static String get(String rule) {
        InputStream in = Rules.class.getResourceAsStream(rule + MARKDOWN_EXT);
        return in == null ? "" :
            new Scanner(in, Charset.defaultCharset().name()).useDelimiter(BEGINNING_OF_INPUT_PATTERN).next();
    }

}
