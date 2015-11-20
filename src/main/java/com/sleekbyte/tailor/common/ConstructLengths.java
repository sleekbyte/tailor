package com.sleekbyte.tailor.common;

/**
 * Keeps track of maximum length restrictions for files and constructs.
 */
public class ConstructLengths {

    public int maxClassLength = 0;
    public int maxClosureLength = 0;
    public int maxFileLength = 0;
    public int maxFunctionLength = 0;
    public int maxLineLength = 0;
    public int maxNameLength = 0;
    public int maxStructLength = 0;

    public int minNameLength = 1;

    public void setMaxClassLength(int maxClassLength) {
        this.maxClassLength = maxClassLength;
    }

    public void setMaxClosureLength(int maxClosureLength) {
        this.maxClosureLength = maxClosureLength;
    }

    public void setMaxFileLength(int maxFileLength) {
        this.maxFileLength = maxFileLength;
    }

    public void setMaxFunctionLength(int maxFunctionLength) {
        this.maxFunctionLength = maxFunctionLength;
    }

    public void setMaxLineLength(int maxLineLength) {
        this.maxLineLength = maxLineLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public void setMaxStructLength(int maxStructLength) {
        this.maxStructLength = maxStructLength;
    }

    public void setMinNameLength(int minNameLength) {
        this.minNameLength = minNameLength;
    }

}
