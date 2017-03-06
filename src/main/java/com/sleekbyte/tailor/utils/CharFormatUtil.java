package com.sleekbyte.tailor.utils;

/**
 * Util class for character formatting.
 */
public final class CharFormatUtil {

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

    public static boolean isLowerCamelCaseOrAcronym(String word) {
        return startsWithAcronym(word) || isLowerCamelCase(word);
    }

    public static boolean isEnclosedInBackticks(String identifier) {
        int length = identifier.length();
        return length >= 2 && identifier.charAt(0) == '`' && identifier.charAt(length - 1) == '`';
    }

    /**
     * Will strip leading and trailing ` character in given string if both present.
     *
     * @param identifier value to sanitize
     * @return sanitized string
     */
    public static String unescapeIdentifier(String identifier) {
        int length = identifier.length();
        if (isEnclosedInBackticks(identifier)) {
            return identifier.substring(1, length - 1);
        }
        return identifier;
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
