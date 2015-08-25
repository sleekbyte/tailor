package com.sleekbyte.tailor.common;

import com.sleekbyte.tailor.listeners.MainListener;

public enum Rules {
    UPPER_CAMEL_CASE ("upperCamelCase", MainListener.class.getName());

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
