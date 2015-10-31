package com.sleekbyte.tailor.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represent a config object.
 */
public final class Configuration {

    private static final String[] DEFAULT_INCLUDE = new String[] {"**.swift"};
    private static final String[] DEFAULT_EXCLUDE = new String[] {"**.{svn,git,lproj,xcassets,framework,xcodeproj}"};

    private Optional<String> fileLocation = Optional.empty();
    private Set<String> include = new HashSet<>(Arrays.asList(DEFAULT_INCLUDE));
    private Set<String> exclude = new HashSet<>(Arrays.asList(DEFAULT_EXCLUDE));

    public Optional<String> getFileLocation() {
        return fileLocation;
    }

    public Set<String> getInclude() {
        return include;
    }

    public Set<String> getExclude() {
        return exclude;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = Optional.ofNullable(fileLocation);
    }

    public void setInclude(Set<String> include) {
        this.include = include;
    }

    public void setExclude(Set<String> exclude) {
        this.exclude = exclude;
    }
}
