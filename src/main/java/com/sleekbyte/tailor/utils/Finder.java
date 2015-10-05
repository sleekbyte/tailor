package com.sleekbyte.tailor.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public Finder(Set<String> includeSet, Set<String> excludeSet) {
        this.includeSet = includeSet;
        this.excludeSet = excludeSet;
    }

    public Set<String> getFileNames() {
        return fileNames;
    }

    // Compares the glob pattern against the file or directory name.
    boolean entityOfInterest(Path file) {
        Path name = file.getFileName();

        if (name == null) {
            return false;
        }

        // If file under inspection is a blacklisted file then return false
        for (String pattern : excludeSet) {
            if (FileSystems.getDefault().getPathMatcher("glob:" + pattern).matches(name)) {
                return false;
            }
        }

        // If file under inspection is a whitelisted swift file then return true
        for (String pattern : includeSet) {
            if (FileSystems.getDefault().getPathMatcher("glob:" + pattern).matches(name)) {
                return true;
            }
        }

        // Handles the case where file/directory is a child of an "include" directory
        return true;
    }

    // Invoke the pattern matching method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // If file cannot be read then throw IOException
        if (!Files.isReadable(file)) {
            throw new IOException("Cannot read " + file);
        }

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
        if (dirName != null && !dirName.toString().equals(".") && !entityOfInterest(dir)) {
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
