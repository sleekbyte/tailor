package com.sleekbyte.tailor.common;

import com.sleekbyte.tailor.listeners.BlankLineListener;
import com.sleekbyte.tailor.listeners.BraceStyleListener;
import com.sleekbyte.tailor.listeners.CommentAnalyzer;
import com.sleekbyte.tailor.listeners.ConstantNamingListener;
import com.sleekbyte.tailor.listeners.FileListener;
import com.sleekbyte.tailor.listeners.ForceTypeCastListener;
import com.sleekbyte.tailor.listeners.KPrefixListener;
import com.sleekbyte.tailor.listeners.LowerCamelCaseListener;
import com.sleekbyte.tailor.listeners.MultipleImportListener;
import com.sleekbyte.tailor.listeners.RedundantParenthesesListener;
import com.sleekbyte.tailor.listeners.SemicolonTerminatedListener;
import com.sleekbyte.tailor.listeners.UpperCamelCaseListener;
import com.sleekbyte.tailor.listeners.WhitespaceListener;
import com.sleekbyte.tailor.utils.ArgumentParser;

/**
 * Enum for all rules implemented in Tailor.
 */
public enum Rules {
    UPPER_CAMEL_CASE("upper-camel-case", UpperCamelCaseListener.class.getName()),
    TERMINATING_SEMICOLON("terminating-semicolon", SemicolonTerminatedListener.class.getName()),
    REDUNDANT_PARENTHESES("redundant-parentheses", RedundantParenthesesListener.class.getName()),
    MULTIPLE_IMPORTS("multiple-imports", MultipleImportListener.class.getName()),
    FUNCTION_WHITESPACE("function-whitespace", BlankLineListener.class.getName()),
    WHITESPACE("whitespace", WhitespaceListener.class.getName()),
    CONSTANT_NAMING("constant-naming", ConstantNamingListener.class.getName()),
    CONSTANT_K_PREFIX("constant-k-prefix", KPrefixListener.class.getName()),
    LOWER_CAMEL_CASE("lower-camel-case", LowerCamelCaseListener.class.getName()),
    BRACE_STYLE("brace-style", BraceStyleListener.class.getName()),
    FORCED_TYPE_CAST("forced-type-cast", ForceTypeCastListener.class.getName()),
    TRAILING_WHITESPACE("trailing-whitespace", FileListener.class.getName()),
    TERMINATING_NEWLINE("file-terminating-newline", FileListener.class.getName()),
    LEADING_WHITESPACE("file-leading-whitespace", FileListener.class.getName()),
    COMMENT_WHITESPACE("comment-whitespace", CommentAnalyzer.class.getName()),

    // Max Length Rules

    MAX_CLASS_LENGTH(ArgumentParser.MAX_NAME_LENGTH_OPT, FileListener.class.getName()),
    MAX_STRUCT_LENGTH(ArgumentParser.MAX_STRUCT_LENGTH_OPT, FileListener.class.getName()),
    MAX_CLOSURE_LENGTH(ArgumentParser.MAX_CLOSURE_LENGTH_OPT, FileListener.class.getName()),
    MAX_FUNCTION_LENGTH(ArgumentParser.MAX_FUNCTION_LENGTH_OPT, FileListener.class.getName()),
    MAX_FILE_LENGTH(ArgumentParser.MAX_FILE_LENGTH_OPT, FileListener.class.getName()),
    MAX_LINE_LENGTH(ArgumentParser.MAX_LINE_LENGTH_LONG_OPT, FileListener.class.getName()),
    MAX_NAME_LENGTH(ArgumentParser.MAX_NAME_LENGTH_OPT, FileListener.class.getName());

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
