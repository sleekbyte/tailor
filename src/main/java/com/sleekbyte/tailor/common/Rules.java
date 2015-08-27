package com.sleekbyte.tailor.common;

import com.sleekbyte.tailor.listeners.ConstructListener;
import com.sleekbyte.tailor.listeners.LowerCamelCaseListener;
import com.sleekbyte.tailor.listeners.SemicolonTerminatedListener;
import com.sleekbyte.tailor.listeners.UpperCamelCaseListener;

/**
 * Enum for all rules implemented in Tailor.
 */
public enum Rules {
    UPPER_CAMEL_CASE ("upperCamelCase", UpperCamelCaseListener.class.getName()),
    SEMICOLON_TERMINATED ("semicolonTerminated", SemicolonTerminatedListener.class.getName()),
    CONSTANT_NAMING ("constantNaming", ConstructListener.class.getName()),
    K_PREFIXED ("kPrefixed", ConstructListener.class.getName()),
    LOWER_CAMEL_CASE ("lowerCamelCase", LowerCamelCaseListener.class.getName());

    private String name;
    private String className;

    Rules(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return this.className;
    }

}
