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

    static String getAbsolutePath(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()
            && (path.endsWith(".xcodeproj/") || path.endsWith(".xcodeproj"))) {
            return file.getCanonicalPath();
        } else {
            throw new IOException(path + " is not a valid .xcodeproj");
        }
    }

    static void integrateTailor(String absolutePath) throws IOException, InterruptedException {
        File tempScript = createTempRubyScript(absolutePath);
        ProcessBuilder pb = new ProcessBuilder("ruby", tempScript.toString());
        pb.inheritIO();
        Process process = pb.start();
        process.waitFor();
        if (!tempScript.delete()) {
            throw new FileNotFoundException("Failed to delete file " + tempScript);
        }
    }

    static File createTempRubyScript(String absolutePath) throws IOException {
        File tempScript = File.createTempFile("xcode_integrate", "rb");
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript), Charset.forName("UTF-8"));
        PrintWriter printWriter = new PrintWriter(streamWriter);

        printWriter.print(String.join(System.getProperty("line.separator"),
            "#!/usr/bin/env ruby",
            "require 'pathname'",
            "# http://stackoverflow.com/a/5471032",
            "def which(cmd)",
            "  exts = ENV['PATHEXT'] ? ENV['PATHEXT'].split(';') : ['']",
            "  ENV['PATH'].split(File::PATH_SEPARATOR).each do |path|",
            "    exts.each { |ext|",
            "      exe = File.join(path, \"#{cmd}#{ext}\")",
            "      return exe if File.executable?(exe) && !File.directory?(exe)",
            "    }",
            "  end",
            "  return nil",
            "end",
            "def find_tailor()",
            "    return Pathname.new(File.realpath(which('tailor'))) + '../..'",
            "end",
            "tailor_gems_dir = find_tailor + 'gems/installed'",
            "tailor_cache_dir = find_tailor + 'gems/vendor/cache'",
            "ENV['GEM_HOME'] = tailor_gems_dir.to_s",
            "ENV['GEM_PATH'] = tailor_gems_dir.to_s",
            "cmd = \"gem install --local --no-rdoc --no-ri xcodeproj-*.gem\"",
            "Dir.chdir(tailor_cache_dir.to_s){ %x[#{cmd}] }",
            "require 'xcodeproj'",
            "begin",
            String.format("  project = Xcodeproj::Project.open(\"%s\")", absolutePath),
            "rescue",
            "  abort(\"Integration Error: Invalid .xcodeproj file\")",
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
