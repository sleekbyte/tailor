package com.sleekbyte.tailor.integration;

import com.sleekbyte.tailor.common.ExitCode;

import java.io.File;
import java.io.IOException;

/**
 * Integrates Tailor into supplied Xcode project.
 */
public final class XcodeIntegrator {

    private static final String xcodeIntegrationRubyScriptPath = "";

    /**
     * Integrate Tailor into supplied Xcode project as a build phase run script.
     *
     * @param relXcodeprojFilePath relative/absolute path to a xcodeproj file
     */
    public static void setupXcode(String relXcodeprojFilePath) {

        try {
            String absXcodeprojFilePath = getAbsolutePath(relXcodeprojFilePath);
            System.out.println("ABSOLUTE PATH: " + absXcodeprojFilePath);
        } catch (IOException e) {
            System.err.println("Tailor-Xcode Integration failed. Reason: " + e.getMessage());
            System.exit(ExitCode.FAILURE);
        }

        System.exit(ExitCode.SUCCESS);
    }

    private static String getAbsolutePath(String relativePath) throws IOException {
        File file = new File(relativePath);
        if (file.isDirectory()
            && (relativePath.endsWith(".xcodeproj/") || relativePath.endsWith(".xcodeproj"))) {
            return file.getAbsolutePath();
        } else {
            throw new IOException(relativePath + " is not a valid .xcodeproj");
        }
    }
}
