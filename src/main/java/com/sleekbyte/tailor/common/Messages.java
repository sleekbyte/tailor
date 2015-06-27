package com.sleekbyte.tailor.common;

/**
 * Output messages
 */
public class Messages {

    // Message styles
    public static final String WARNING = "warning";
    public static final String ERROR = "error";

    // Modifiers
    public static final String NAME = "name ";
    public static final String NAMES = "names ";

    // Singular constructs
    public static final String CLASS = "Class ";
    public static final String CLOSURE = "Closure ";
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

    // Plural constructs
    public static final String STATEMENTS = "Statements ";

    // Message descriptions
    public static final String UPPER_CAMEL_CASE = "should be in UpperCamelCase";
    public static final String SEMICOLON = "should not terminate with a semicolon";
    public static final String EXCEEDS_LINE_LIMIT = "is over maximum line limit";
    public static final String EXCEEDS_CHARACTER_LIMIT = "is over maximum character limit";

    // Usage messages
    public static final String CMD_LINE_SYNTAX = "tailor";
    public static final String HELP_DESC = "display help";
    public static final String MAXIMUM = "maximum ";
    public static final String LENGTH = "length";
    public static final String IN_LINES_DESC = " (in lines)";
    public static final String IN_CHARS_DESC = " (in characters)";
    public static final String MAX_CLASS_LENGTH_DESC = MAXIMUM + CLASS + LENGTH + IN_LINES_DESC;
    public static final String MAX_CLOSURE_LENGTH_DESC = MAXIMUM + CLOSURE + LENGTH + IN_LINES_DESC;
    public static final String MAX_FILE_LENGTH_DESC = MAXIMUM + FILE + LENGTH + IN_LINES_DESC;
    public static final String MAX_FUNCTION_LENGTH_DESC = MAXIMUM + FUNCTION + LENGTH + IN_LINES_DESC;
    public static final String MAX_LINE_LENGTH_DESC = MAXIMUM + LINE + LENGTH + IN_CHARS_DESC;
    public static final String MAX_NAME_LENGTH_DESC = MAXIMUM + IDENTIFIER_NAME + LENGTH + IN_CHARS_DESC;
    public static final String MAX_STRUCT_LENGTH_DESC = MAXIMUM + STRUCT + LENGTH + IN_CHARS_DESC;

}
