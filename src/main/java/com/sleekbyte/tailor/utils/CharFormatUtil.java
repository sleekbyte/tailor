package com.sleekbyte.tailor.utils;

/**
 * Util class for character formatting.
 */
public class CharFormatUtil {

    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";

    public static boolean isUpperCamelCase(String word) {
        return !word.isEmpty() && Character.isUpperCase(word.charAt(0)) && word.matches(ALPHANUMERIC_REGEX);
    }

    public static boolean isLowerCamelCase(String word) {
        return !word.isEmpty() && Character.isLowerCase(word.charAt(0)) && word.matches(ALPHANUMERIC_REGEX);
    }

    public static boolean startsWithAcronym(String word) {
        return word.length() >= 2 && Character.isUpperCase(word.charAt(0)) && Character.isUpperCase(word.charAt(1))
            && word.matches(ALPHANUMERIC_REGEX);
    }

    /**
     * Checks if a name is prefixed with a 'k' or 'K'.
     *
     * @param name the name of an identifier
     * @return true if name is prefixed with a 'k' or 'K'
     */
    public static boolean isKPrefixed(String name) {
        if (name.length() < 2) {
            return false;
        }
        if (isUpperCamelCase(name)) {
            return name.charAt(0) == 'K' && Character.isUpperCase(name.charAt(1));
        }
        if (isLowerCamelCase(name)) {
            return name.charAt(0) == 'k' && Character.isUpperCase(name.charAt(1));
        }
        return false;
    }

}
