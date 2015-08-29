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
    public static int setupXcode(String xcodeprojPath) {
        try {
            integrateTailor(getAbsolutePath(xcodeprojPath));
        } catch (IOException | InterruptedException e) {
            System.err.println("Could not add Tailor Build Phase Run Script to " + xcodeprojPath + "\nReason: "
                                + e.getMessage());
            return ExitCode.FAILURE;
        }

        return ExitCode.SUCCESS;
    }

    private static String getAbsolutePath(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()
            && (path.endsWith(".xcodeproj/") || path.endsWith(".xcodeproj"))) {
            return file.getAbsolutePath();
        } else {
            throw new IOException(path + " is not a valid .xcodeproj");
        }
    }

    private static void integrateTailor(String absolutePath) throws IOException, InterruptedException {
        File tempScript = createTempRubyScript(absolutePath);
        ProcessBuilder pb = new ProcessBuilder("ruby", tempScript.toString());
        pb.inheritIO();
        Process process = pb.start();
        process.waitFor();
        if (!tempScript.delete()) {
            throw new FileNotFoundException("Failed to delete file " + tempScript);
        }
    }

    private static File createTempRubyScript(String absolutePath) throws IOException {
        File tempScript = File.createTempFile("xcode_integrate", "rb");
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);

        printWriter.print(String.join(System.getProperty("line.separator"),
            "#!/usr/bin/env ruby",
            "ENV['GEM_HOME'] = '/usr/local/tailor/gems/installed'",
            "ENV['GEM_PATH'] = '/usr/local/tailor/gems/installed'",
            "cmd = \"gem install --local --no-rdoc --no-ri xcodeproj-*.gem\"",
            "Dir.chdir('/usr/local/tailor/gems/vendor/cache'){ %x[#{cmd}] }",
            "require 'xcodeproj'",
            "begin",
            String.format("\tproject = Xcodeproj::Project.open(\"%s\")", absolutePath),
            "rescue",
            "\tabort(\"Integration Error: Invalid .xcodeproj file\")",
            "end",
            "main_target = project.targets.first",
            "phase = main_target.new_shell_script_build_phase(\"Tailor\")",
            "phase.shell_script = \"/usr/local/bin/tailor\"",
            "project.save()"
        ));

        printWriter.close();
        streamWriter.close();

        return tempScript;
    }
}
