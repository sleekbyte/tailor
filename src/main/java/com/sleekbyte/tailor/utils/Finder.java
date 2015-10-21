package com.sleekbyte.tailor.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Finds all files that match the specified pattern.
 */
public final class Finder extends SimpleFileVisitor<Path> {

    private Set<String> includeSet = new HashSet<>();
    private Set<String> excludeSet = new HashSet<>();
    private Set<String> fileNames = new TreeSet<>();
    private String base = "";

    /**
     * Finder constructor.
     *
     * @param includeSet file paths that should be analyzed
     * @param excludeSet file paths that should be ignored
     * @param base location of .tailor.yml file
     */
    public Finder(Set<String> includeSet, Set<String> excludeSet, String base) {
        this.includeSet = includeSet;
        this.excludeSet = excludeSet;
        this.base = base;
    }

    public Set<String> getFileNames() {
        return fileNames;
    }

    // Compares the glob pattern against the file or directory name.
    boolean entityOfInterest(Path file) {
        // Extract relative path of file
        Path relative = Paths.get(new File(base).toURI()
            .relativize(new File(file.toAbsolutePath().toString()).toURI())
            .getPath());

        if (relative == null) {
            return false;
        }

        // If file under inspection is a blacklisted file then return false
        for (String pattern : excludeSet) {
            if (FileSystems.getDefault().getPathMatcher("glob:" + pattern).matches(relative)) {
                return false;
            }
        }

        // If file under inspection is a whitelisted swift file then return true
        for (String pattern : includeSet) {
            if (FileSystems.getDefault().getPathMatcher("glob:" + pattern).matches(relative)) {
                return true;
            }
        }

        // Traverse through included directory children
        return Files.isDirectory(file);
    }

    // Invoke the pattern matching method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // If file cannot be read then throw IOException
        if (!Files.isReadable(file)) {
            throw new IOException("Cannot read " + file);
        }

        // Ensure only Swift files are linted
        if (entityOfInterest(file) && file.toString().endsWith(".swift")) {
            fileNames.add(file.toAbsolutePath().toString());
        }

        return FileVisitResult.CONTINUE;
    }

    // Invoke the pattern matching method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        // Do not analyze the directory's children if blacklisted.
        Path dirName = dir.getFileName();
        if (dirName != null && !entityOfInterest(dir)) {
            return FileVisitResult.SKIP_SUBTREE;
        } else {
            return FileVisitResult.CONTINUE;
        }
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc.getMessage());
        return FileVisitResult.CONTINUE;
    }
}
