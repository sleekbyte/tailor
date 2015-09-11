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
    UPPER_CAMEL_CASE("upper-camel-case", UpperCamelCaseListener.class.getName(),
        "class, enum, enum value, struct, and protocol names should follow UpperCamelCase naming convention.",
        ""),
    TERMINATING_SEMICOLON("terminating-semicolon", SemicolonTerminatedListener.class.getName(),
        "Statements should not be terminated by semicolons.",
        ""),
    REDUNDANT_PARENTHESES("redundant-parentheses", RedundantParenthesesListener.class.getName(), "Control flow "
        + "constructs, exception handling constructs, and initializer(s) should not be enclosed in parentheses.",
        ""),
    MULTIPLE_IMPORTS("multiple-imports", MultipleImportListener.class.getName(),
        "Multiple import statements should not be defined on a single line.",
        ""),
    FUNCTION_WHITESPACE("function-whitespace", BlankLineListener.class.getName(),
        "",
        ""),
    WHITESPACE("whitespace", WhitespaceListener.class.getName(),
        "",
        ""),
    CONSTANT_NAMING("constant-naming", ConstantNamingListener.class.getName(),
        "Global constants should follow either UpperCamelCase or lowerCamelCase naming conventions. Local constants "
        + "should follow lowerCamelCase naming conventions.",
        ""),
    CONSTANT_K_PREFIX("constant-k-prefix", KPrefixListener.class.getName(), "Flag constants with prefix k.",
        ""),
    LOWER_CAMEL_CASE("lower-camel-case", LowerCamelCaseListener.class.getName(),
        "Method and variable names should follow lowerCamelCase naming convention.",
        ""),
    BRACE_STYLE("brace-style", BraceStyleListener.class.getName(),
        "Definitions of constructs should follow the One True Brace (OTB) style.",
        ""),
    FORCED_TYPE_CAST("forced-type-cast", ForceTypeCastListener.class.getName(), "Flag force cast usages.",
        ""),
    TRAILING_WHITESPACE("trailing-whitespace", FileListener.class.getName(),
        "Flag spaces or tabs after the last non-whitespace character on the line until the newline.",
        ""),
    TERMINATING_NEWLINE("file-terminating-newline", FileListener.class.getName(),
        "Verify that source files terminate with a single \\n character.",
        ""),
    LEADING_WHITESPACE("file-leading-whitespace", FileListener.class.getName(),
        "Verify that source files begins with a non whitespace character.",
        ""),
    COMMENT_WHITESPACE("comment-whitespace", CommentAnalyzer.class.getName(),
        "",
        ""),

    // Max Length Rules

    MAX_CLASS_LENGTH(ArgumentParser.MAX_CLASS_LENGTH_OPT, FileListener.class.getName(),
        "Enforce a line limit on the lengths of class bodies.",
        ""),
    MAX_STRUCT_LENGTH(ArgumentParser.MAX_STRUCT_LENGTH_OPT, FileListener.class.getName(),
        "Enforce a line limit on the lengths of struct bodies.",
        ""),
    MAX_CLOSURE_LENGTH(ArgumentParser.MAX_CLOSURE_LENGTH_OPT, FileListener.class.getName(),
        "Enforce a line limit on the lengths of closure bodies.",
        ""),
    MAX_FUNCTION_LENGTH(ArgumentParser.MAX_FUNCTION_LENGTH_OPT, FileListener.class.getName(),
        "Enforce a line limit on the lengths of function bodies.",
        ""),
    MAX_FILE_LENGTH(ArgumentParser.MAX_FILE_LENGTH_OPT, FileListener.class.getName(),
        "Enforce a line limit on a file.",
        ""),
    MAX_LINE_LENGTH(ArgumentParser.MAX_LINE_LENGTH_LONG_OPT, FileListener.class.getName(),
        "Enforce a character limit on the length of each line.",
        ""),
    MAX_NAME_LENGTH(ArgumentParser.MAX_NAME_LENGTH_OPT, FileListener.class.getName(),
        "Enforce a character limit on the length of each construct name.",
        "");

    private String name;
    private String className;
    private String description;
    private String link;

    Rules(String name, String className, String description, String link) {
        this.name = name;
        this.className = className;
        this.description = description;
        this.link = link;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return this.className;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLink() {
        return this.link;
    }
}
