package com.sleekbyte.tailor.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent a .tailor.yml config object.
 */
public final class Configuration {

    private Set<String> include = new HashSet<>();
    private Set<String> exclude = new HashSet<>();

    public Set<String> getExclude() {
        return exclude;
    }

    public Set<String> getInclude() {
        return include;
    }

    public void setInclude(Set<String> include) {
        this.include = include;
    }

    public void setExclude(Set<String> exclude) {
        this.exclude = exclude;
    }

}
