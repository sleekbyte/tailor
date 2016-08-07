package com.sleekbyte.tailor.common;

import com.sleekbyte.tailor.listeners.BlankLineListener;
import com.sleekbyte.tailor.listeners.BraceStyleListener;
import com.sleekbyte.tailor.listeners.ConstantNamingListener;
import com.sleekbyte.tailor.listeners.FileListener;
import com.sleekbyte.tailor.listeners.ForceTypeCastListener;
import com.sleekbyte.tailor.listeners.KPrefixListener;
import com.sleekbyte.tailor.listeners.LowerCamelCaseListener;
import com.sleekbyte.tailor.listeners.MultipleImportListener;
import com.sleekbyte.tailor.listeners.RedundantParenthesesListener;
import com.sleekbyte.tailor.listeners.SemicolonTerminatedListener;
import com.sleekbyte.tailor.listeners.TodoCommentListener;
import com.sleekbyte.tailor.listeners.TrailingClosureListener;
import com.sleekbyte.tailor.listeners.UpperCamelCaseListener;
import com.sleekbyte.tailor.listeners.whitespace.AngleBracketWhitespaceListener;
import com.sleekbyte.tailor.listeners.whitespace.ArrowWhitespaceListener;
import com.sleekbyte.tailor.listeners.whitespace.ColonWhitespaceListener;
import com.sleekbyte.tailor.listeners.whitespace.CommaWhitespaceListener;
import com.sleekbyte.tailor.listeners.whitespace.CommentWhitespaceListener;
import com.sleekbyte.tailor.listeners.whitespace.OperatorWhitespaceListener;
import com.sleekbyte.tailor.listeners.whitespace.ParenthesisWhitespaceListener;

/**
 * Enum for all rules implemented in Tailor.
 */
public enum Rules {
    ARROW_WHITESPACE,
    ANGLE_BRACKET_WHITESPACE,
    BRACE_STYLE,
    COLON_WHITESPACE,
    COMMA_WHITESPACE,
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
    MIN_NAME_LENGTH,
    MULTIPLE_IMPORTS,
    OPERATOR_WHITESPACE,
    PARENTHESIS_WHITESPACE,
    REDUNDANT_PARENTHESES,
    TERMINATING_NEWLINE,
    TERMINATING_SEMICOLON,
    TODO_SYNTAX,
    TRAILING_CLOSURE,
    TRAILING_WHITESPACE,
    UPPER_CAMEL_CASE;

    private static final String BASE_STYLE_GUIDE_LINK = "https://github.com/sleekbyte/tailor/wiki/Rules#";
    public static final int REMEDIATION_POINTS = 50000;
    private String name;
    private RuleCategory category;
    private String className;
    private String description;
    private String examples;

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category.toString();
    }

    public String getClassName() {
        return this.className;
    }

    public String getDescription() {
        return this.description;
    }

    public String getExamples() {
        return this.examples;
    }

    public String getLink() {
        return BASE_STYLE_GUIDE_LINK + this.getName();
    }

    /**
     * Provide a rough estimate for how long it would take to resolve the reported issue.
     *
     * @return an integer indicating the effort involved to remedy the issue
     */
    public int getRemediationPoints() {
        return REMEDIATION_POINTS;
    }

    static {
        ARROW_WHITESPACE.name = "arrow-whitespace";
        ARROW_WHITESPACE.description = "Flags all return arrows (->) that are not space delimited.";
        ARROW_WHITESPACE.examples =  RuleExamples.get(ARROW_WHITESPACE.name);
        ARROW_WHITESPACE.className = ArrowWhitespaceListener.class.getName();
        ARROW_WHITESPACE.category = RuleCategory.STYLE;

        ANGLE_BRACKET_WHITESPACE.name = "angle-bracket-whitespace";
        ANGLE_BRACKET_WHITESPACE.description = "Ensure no whitespace is present immediately before/after an opening"
            + " chevron and before the closing chevron.";
        ANGLE_BRACKET_WHITESPACE.examples = RuleExamples.get(ANGLE_BRACKET_WHITESPACE.name);
        ANGLE_BRACKET_WHITESPACE.className = AngleBracketWhitespaceListener.class.getName();
        ANGLE_BRACKET_WHITESPACE.category = RuleCategory.STYLE;

        BRACE_STYLE.name = "brace-style";
        BRACE_STYLE.description = "Definitions of constructs should follow the One True Brace Style (1TBS).";
        BRACE_STYLE.examples = RuleExamples.get(BRACE_STYLE.name);
        BRACE_STYLE.className = BraceStyleListener.class.getName();
        BRACE_STYLE.category = RuleCategory.STYLE;

        COLON_WHITESPACE.name = "colon-whitespace";
        COLON_WHITESPACE.description = "Flag whitespace violations around colons (:).";
        COLON_WHITESPACE.examples = RuleExamples.get(COLON_WHITESPACE.name);
        COLON_WHITESPACE.className = ColonWhitespaceListener.class.getName();
        COLON_WHITESPACE.category = RuleCategory.STYLE;

        COMMA_WHITESPACE.name = "comma-whitespace";
        COMMA_WHITESPACE.description = "Flags all commas (,) that are not left associated.";
        COMMA_WHITESPACE.examples = RuleExamples.get(COMMA_WHITESPACE.name);
        COMMA_WHITESPACE.className = CommaWhitespaceListener.class.getName();
        COMMA_WHITESPACE.category = RuleCategory.STYLE;

        COMMENT_WHITESPACE.name = "comment-whitespace";
        COMMENT_WHITESPACE.description = "Ensure at least one whitespace character after a comment opening symbol"
            + " (// or /*) and at least one whitespace character before a comment closing symbol (*/).";
        COMMENT_WHITESPACE.examples = RuleExamples.get(COMMENT_WHITESPACE.name);
        COMMENT_WHITESPACE.className = CommentWhitespaceListener.class.getName();
        COMMENT_WHITESPACE.category = RuleCategory.STYLE;

        CONSTANT_K_PREFIX.name = "constant-k-prefix";
        CONSTANT_K_PREFIX.description = "Flag constants with prefix k.";
        CONSTANT_K_PREFIX.examples = RuleExamples.get(CONSTANT_K_PREFIX.name);
        CONSTANT_K_PREFIX.className = KPrefixListener.class.getName();
        CONSTANT_K_PREFIX.category = RuleCategory.STYLE;

        CONSTANT_NAMING.name = "constant-naming";
        CONSTANT_NAMING.description = "Global constants should follow either UpperCamelCase or lowerCamelCase naming "
            + "conventions. Local constants should follow lowerCamelCase naming conventions.";
        CONSTANT_NAMING.examples = RuleExamples.get(CONSTANT_NAMING.name);
        CONSTANT_NAMING.className = ConstantNamingListener.class.getName();
        CONSTANT_NAMING.category = RuleCategory.STYLE;

        FORCED_TYPE_CAST.name = "forced-type-cast";
        FORCED_TYPE_CAST.description = "Flag uses of the forced form of the type cast operator (as!).";
        FORCED_TYPE_CAST.examples = RuleExamples.get(FORCED_TYPE_CAST.name);
        FORCED_TYPE_CAST.className = ForceTypeCastListener.class.getName();
        FORCED_TYPE_CAST.category = RuleCategory.BUG_RISK;

        FUNCTION_WHITESPACE.name = "function-whitespace";
        FUNCTION_WHITESPACE.description = "Every function declaration except those at the start and end of file "
            + "should have one blank line before and after itself.";
        FUNCTION_WHITESPACE.examples = RuleExamples.get(FUNCTION_WHITESPACE.name);
        FUNCTION_WHITESPACE.className = BlankLineListener.class.getName();
        FUNCTION_WHITESPACE.category = RuleCategory.STYLE;

        LEADING_WHITESPACE.name = "leading-whitespace";
        LEADING_WHITESPACE.description = "Verify that source files begin with a non-whitespace character.";
        LEADING_WHITESPACE.examples = RuleExamples.get(LEADING_WHITESPACE.name);
        LEADING_WHITESPACE.className = FileListener.class.getName();
        LEADING_WHITESPACE.category = RuleCategory.STYLE;

        LOWER_CAMEL_CASE.name = "lower-camel-case";
        LOWER_CAMEL_CASE.description = "Method and variable names should follow lowerCamelCase naming convention.";
        LOWER_CAMEL_CASE.examples = RuleExamples.get(LOWER_CAMEL_CASE.name);
        LOWER_CAMEL_CASE.className = LowerCamelCaseListener.class.getName();
        LOWER_CAMEL_CASE.category = RuleCategory.STYLE;

        MAX_CLASS_LENGTH.name = Messages.MAX_CLASS_LENGTH_OPT;
        MAX_CLASS_LENGTH.description = "Enforce a line limit on the lengths of class bodies.";
        MAX_CLASS_LENGTH.examples = RuleExamples.get(MAX_CLASS_LENGTH.name);
        MAX_CLASS_LENGTH.className = FileListener.class.getName();
        MAX_CLASS_LENGTH.category = RuleCategory.COMPLEXITY;

        MAX_CLOSURE_LENGTH.name = Messages.MAX_CLOSURE_LENGTH_OPT;
        MAX_CLOSURE_LENGTH.description = "Enforce a line limit on the lengths of closure bodies.";
        MAX_CLOSURE_LENGTH.examples = RuleExamples.get(MAX_CLOSURE_LENGTH.name);
        MAX_CLOSURE_LENGTH.className = FileListener.class.getName();
        MAX_CLOSURE_LENGTH.category = RuleCategory.COMPLEXITY;

        MAX_FILE_LENGTH.name = Messages.MAX_FILE_LENGTH_OPT;
        MAX_FILE_LENGTH.description = "Enforce a line limit on each file.";
        MAX_FILE_LENGTH.examples = RuleExamples.get(MAX_FILE_LENGTH.name);
        MAX_FILE_LENGTH.className = FileListener.class.getName();
        MAX_FILE_LENGTH.category = RuleCategory.COMPLEXITY;

        MAX_FUNCTION_LENGTH.name = Messages.MAX_FUNCTION_LENGTH_OPT;
        MAX_FUNCTION_LENGTH.description = "Enforce a line limit on the lengths of function bodies.";
        MAX_FUNCTION_LENGTH.examples = RuleExamples.get(MAX_FUNCTION_LENGTH.name);
        MAX_FUNCTION_LENGTH.className = FileListener.class.getName();
        MAX_FUNCTION_LENGTH.category = RuleCategory.COMPLEXITY;

        MAX_LINE_LENGTH.name = Messages.MAX_LINE_LENGTH_LONG_OPT;
        MAX_LINE_LENGTH.description = "Enforce a character limit on the length of each line.";
        MAX_LINE_LENGTH.examples = RuleExamples.get(MAX_LINE_LENGTH.name);
        MAX_LINE_LENGTH.className = FileListener.class.getName();
        MAX_LINE_LENGTH.category = RuleCategory.COMPLEXITY;

        MAX_NAME_LENGTH.name = Messages.MAX_NAME_LENGTH_OPT;
        MAX_NAME_LENGTH.description = "Enforce a character limit on the length of each construct name.";
        MAX_NAME_LENGTH.examples = RuleExamples.get(MAX_NAME_LENGTH.name);
        MAX_NAME_LENGTH.className = FileListener.class.getName();
        MAX_NAME_LENGTH.category = RuleCategory.STYLE;

        MAX_STRUCT_LENGTH.name = Messages.MAX_STRUCT_LENGTH_OPT;
        MAX_STRUCT_LENGTH.description = "Enforce a line limit on the lengths of struct bodies.";
        MAX_STRUCT_LENGTH.examples = RuleExamples.get(MAX_STRUCT_LENGTH.name);
        MAX_STRUCT_LENGTH.className = FileListener.class.getName();
        MAX_STRUCT_LENGTH.category = RuleCategory.COMPLEXITY;

        MIN_NAME_LENGTH.name = Messages.MIN_NAME_LENGTH_OPT;
        MIN_NAME_LENGTH.description = "Enforce a minimum character limit on the length of each construct name.";
        MIN_NAME_LENGTH.examples = RuleExamples.get(MIN_NAME_LENGTH.name);
        MIN_NAME_LENGTH.className = FileListener.class.getName();
        MIN_NAME_LENGTH.category = RuleCategory.STYLE;

        MULTIPLE_IMPORTS.name = "multiple-imports";
        MULTIPLE_IMPORTS.description = "Multiple import statements should not be defined on a single line.";
        MULTIPLE_IMPORTS.examples = RuleExamples.get(MULTIPLE_IMPORTS.name);
        MULTIPLE_IMPORTS.className = MultipleImportListener.class.getName();
        MULTIPLE_IMPORTS.category = RuleCategory.STYLE;

        OPERATOR_WHITESPACE.name = "operator-whitespace";
        OPERATOR_WHITESPACE.description = "Flags operators that are not space delimited in operator declarations.";
        OPERATOR_WHITESPACE.examples = RuleExamples.get(OPERATOR_WHITESPACE.name);
        OPERATOR_WHITESPACE.className = OperatorWhitespaceListener.class.getName();
        OPERATOR_WHITESPACE.category = RuleCategory.STYLE;

        PARENTHESIS_WHITESPACE.name = "parenthesis-whitespace";
        PARENTHESIS_WHITESPACE.description = "Ensure no whitespace is present immediately before/after an opening"
            + " parenthesis and before the closing parenthesis.";
        PARENTHESIS_WHITESPACE.examples = RuleExamples.get(PARENTHESIS_WHITESPACE.name);
        PARENTHESIS_WHITESPACE.className = ParenthesisWhitespaceListener.class.getName();
        PARENTHESIS_WHITESPACE.category = RuleCategory.STYLE;

        REDUNDANT_PARENTHESES.name = "redundant-parentheses";
        REDUNDANT_PARENTHESES.description = "Control flow constructs, exception handling constructs, and "
            + "values assigned in variable/constant declarations should not be enclosed in parentheses.";
        REDUNDANT_PARENTHESES.examples = RuleExamples.get(REDUNDANT_PARENTHESES.name);
        REDUNDANT_PARENTHESES.className = RedundantParenthesesListener.class.getName();
        REDUNDANT_PARENTHESES.category = RuleCategory.STYLE;

        TERMINATING_NEWLINE.name = "terminating-newline";
        TERMINATING_NEWLINE.description = "Verify that source files terminate with exactly one '\\n' character.";
        TERMINATING_NEWLINE.examples = RuleExamples.get(TERMINATING_NEWLINE.name);
        TERMINATING_NEWLINE.className = FileListener.class.getName();
        TERMINATING_NEWLINE.category = RuleCategory.STYLE;

        TERMINATING_SEMICOLON.name = "terminating-semicolon";
        TERMINATING_SEMICOLON.description = "Statements should not be terminated with semicolons.";
        TERMINATING_SEMICOLON.examples = RuleExamples.get(TERMINATING_SEMICOLON.name);
        TERMINATING_SEMICOLON.className = SemicolonTerminatedListener.class.getName();
        TERMINATING_SEMICOLON.category = RuleCategory.STYLE;

        TODO_SYNTAX.name = "todo-syntax";
        TODO_SYNTAX.description = "TODO comments should follow either <TODO: description> or"
            + " <TODO(dev-name): description> format.";
        TODO_SYNTAX.examples = RuleExamples.get(TODO_SYNTAX.name);
        TODO_SYNTAX.className = TodoCommentListener.class.getName();
        TODO_SYNTAX.category = RuleCategory.STYLE;

        TRAILING_CLOSURE.name = "trailing-closure";
        TRAILING_CLOSURE.description = "Functions that have a closure as their last argument should be called"
            + "using trailing closure syntax.";
        TRAILING_CLOSURE.examples = RuleExamples.get(TRAILING_CLOSURE.name);
        TRAILING_CLOSURE.className = TrailingClosureListener.class.getName();
        TRAILING_CLOSURE.category = RuleCategory.STYLE;

        TRAILING_WHITESPACE.name = "trailing-whitespace";
        TRAILING_WHITESPACE.description = "Flag whitespace after the last non-whitespace character on each line "
            + "until the newline.";
        TRAILING_WHITESPACE.examples = RuleExamples.get(TRAILING_WHITESPACE.name);
        TRAILING_WHITESPACE.className = FileListener.class.getName();
        TRAILING_WHITESPACE.category = RuleCategory.STYLE;

        UPPER_CAMEL_CASE.name = "upper-camel-case";
        UPPER_CAMEL_CASE.description = "Class, enum, enum value, struct, and protocol names should follow"
            + " UpperCamelCase naming convention.";
        UPPER_CAMEL_CASE.examples = RuleExamples.get(UPPER_CAMEL_CASE.name);
        UPPER_CAMEL_CASE.className = UpperCamelCaseListener.class.getName();
        UPPER_CAMEL_CASE.category = RuleCategory.STYLE;
    }
}
