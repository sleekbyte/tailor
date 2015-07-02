package com.sleekbyte.tailor.common;

/**
 * Stores location corresponding to a character in a source file.
 */
public class Location {

    public int line;
    public int column;

    public Location(int line) {
        this.line = line;
    }

    public Location(int line, int column) {
        this.line = line;
        this.column = column;
    }

}
