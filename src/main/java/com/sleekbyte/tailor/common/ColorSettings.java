package com.sleekbyte.tailor.common;

/**
 * Stores settings corresponding to colorized console output.
 */
public class ColorSettings {

    public boolean colorOutput = false;
    public boolean invertColor = false;

    public ColorSettings(boolean colorOutput, boolean invertColor) {
        this.colorOutput = colorOutput;
        this.invertColor = invertColor;
    }

}
