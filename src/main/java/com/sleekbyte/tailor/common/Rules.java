package com.sleekbyte.tailor.common;

public enum Rules {
    UPPER_CAMEL_CASE ("upperCamelCase");

    private String name;

    Rules(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
