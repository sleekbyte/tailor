package com.sleekbyte.tailor.common;

/**
 * Output messages.
 */
public abstract class Messages {

    // Message styles
    public static final String WARNING = "warning";
    public static final String ERROR = "error";

    // Modifiers
    public static final String GLOBAL = "Global ";
    public static final String NAME = "name ";
    public static final String NAMES = "names ";

    // Singular constructs
    public static final String CLASS = "Class ";
    public static final String CLOSURE = "Closure ";
    public static final String CONSTANT = "Constant ";
    public static final String ELEMENT = "Element ";
    public static final String ENUM = "Enum ";
    public static final String ENUM_CASE = "Enum case ";
    public static final String FILE = "File ";
    public static final String FUNCTION = "Function ";
    public static final String LABEL = "Label ";
    public static final String LINE = "Line ";
    public static final String PROTOCOL = "Protocol ";
    public static final String IDENTIFIER_NAME = "Identifier name ";
    public static final String SETTER = "Setter ";
    public static final String STRUCT = "Struct ";
    public static final String TYPE = "Type ";
    public static final String TYPEALIAS = "Typealias ";
    public static final String VARIABLE = "Variable ";
    public static final String CONDITION = "Condition ";
    public static final String SWITCH_EXPRESSION = "Switch expression ";
    public static final String THROW_STATEMENT = "Throw statement ";
    public static final String CATCH_CLAUSE = "Catch clause ";
    public static final String INITIALIZER_EXPRESSION = "Initializer expression ";
    public static final String ARRAY_LITERAL = "Array literal ";
    public static final String DICTIONARY_LITERAL = "Dictionary literal ";
    public static final String IF_STATEMENT = "If statement ";
    public static final String ELSE_CLAUSE = "Else clause ";
    public static final String SWITCH_STATEMENT = "Switch statement ";
    public static final String WHILE_STATEMENT = "While statement ";
    public static final String REPEAT_WHILE_STATEMENT = "Repeat-While statement ";
    public static final String INITIALIZER_BODY = "Initializer body ";
    public static final String FOR_IN_LOOP = "For-in loop ";
    public static final String OPERATOR = "Operator ";
    public static final String COLON = "Colon ";
    public static final String EXTENSION = "Extension ";
    public static final String GETTER = "Getter ";
    public static final String OPEN_BRACE = "Opening brace ";
    public static final String SINGLE_LINE_COMMENT = "Single-line comment ";
    public static final String MULTILINE_COMMENT = "Multiline comment ";
    public static final String RETURN_ARROW = "Return arrow ";
    public static final String SUBSCRIPT = "Subscript ";
    public static final String GETTER_SETTER_BLOCK = "Getter/Setter block ";
    public static final String WILL_SET_CLAUSE = "WillSet clause ";
    public static final String DID_SET_CLAUSE = "DidSet clause ";
    public static final String WILLSET_DIDSET_BLOCK = "WillSet/DidSet block ";
    public static final String TODOS = "TODO comments ";
    public static final String GENERIC_PARAMETERS = "Generic parameters ";
    public static final String COMMA = "Comma ";
    public static final String EMPTY_PARENTHESES = "Empty parentheses ";
    public static final String EMPTY = "Empty ";
    public static final String PARENTHESES = "Parentheses ";
    public static final String CHEVRONS = "Chevrons ";

    // Plural constructs
    public static final String IMPORTS = "Imports ";
    public static final String STATEMENTS = "Statements ";

    // Message connector
    public static final String AT_COLUMN = "at column ";
    public static final String CONTENT = "content ";

    // Message descriptions
    public static final String UPPER_CAMEL_CASE = "should be UpperCamelCase";
    public static final String LOWER_CAMEL_CASE = "should be lowerCamelCase";
    public static final String SEMICOLON = "should not terminate with a semicolon";
    public static final String EXCEEDS_LINE_LIMIT = "is over maximum line limit";
    public static final String EXCEEDS_CHARACTER_LIMIT = "is over maximum character limit";
    public static final String VIOLATES_MINIMUM_CHARACTER_LIMIT = "is less than minimum character limit";
    public static final String GLOBAL_CONSTANT_NAMING = "should be either lowerCamelCase or UpperCamelCase";
    public static final String NEWLINE_TERMINATOR = "should terminate with exactly one newline character ('\\n')";
    public static final String ENCLOSED_PARENTHESES = "should not be enclosed within parentheses";
    public static final String K_PREFIXED = "should not be prefixed with 'k' or 'K'";
    public static final String MULTIPLE_IMPORTS = "should be on separate lines";
    public static final String OPEN_BRACE_STYLE = "should not have any line breaks before the opening brace";
    public static final String CLOSE_BRACE_STYLE = "closing brace should be isolated on a separate line";
    public static final String EMPTY_BODY = "Empty construct body should not contain any whitespace";
    public static final String SPACE_BEFORE = "should have exactly one space before it";
    public static final String SPACE_AFTER = "should have exactly one space after it";
    public static final String NO_SPACE_BEFORE = "should have no spaces before it";
    public static final String FORCE_CAST = "Force casts should be avoided";
    public static final String BLANK_LINE_BEFORE = "should have at least one blank line before it";
    public static final String BLANK_LINE_AFTER = "should have at least one blank line after it";
    public static final String LEADING_WHITESPACE = "should not start with whitespace";
    public static final String TRAILING_WHITESPACE = "should not have any trailing whitespace";
    public static final String START_SPACE = "should start with whitespace";
    public static final String END_SPACE = "should end with whitespace";
    public static final String NOT_END_SPACE = "should not end with whitespace";
    public static final String ILLEGAL_WHITESPACE = "should not contain whitespace";
    public static final String TODO_SYNTAX = "should be formatted either as <TODO: description>"
        + " or <TODO(dev-name): description>";
    public static final String REDUNDANT_METHOD_PARENTHESES = "following method call with trailing closure argument"
        + " should be removed";
    public static final String NO_WHITESPACE_BEFORE = "should not be preceded by whitespace";
    public static final String OPERATOR_OVERLOADING_ONE_SPACE = "Operator definitions should be followed by exactly "
        + "one space";
    public static final String CLOSURE_PARENTHESES_ONE_SPACE = "Parameter clause in closure signature should be"
        + " preceded by exactly one space";
    public static final String REDUNDANT = "Redundant ";
    public static final String REDUNDANT_OPTIONAL_BINDING = " in optional binding should be removed";
    public static final String TRAILING_CLOSURE = "is the function's final argument and may be passed as a trailing "
        + "closure instead";
    public static final String TAILOR = "[tailor] ";


    // Usage messages
    public static final String CMD_LINE_SYNTAX = "tailor [options] [--] [[file|directory] ...]";
    public static final String TAILOR_DESC = "Perform static analysis on Swift source files.";
    public static final String TAILOR_ARGS_INFO =
        "Invoking Tailor with at least one file or directory will analyze all Swift files at those paths."
        + " If no paths are provided, Tailor will analyze all Swift files found in '$SRCROOT' (if defined),"
        + " which is set by Xcode when run in a Build Phase. Tailor may be set up as an Xcode Build Phase automatically"
        + " with the --xcode option.";
    public static final String HELP_DESC = "display help";
    public static final String VERSION_DESC = "display version";
    public static final String MAXIMUM = "maximum ";
    public static final String MINIMUM = "minimum ";
    public static final String LENGTH = "length";
    public static final String SEVERITY = "severity ";
    public static final String IN_LINES_DESC = " (in lines)";
    public static final String IN_CHARS_DESC = " (in characters)";
    public static final String MAX_CLASS_LENGTH_DESC = MAXIMUM + CLASS + LENGTH + IN_LINES_DESC;
    public static final String MAX_CLOSURE_LENGTH_DESC = MAXIMUM + CLOSURE + LENGTH + IN_LINES_DESC;
    public static final String MAX_FILE_LENGTH_DESC = MAXIMUM + FILE + LENGTH + IN_LINES_DESC;
    public static final String MAX_FUNCTION_LENGTH_DESC = MAXIMUM + FUNCTION + LENGTH + IN_LINES_DESC;
    public static final String MAX_LINE_LENGTH_DESC = MAXIMUM + LINE + LENGTH + IN_CHARS_DESC;
    public static final String MAX_NAME_LENGTH_DESC = MAXIMUM + IDENTIFIER_NAME + LENGTH + IN_CHARS_DESC;
    public static final String MAX_STRUCT_LENGTH_DESC = MAXIMUM + STRUCT + LENGTH + IN_LINES_DESC;
    public static final String MAX_SEVERITY_DESC = MAXIMUM + SEVERITY;
    public static final String MIN_NAME_LENGTH_DESC = MINIMUM + IDENTIFIER_NAME + LENGTH + IN_CHARS_DESC;
    public static final String ONLY_SPECIFIC_RULES_DESC = "run only the specified rules";
    public static final String EXCEPT_RULES_DESC = "run all rules except the specified ones";
    public static final String XCODE_INTEGRATION_DESC = "add Tailor Build Phase Run Script to Xcode Project";
    public static final String DEBUG_DESC = "print ANTLR error messages when parsing error occurs";
    public static final String NO_COLOR_DESC = "disable colorized console output";
    public static final String INVERT_COLOR_DESC = "invert colorized console output";
    public static final String SHOW_RULES_DESC = "show description for each rule";
    public static final String CONFIG_FILE_DESC = "specify configuration file";
    public static final String LIST_FILES_DESC = "display Swift source files to be analyzed";
    public static final String FORMAT_DESC = "select an output format";
    public static final String PURGE_DESC = "reduce memory usage by clearing DFA cache after specified number "
        + "of files are parsed";

    // Runtime messages
    public static final String TAILOR_CONFIG_LOCATION = "Using Tailor configuration file at: ";
    public static final String FILES_TO_BE_ANALYZED = "Files to be analyzed:";

    // JSON Format messages
    public static final String LOCATION_KEY = "location";
    public static final String LINE_KEY = "line";
    public static final String COLUMN_KEY = "column";
    public static final String SEVERITY_KEY = "severity";
    public static final String RULE_KEY = "rule";
    public static final String MESSAGE_KEY = "message";
    public static final String FILES_KEY = "files";
    public static final String PATH_KEY = "path";
    public static final String VIOLATIONS_KEY = "violations";
    public static final String PARSED_KEY = "parsed";
    public static final String SUMMARY_KEY = "summary";
    public static final String ANALYZED_KEY = "analyzed";
    public static final String SKIPPED_KEY = "skipped";
    public static final String ERRORS_KEY = "errors";
    public static final String WARNINGS_KEY = "warnings";

    // Code Climate Format messages
    public static final String BEGIN_KEY = "begin";
    public static final String END_KEY = "end";
    public static final String POSITIONS_KEY = "positions";
    public static final String LINES_KEY = "lines";
    public static final String CHECK_NAME_KEY = "check_name";
    public static final String BODY_KEY = "body";
    public static final String CONTENT_KEY = "content";
    public static final String DESCRIPTION_KEY = "description";
    public static final String TYPE_KEY = "type";
    public static final String ISSUE_VALUE = "issue";
    public static final String CATEGORIES_KEY = "categories";
    public static final String REMEDIATION_POINTS_KEY = "remediation_points";
    public static final String STYLE_CATEGORY = "Style";
    public static final String BUG_RISK_CATEGORY = "Bug Risk";

    // HTML Format messages
    public static final String SINGLE_VIOLATION_KEY = "violation";
    public static final String MULTI_VIOLATIONS_KEY = "violations";
    public static final String NUM_VIOLATIONS_KEY = "num_violations";

    // Formatter messages
    public static final String FILE_KEY = "file";

    // Error messages
    public static final String NO_SWIFT_FILES_FOUND = "No Swift source files were found.";
    public static final String COULD_NOT_BE_PARSED = " could not be parsed successfully, skipping...";

    // Tailor disable feature warnings
    public static final String ON_OFF_MISMATCH = "Tailor block disable comments are unbalanced";

    // CLI options
    public static final String SYNTAX_PREFIX = "Usage: ";
    public static final String OPTIONS_PREFIX = "Options:";
    public static final int HELP_WIDTH = 99;
    public static final String HELP_SHORT_OPT = "h";
    public static final String HELP_LONG_OPT = "help";
    public static final String VERSION_SHORT_OPT = "v";
    public static final String VERSION_LONG_OPT = "version";
    public static final String MAX_CLASS_LENGTH_OPT = "max-class-length";
    public static final String MAX_CLOSURE_LENGTH_OPT = "max-closure-length";
    public static final String MAX_FILE_LENGTH_OPT = "max-file-length";
    public static final String MAX_FUNCTION_LENGTH_OPT = "max-function-length";
    public static final String MAX_LINE_LENGTH_SHORT_OPT = "l";
    public static final String MAX_LINE_LENGTH_LONG_OPT = "max-line-length";
    public static final String MAX_NAME_LENGTH_OPT = "max-name-length";
    public static final String MAX_STRUCT_LENGTH_OPT = "max-struct-length";
    public static final String MIN_NAME_LENGTH_OPT = "min-name-length";
    public static final String MAX_SEVERITY_OPT = "max-severity";
    public static final String ONLY_OPT = "only";
    public static final String EXCEPT_OPT = "except";
    public static final String DEBUG_OPT = "debug";
    public static final String NO_COLOR_OPT = "no-color";
    public static final String INVERT_COLOR_OPT = "invert-color";
    public static final String DEFAULT_INT_ARG = "0";
    public static final String XCODE_INTEGRATION_OPT = "xcode";
    public static final String SHOW_RULES_OPT = "show-rules";
    public static final String CONFIG_SHORT_OPT = "c";
    public static final String CONFIG_LONG_OPT = "config";
    public static final String LIST_FILES_OPT = "list-files";
    public static final String FORMAT_SHORT_OPT = "f";
    public static final String FORMAT_LONG_OPT = "format";
    public static final String PURGE_OPT = "purge";
    public static final String INVALID_OPTION_VALUE = "Invalid value provided for option ";

    // Config options
    public static final String INVERT = "invert";
    public static final String DISABLE = "disable";

}
