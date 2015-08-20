package com.sleekbyte.tailor.integration;

import com.sleekbyte.tailor.common.ExitCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * Integrates Tailor into supplied Xcode project.
 */
public final class XcodeIntegrator {

    /**
     * Integrate Tailor into supplied Xcode project as a build phase run script.
     *
     * @param xcodeprojPath relative/absolute path to a xcodeproj file
     */
    public void setupXcode(String xcodeprojPath) {
        try {
            integrateTailor(getAbsolutePath(xcodeprojPath));
        } catch (IOException | InterruptedException e) {
            System.err.println("Tailor-Xcode Integration failed. Reason: " + e.getMessage());
            System.exit(ExitCode.FAILURE);
        }

        System.exit(ExitCode.SUCCESS);
    }

    private String getAbsolutePath(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()
            && (path.endsWith(".xcodeproj/") || path.endsWith(".xcodeproj"))) {
            return file.getAbsolutePath();
        } else {
            throw new IOException(path + " is not a valid .xcodeproj");
        }
    }

    private void integrateTailor(String absolutePath) throws IOException, InterruptedException {
        File tempScript = createTempRubyScript(absolutePath);

        try {
            ProcessBuilder pb = new ProcessBuilder("ruby", tempScript.toString());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } finally {
            if (!tempScript.delete()) {
                throw new FileNotFoundException("Failed to delete file " + tempScript );
            }
        }
    }

    private File createTempRubyScript(String absolutePath) throws IOException {
        File tempScript = File.createTempFile("xcode_integrate", "rb");
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);

        printWriter.println("#!/usr/bin/env ruby");
        printWriter.println("require 'xcodeproj'");
        printWriter.println("begin");
        printWriter.println(String.format("\tproject = Xcodeproj::Project.open(\"%s\")", absolutePath));
        printWriter.println("rescue");
        printWriter.println("\tabort(\"Integration Error: Invalid .xcodeproj file\")");
        printWriter.println("end");
        printWriter.println("main_target = project.targets.first");
        printWriter.println("phase = main_target.new_shell_script_build_phase(\"Tailor\")");
        printWriter.println("phase.shell_script = \"/usr/local/bin/tailor\"");
        printWriter.println("project.save()");

        printWriter.close();
        streamWriter.close();

        return tempScript;
    }
}
