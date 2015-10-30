package com.sleekbyte.tailor.utils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Finds all files that match the specified pattern.
 */
public final class Finder extends SimpleFileVisitor<Path> {

    private Set<String> fileNames = new TreeSet<>();
    private Set<PathMatcher> includeMatcher;
    private Set<PathMatcher> excludeMatcher;
    private URI base;
    private Map<Path, Boolean> isParentIncluded = new HashMap<>();

    /**
     * Finder constructor.
     *
     * @param includeSet file paths that should be analyzed
     * @param excludeSet file paths that should be ignored
     * @param base location of .tailor.yml file
     */
    public Finder(Set<String> includeSet, Set<String> excludeSet, URI base) {
        this.includeMatcher = includeSet
            .stream()
            .map(includePattern -> FileSystems.getDefault().getPathMatcher("glob:" + includePattern))
            .collect(Collectors.toSet());
        this.excludeMatcher = excludeSet
            .stream()
            .map(excludePattern -> FileSystems.getDefault().getPathMatcher("glob:" + excludePattern))
            .collect(Collectors.toSet());
        this.base = base;
    }

    public Set<String> getFileNames() {
        return fileNames;
    }

    // Invoke the pattern matching method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        Path relative = Paths.get(base.relativize(file.toUri()).getPath());
        if (excludeMatcher.stream().anyMatch(pathMatcher -> pathMatcher.matches(relative))) {
            return FileVisitResult.CONTINUE;
        }
        if ((isParentIncluded.getOrDefault(file.getParent(), false)
            || includeMatcher.stream().anyMatch(pathMatcher -> pathMatcher.matches(relative)))
            && (file.toFile().getCanonicalPath().endsWith(".swift") && file.toFile().canRead())) {
            fileNames.add(file.toFile().getCanonicalPath());
        }
        return FileVisitResult.CONTINUE;
    }

    // Invoke the pattern matching method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attributes) {
        Path relative = Paths.get(base.relativize(dir.toUri()).getPath());
        if (excludeMatcher.stream().anyMatch(pathMatcher -> pathMatcher.matches(relative))) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        if (isParentIncluded.getOrDefault(dir.getParent(), false)
            || includeMatcher.stream().anyMatch(pathMatcher -> pathMatcher.matches(relative))) {
            isParentIncluded.put(dir, true);
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        isParentIncluded.put(dir, false);
        return super.postVisitDirectory(dir, exc);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc.getMessage());
        return FileVisitResult.CONTINUE;
    }
}
