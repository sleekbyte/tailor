package com.sleekbyte.tailor.common;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent a .tailor.yml config object.
 */
public final class Configuration {

    private String fileLocation;
    private Set<String> include = new HashSet<>();
    private Set<String> exclude = new HashSet<>();

    public String getFileLocation() {
        return fileLocation;
    }

    public Set<String> getExclude() {
        return exclude;
    }

    public Set<String> getInclude() {
        return include;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void setInclude(Set<String> include) {
        this.include.addAll(include);
    }

    public void setExclude(Set<String> exclude) {
        this.exclude.addAll(exclude);
    }
}
