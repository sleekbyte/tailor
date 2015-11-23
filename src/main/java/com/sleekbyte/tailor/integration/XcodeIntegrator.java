package com.sleekbyte.tailor.integration;

import com.sleekbyte.tailor.common.ExitCode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Integrates Tailor into supplied Xcode project.
 */
public final class XcodeIntegrator {

    public static final String BEGINNING_OF_INPUT_PATTERN = "\\A";

    /**
     * Integrate Tailor into supplied Xcode project as a build phase run script.
     *
     * @param xcodeprojPath relative/absolute path to a xcodeproj file
     */
    public static int setupXcode(String xcodeprojPath) {
        try {
            integrateTailor(getAbsolutePath(xcodeprojPath));
        } catch (IOException | InterruptedException e) {
            System.err.println("Could not add Tailor Build Phase Run Script to " + xcodeprojPath + "\nReason: "
                + e.getMessage());
            return ExitCode.failure();
        }

        return ExitCode.success();
    }

    protected static String getAbsolutePath(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()
            && (path.endsWith(".xcodeproj/") || path.endsWith(".xcodeproj"))) {
            return file.getCanonicalPath();
        } else {
            throw new IOException(path + " is not a valid .xcodeproj");
        }
    }

    protected static void integrateTailor(String absolutePath) throws IOException, InterruptedException {
        File tempScript = createTempRubyScript(absolutePath);
        ProcessBuilder pb = new ProcessBuilder("ruby", tempScript.toString());
        pb.inheritIO().start().waitFor();
        if (!tempScript.delete()) {
            throw new FileNotFoundException("Failed to delete file " + tempScript);
        }
    }

    protected static File createTempRubyScript(String absolutePath) throws IOException {
        File tempScript = File.createTempFile("xcode_integrate", "rb");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tempScript.toPath(), Charset.defaultCharset())) {
            InputStream in = XcodeIntegrator.class.getResourceAsStream("xcode_integrate.rb");
            if (in != null) {
                String scriptText =
                    new Scanner(in, Charset.defaultCharset().name()).useDelimiter(BEGINNING_OF_INPUT_PATTERN).next();
                String scriptContent = String.format(scriptText, absolutePath);
                bufferedWriter.write(scriptContent);
            }
        }
        return tempScript;
    }
}
