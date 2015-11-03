package com.sleekbyte.tailor.common;

/**
 * Output messages.
 */
public class Messages {

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
    public static final String EXTERNAL_PARAMETER = "External parameter ";
    public static final String FILE = "File ";
    public static final String FUNCTION = "Function ";
    public static final String LABEL = "Label ";
    public static final String LINE = "Line ";
    public static final String LOCAL_PARAMETER = "Local parameter ";
    public static final String PROTOCOL = "Protocol ";
    public static final String IDENTIFIER_NAME = "Identifier name ";
    public static final String SETTER = "Setter ";
    public static final String STRUCT = "Struct ";
    public static final String TYPE = "Type ";
    public static final String TYPEALIAS = "Typealias ";
    public static final String VARIABLE = "Variable ";
    public static final String CONDITIONAL_CLAUSE = "Conditional clause ";
    public static final String SWITCH = "Switch ";
    public static final String SWITCH_EXPRESSION = "Switch expression ";
    public static final String FOR = "For ";
    public static final String FOR_LOOP = "For loop ";
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

    // Plural constructs
    public static final String IMPORTS = "Imports ";
    public static final String STATEMENTS = "Statements ";

    // Message connector
    public static final String AT_COLUMN = "at column ";

    // Message descriptions
    public static final String UPPER_CAMEL_CASE = "should be UpperCamelCase";
    public static final String LOWER_CAMEL_CASE = "should be lowerCamelCase";
    public static final String SEMICOLON = "should not terminate with a semicolon";
    public static final String EXCEEDS_LINE_LIMIT = "is over maximum line limit";
    public static final String EXCEEDS_CHARACTER_LIMIT = "is over maximum character limit";
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
    public static final String TODO_SYNTAX = "should be formatted either as <TODO: description>"
        + " or <TODO(dev-name): description>";

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
    public static final String ONLY_SPECIFIC_RULES_DESC = "run only the specified rules";
    public static final String EXCEPT_RULES_DESC = "run all rules except the specified ones";
    public static final String XCODE_INTEGRATION_DESC = "add Tailor Build Phase Run Script to Xcode Project";
    public static final String DEBUG_DESC = "print ANTLR error messages when parsing error occurs";
    public static final String NO_COLOR_DESC = "disable colorized console output";
    public static final String INVERT_COLOR_DESC = "invert colorized console output";
    public static final String SHOW_RULES_DESC = "show description for each rule";
    public static final String CONFIG_FILE_DESC = "specify configuration file";
    public static final String LIST_FILES_DESC = "display Swift source files to be analyzed";

    // Runtime messages
    public static final String TAILOR_CONFIG_LOCATION = "Using Tailor configuration file at: ";
    public static final String FILES_TO_BE_ANALYZED = "Files to be analyzed:";

}
