package com.sleekbyte.tailor.common;

import com.sleekbyte.tailor.listeners.ArrowWhitespaceListener;
import com.sleekbyte.tailor.listeners.BlankLineListener;
import com.sleekbyte.tailor.listeners.BraceStyleListener;
import com.sleekbyte.tailor.listeners.ColonWhitespaceListener;
import com.sleekbyte.tailor.listeners.CommentWhitespaceListener;
import com.sleekbyte.tailor.listeners.ConstantNamingListener;
import com.sleekbyte.tailor.listeners.FileListener;
import com.sleekbyte.tailor.listeners.ForceTypeCastListener;
import com.sleekbyte.tailor.listeners.KPrefixListener;
import com.sleekbyte.tailor.listeners.LowerCamelCaseListener;
import com.sleekbyte.tailor.listeners.MultipleImportListener;
import com.sleekbyte.tailor.listeners.RedundantParenthesesListener;
import com.sleekbyte.tailor.listeners.SemicolonTerminatedListener;
import com.sleekbyte.tailor.listeners.TodoCommentListener;
import com.sleekbyte.tailor.listeners.UpperCamelCaseListener;
import com.sleekbyte.tailor.listeners.WhitespaceListener;
import com.sleekbyte.tailor.utils.ArgumentParser;

/**
 * Enum for all rules implemented in Tailor.
 */
public enum Rules {
    ARROW_WHITESPACE,
    BRACE_STYLE,
    COLON_WHITESPACE,
    COMMENT_WHITESPACE,
    CONSTANT_K_PREFIX,
    CONSTANT_NAMING,
    FORCED_TYPE_CAST,
    FUNCTION_WHITESPACE,
    LEADING_WHITESPACE,
    LOWER_CAMEL_CASE,
    MAX_CLASS_LENGTH,
    MAX_CLOSURE_LENGTH,
    MAX_FILE_LENGTH,
    MAX_FUNCTION_LENGTH,
    MAX_LINE_LENGTH,
    MAX_NAME_LENGTH,
    MAX_STRUCT_LENGTH,
    MULTIPLE_IMPORTS,
    REDUNDANT_PARENTHESES,
    TERMINATING_NEWLINE,
    TERMINATING_SEMICOLON,
    TODO_SYNTAX,
    TRAILING_WHITESPACE,
    UPPER_CAMEL_CASE,
    WHITESPACE;

    private static final String BASE_STYLE_GUIDE_LINK = "https://github.com/sleekbyte/tailor/wiki/Rules#";
    private String name;
    private String className;
    private String description;

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
        return BASE_STYLE_GUIDE_LINK + this.getName();
    }

    static {
        ARROW_WHITESPACE.name = "arrow-whitespace";
        ARROW_WHITESPACE.description = "Flags all return arrows (->) that are not space delimited.";
        ARROW_WHITESPACE.className = ArrowWhitespaceListener.class.getName();

        BRACE_STYLE.name = "brace-style";
        BRACE_STYLE.description = "Definitions of constructs should follow the One True Brace Style (1TBS).";
        BRACE_STYLE.className = BraceStyleListener.class.getName();

        COLON_WHITESPACE.name = "colon-whitespace";
        COLON_WHITESPACE.description = "Flag whitespace violations around colons (:).";
        COLON_WHITESPACE.className = ColonWhitespaceListener.class.getName();

        COMMENT_WHITESPACE.name = "comment-whitespace";
        COMMENT_WHITESPACE.description = "Ensure at least one whitespace character after a comment opening symbol"
            + " (// or /*) and at least one whitespace character before a comment closing symbol (*/).";
        COMMENT_WHITESPACE.className = CommentWhitespaceListener.class.getName();

        CONSTANT_K_PREFIX.name = "constant-k-prefix";
        CONSTANT_K_PREFIX.description = "Flag constants with prefix k.";
        CONSTANT_K_PREFIX.className = KPrefixListener.class.getName();

        CONSTANT_NAMING.name = "constant-naming";
        CONSTANT_NAMING.description = "Global constants should follow either UpperCamelCase or lowerCamelCase naming "
            + "conventions. Local constants should follow lowerCamelCase naming conventions.";
        CONSTANT_NAMING.className = ConstantNamingListener.class.getName();

        FORCED_TYPE_CAST.name = "forced-type-cast";
        FORCED_TYPE_CAST.description = "Flag uses of the forced form of the type cast operator (as!).";
        FORCED_TYPE_CAST.className = ForceTypeCastListener.class.getName();

        FUNCTION_WHITESPACE.name = "function-whitespace";
        FUNCTION_WHITESPACE.description = "Every function declaration except those at the start and end of file "
            + "should have one blank line before and after itself.";
        FUNCTION_WHITESPACE.className = BlankLineListener.class.getName();

        LEADING_WHITESPACE.name = "leading-whitespace";
        LEADING_WHITESPACE.description = "Verify that source files begin with a non-whitespace character.";
        LEADING_WHITESPACE.className = FileListener.class.getName();

        LOWER_CAMEL_CASE.name = "lower-camel-case";
        LOWER_CAMEL_CASE.description = "Method and variable names should follow lowerCamelCase naming convention.";
        LOWER_CAMEL_CASE.className = LowerCamelCaseListener.class.getName();

        MAX_CLASS_LENGTH.name = ArgumentParser.MAX_CLASS_LENGTH_OPT;
        MAX_CLASS_LENGTH.description = "Enforce a line limit on the lengths of class bodies.";
        MAX_CLASS_LENGTH.className = FileListener.class.getName();

        MAX_CLOSURE_LENGTH.name = ArgumentParser.MAX_CLOSURE_LENGTH_OPT;
        MAX_CLOSURE_LENGTH.description = "Enforce a line limit on the lengths of closure bodies.";
        MAX_CLOSURE_LENGTH.className = FileListener.class.getName();

        MAX_FILE_LENGTH.name = ArgumentParser.MAX_FILE_LENGTH_OPT;
        MAX_FILE_LENGTH.description = "Enforce a line limit on each file.";
        MAX_FILE_LENGTH.className = FileListener.class.getName();

        MAX_FUNCTION_LENGTH.name = ArgumentParser.MAX_FUNCTION_LENGTH_OPT;
        MAX_FUNCTION_LENGTH.description = "Enforce a line limit on the lengths of function bodies.";
        MAX_FUNCTION_LENGTH.className = FileListener.class.getName();

        MAX_LINE_LENGTH.name = ArgumentParser.MAX_LINE_LENGTH_LONG_OPT;
        MAX_LINE_LENGTH.description = "Enforce a character limit on the length of each line.";
        MAX_LINE_LENGTH.className = FileListener.class.getName();

        MAX_NAME_LENGTH.name = ArgumentParser.MAX_NAME_LENGTH_OPT;
        MAX_NAME_LENGTH.description = "Enforce a character limit on the length of each construct name.";
        MAX_NAME_LENGTH.className = FileListener.class.getName();

        MAX_STRUCT_LENGTH.name = ArgumentParser.MAX_STRUCT_LENGTH_OPT;
        MAX_STRUCT_LENGTH.description = "Enforce a line limit on the lengths of struct bodies.";
        MAX_STRUCT_LENGTH.className = FileListener.class.getName();

        MULTIPLE_IMPORTS.name = "multiple-imports";
        MULTIPLE_IMPORTS.description = "Multiple import statements should not be defined on a single line.";
        MULTIPLE_IMPORTS.className = MultipleImportListener.class.getName();

        REDUNDANT_PARENTHESES.name = "redundant-parentheses";
        REDUNDANT_PARENTHESES.description = "Control flow constructs, exception handling constructs, and "
            + "values assigned in variable/constant declarations should not be enclosed in parentheses.";
        REDUNDANT_PARENTHESES.className = RedundantParenthesesListener.class.getName();

        TERMINATING_NEWLINE.name = "terminating-newline";
        TERMINATING_NEWLINE.description = "Verify that source files terminate with exactly one '\\n' character.";
        TERMINATING_NEWLINE.className = FileListener.class.getName();

        TERMINATING_SEMICOLON.name = "terminating-semicolon";
        TERMINATING_SEMICOLON.description = "Statements should not be terminated with semicolons.";
        TERMINATING_SEMICOLON.className = SemicolonTerminatedListener.class.getName();

        TODO_SYNTAX.name = "todo-syntax";
        TODO_SYNTAX.description = "TODO comments should follow either <TODO: description> or"
            + " <TODO(dev-name): description> format.";
        TODO_SYNTAX.className = TodoCommentListener.class.getName();

        TRAILING_WHITESPACE.name = "trailing-whitespace";
        TRAILING_WHITESPACE.description = "Flag whitespace after the last non-whitespace character on each line "
            + "until the newline.";
        TRAILING_WHITESPACE.className = FileListener.class.getName();

        UPPER_CAMEL_CASE.name = "upper-camel-case";
        UPPER_CAMEL_CASE.description = "Class, enum, enum value, struct, and protocol names should follow"
            + " UpperCamelCase naming convention.";
        UPPER_CAMEL_CASE.className = UpperCamelCaseListener.class.getName();

        WHITESPACE.name = "whitespace";
        WHITESPACE.description = "Flag whitespace violations around colon (:), arrow (->), and between construct"
            + " and opening brace ({).";
        WHITESPACE.className = WhitespaceListener.class.getName();
    }
}
