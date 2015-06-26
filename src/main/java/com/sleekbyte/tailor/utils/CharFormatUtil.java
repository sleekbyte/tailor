package com.sleekbyte.tailor.utils;

/**
 * Util class for character formatting
 */
public class CharFormatUtil {

    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";

    public static boolean isUpperCamelCase(String word) {
        return !word.isEmpty() && word.matches(ALPHANUMERIC_REGEX) && Character.isUpperCase(word.charAt(0));
    }

    public static boolean isLowerCamelCase(String word) {
        return !word.isEmpty() && word.matches(ALPHANUMERIC_REGEX) && Character.isLowerCase(word.charAt(0));
    }

}
