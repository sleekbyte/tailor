package com.sleekbyte.tailor.utils;

/**
 * Custom exception for failure to parse options in {@link CliArgumentParser}.
 */
public class CliArgumentParserException extends Exception {

    public CliArgumentParserException(String message) {
        super(message);
    }

}
