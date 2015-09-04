package com.sleekbyte.tailor.common;

import com.sleekbyte.tailor.listeners.BlankLineListener;
import com.sleekbyte.tailor.listeners.BraceStyleListener;
import com.sleekbyte.tailor.listeners.ConstantNamingListener;
import com.sleekbyte.tailor.listeners.ForceTypeCastListener;
import com.sleekbyte.tailor.listeners.KPrefixListener;
import com.sleekbyte.tailor.listeners.LowerCamelCaseListener;
import com.sleekbyte.tailor.listeners.MultipleImportListener;
import com.sleekbyte.tailor.listeners.RedundantParenthesisListener;
import com.sleekbyte.tailor.listeners.SemicolonTerminatedListener;
import com.sleekbyte.tailor.listeners.UpperCamelCaseListener;
import com.sleekbyte.tailor.listeners.WhitespaceListener;

/**
 * Enum for all rules implemented in Tailor.
 */
public enum Rules {
    UPPER_CAMEL_CASE ("upperCamelCase", UpperCamelCaseListener.class.getName()),
    SEMICOLON_TERMINATED ("semicolonTerminated", SemicolonTerminatedListener.class.getName()),
    REDUNDANT_PARENTHESES ("redundantParentheses", RedundantParenthesisListener.class.getName()),
    MULTIPLE_IMPORT ("multipleImports", MultipleImportListener.class.getName()),
    BLANK_LINE_FUNCTION ("blankLinesAroundFunction", BlankLineListener.class.getName()),
    WHITESPACE ("whitespace", WhitespaceListener.class.getName()),
    CONSTANT_NAMING ("constantNaming", ConstantNamingListener.class.getName()),
    K_PREFIXED ("kPrefixed", KPrefixListener.class.getName()),
    LOWER_CAMEL_CASE ("lowerCamelCase", LowerCamelCaseListener.class.getName()),
    BRACE_STYLE ("braceStyle", BraceStyleListener.class.getName()),
    FORCE_TYPE_CAST ("forceTypeCast", ForceTypeCastListener.class.getName());

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
