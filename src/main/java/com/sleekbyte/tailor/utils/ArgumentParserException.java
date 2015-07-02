package com.sleekbyte.tailor.utils;

/**
 * Custom exception for failure to parse options in {@link ArgumentParser}.
 */
public class ArgumentParserException extends Exception {

    public ArgumentParserException(String message) {
        super(message);
    }

}
