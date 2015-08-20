package com.sleekbyte.tailor.integration;

import com.sleekbyte.tailor.common.ExitCode;

import java.io.IOException;

/**
 * Integrates Tailor into supplied Xcode project.
 */
public final class XcodeIntegrator {

    private static final String xcodeIntegrationRubyScriptPath = "path to ruby script";

    /**
     * Integrate Tailor into suppled Xcode project as a build phase run script.
     *
     * @param relXcodeprojFilePath relative/absolute path to a xcodeproj file
     */
    public static void setupXcode(String relXcodeprojFilePath) {
        String[] commands = {xcodeIntegrationRubyScriptPath, "absolute path to xcodeproj"};
        try {
            Process process = Runtime.getRuntime().exec(commands);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Tailor - Xcode integration failed. Reason: " + e.getMessage());
            System.exit(ExitCode.FAILURE);
        }
        System.exit(ExitCode.SUCCESS);
    }
}
