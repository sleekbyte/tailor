package com.sleekbyte.tailor.output;

/**
 * Handle system exit calls.
 */
public final class TailorExit {

    /**
     * Exit codes.
     */
    public enum ExitCode {
        SUCCESS, FAILURE
    }

    public static void exit(ExitCode exitCode) {
        System.exit(exitCode.ordinal());
    }

    public static void failure() {
        exit(ExitCode.FAILURE);
    }

    public static void success() {
        exit(ExitCode.SUCCESS);
    }

}
