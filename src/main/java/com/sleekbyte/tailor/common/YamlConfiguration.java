package com.sleekbyte.tailor.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represent a config object.
 */
public final class YamlConfiguration {

    private static final String[] DEFAULT_INCLUDE = new String[] {"**.swift"};
    private static final String[] DEFAULT_EXCLUDE = new String[] {"**.{svn,git,lproj,xcassets,framework,xcodeproj}"};

    private Optional<String> fileLocation = Optional.empty();
    private Set<String> include = new HashSet<>(Arrays.asList(DEFAULT_INCLUDE));
    private Set<String> exclude = new HashSet<>(Arrays.asList(DEFAULT_EXCLUDE));
    private Set<String> only = new HashSet<>();
    private Set<String> except = new HashSet<>();
    private String format = "";
    private boolean debug = false;
    private String color = "";
    private int purge = 0;

    private boolean purgeSet = false;

    public String getFormat() {
        return format;
    }

    public Optional<String> getFileLocation() {
        return fileLocation;
    }

    public Set<String> getInclude() {
        return include;
    }

    public Set<String> getExclude() {
        return exclude;
    }

    public Set<String> getOnly() {
        return only;
    }

    public Set<String> getExcept() {
        return except;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getColor() {
        return color;
    }

    public int getPurge() {
        return purge;
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

    public void setOnly(Set<String> only) {
        this.only = only;
    }

    public void setExcept(Set<String> except) {
        this.except = except;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPurge(int purge) {
        this.purge = purge;
        purgeSet = true;
    }

    public boolean isPurgeSet() {
        return purgeSet;
    }

}
