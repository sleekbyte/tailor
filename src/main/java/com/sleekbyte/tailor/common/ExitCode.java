package com.sleekbyte.tailor.common;


/**
 * Exit codes.
 */
public enum ExitCode {
    SUCCESS, FAILURE;

    public static int success() {
        return SUCCESS.ordinal();
    }

    public static int failure() {
        return FAILURE.ordinal();
    }

}
