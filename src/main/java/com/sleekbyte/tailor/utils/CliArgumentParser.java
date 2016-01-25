package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.ConstructLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.format.Format;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Parse command line options and arguments.
 */
public class CliArgumentParser {

    private static final String SYNTAX_PREFIX = "Usage: ";
    private static final String OPTIONS_PREFIX = "Options:";
    private static final int HELP_WIDTH = 99;
    private static final String HELP_SHORT_OPT = "h";
    private static final String HELP_LONG_OPT = "help";
    private static final String VERSION_SHORT_OPT = "v";
    private static final String VERSION_LONG_OPT = "version";

    public static final String MAX_CLASS_LENGTH_OPT = "max-class-length";
    public static final String MAX_CLOSURE_LENGTH_OPT = "max-closure-length";
    public static final String MAX_FILE_LENGTH_OPT = "max-file-length";
    public static final String MAX_FUNCTION_LENGTH_OPT = "max-function-length";
    private static final String MAX_LINE_LENGTH_SHORT_OPT = "l";
    public static final String MAX_LINE_LENGTH_LONG_OPT = "max-line-length";
    public static final String MAX_NAME_LENGTH_OPT = "max-name-length";
    public static final String MAX_STRUCT_LENGTH_OPT = "max-struct-length";

    public static final String MIN_NAME_LENGTH_OPT = "min-name-length";

    private static final String MAX_SEVERITY_OPT = "max-severity";
    private static final String ONLY_OPT = "only";
    private static final String EXCEPT_OPT = "except";
    private static final String DEBUG_OPT = "debug";
    private static final String NO_COLOR_OPT = "no-color";
    private static final String INVERT_COLOR_OPT = "invert-color";
    private static final String DEFAULT_INT_ARG = "0";
    private static final String XCODE_INTEGRATION_OPT = "xcode";
    private static final String SHOW_RULES_OPT = "show-rules";
    private static final String CONFIG_SHORT_OPT = "c";
    private static final String CONFIG_LONG_OPT = "config";
    private static final String LIST_FILES_OPT = "list-files";
    private static final String FORMAT_SHORT_OPT = "f";
    private static final String FORMAT_LONG_OPT = "format";
    public static final String INVALID_OPTION_VALUE = "Invalid value provided for option ";

    private Options options;
    private CommandLine cmd;

    /**
     * Parse command line options/flags and arguments.
     */
    public CommandLine parseCommandLine(String[] args) throws ParseException {
        addOptions();
        cmd = new DefaultParser().parse(this.options, args);
        return cmd;
    }

    /**
     * Check if "-h" or "--help" option was specified.
     */
    public boolean shouldPrintHelp() {
        return cmd != null && cmd.hasOption(HELP_SHORT_OPT);
    }

    /**
     * Check if "-v" or "--version" option was specified.
     */
    public boolean shouldPrintVersion() {
        return cmd != null && cmd.hasOption(VERSION_SHORT_OPT);
    }

    /**
     * Check if "--show-rules" option was specified.
     */
    public boolean shouldPrintRules() {
        return cmd != null && cmd.hasOption(SHOW_RULES_OPT);
    }

    /**
     * Check if "--list-files" option was specified.
     */
    public boolean shouldListFiles() {
        return cmd != null && cmd.hasOption(LIST_FILES_OPT);
    }

    /**
     * Print usage message with flag descriptions to STDOUT.
     */
    public void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setSyntaxPrefix(SYNTAX_PREFIX);
        String newLine = helpFormatter.getNewLine();
        String header = newLine + Messages.TAILOR_DESC + newLine + newLine + Messages.TAILOR_ARGS_INFO + newLine
            + newLine + OPTIONS_PREFIX;
        helpFormatter.setLongOptSeparator("=");
        helpFormatter.printHelp(HELP_WIDTH, Messages.CMD_LINE_SYNTAX, header, this.options, "");
    }

    /**
     * Parse construct length flags into ConstructLengths object.
     */
    public ConstructLengths parseConstructLengths() throws CliArgumentParserException {
        ConstructLengths constructLengths = new ConstructLengths();

        constructLengths.setMaxClassLength(getIntegerArgument(MAX_CLASS_LENGTH_OPT));
        constructLengths.setMaxClosureLength(getIntegerArgument(MAX_CLOSURE_LENGTH_OPT));
        constructLengths.setMaxFileLength(getIntegerArgument(MAX_FILE_LENGTH_OPT));
        constructLengths.setMaxFunctionLength(getIntegerArgument(MAX_FUNCTION_LENGTH_OPT));
        constructLengths.setMaxLineLength(getIntegerArgument(MAX_LINE_LENGTH_LONG_OPT));
        constructLengths.setMaxNameLength(getIntegerArgument(MAX_NAME_LENGTH_OPT));
        constructLengths.setMaxStructLength(getIntegerArgument(MAX_STRUCT_LENGTH_OPT));

        constructLengths.setMinNameLength(getIntegerArgument(MIN_NAME_LENGTH_OPT));

        return constructLengths;
    }

    private void addOptions() {
        options = new Options();

        options.addOption(createNoArgOpt(HELP_SHORT_OPT, HELP_LONG_OPT, Messages.HELP_DESC));
        options.addOption(createNoArgOpt(VERSION_SHORT_OPT, VERSION_LONG_OPT, Messages.VERSION_DESC));

        String argName = "0-999";
        options.addOption(createSingleArgOpt(MAX_CLASS_LENGTH_OPT, argName, Messages.MAX_CLASS_LENGTH_DESC));
        options.addOption(createSingleArgOpt(MAX_CLOSURE_LENGTH_OPT, argName, Messages.MAX_CLOSURE_LENGTH_DESC));
        options.addOption(createSingleArgOpt(MAX_FILE_LENGTH_OPT, argName, Messages.MAX_FILE_LENGTH_DESC));
        options.addOption(createSingleArgOpt(MAX_FUNCTION_LENGTH_OPT, argName, Messages.MAX_FUNCTION_LENGTH_DESC));
        options.addOption(createSingleArgOpt(
            MAX_LINE_LENGTH_SHORT_OPT, MAX_LINE_LENGTH_LONG_OPT, argName, Messages.MAX_LINE_LENGTH_DESC));
        options.addOption(createSingleArgOpt(MAX_NAME_LENGTH_OPT, argName, Messages.MAX_NAME_LENGTH_DESC));
        options.addOption(createSingleArgOpt(MAX_STRUCT_LENGTH_OPT, argName, Messages.MAX_STRUCT_LENGTH_DESC));

        argName = "1-999";
        options.addOption(createSingleArgOpt(MIN_NAME_LENGTH_OPT, argName, Messages.MIN_NAME_LENGTH_DESC));


        argName = "error|warning (default)";
        options.addOption(createSingleArgOpt(MAX_SEVERITY_OPT, argName, Messages.MAX_SEVERITY_DESC));

        argName = "rule1,rule2,...";
        options.addOption(createMultiArgOpt(ONLY_OPT, argName, Messages.ONLY_SPECIFIC_RULES_DESC));
        options.addOption(createMultiArgOpt(EXCEPT_OPT, argName, Messages.EXCEPT_RULES_DESC));

        argName = "path/to/project.xcodeproj";
        options.addOption(createSingleArgOpt(XCODE_INTEGRATION_OPT, argName, Messages.XCODE_INTEGRATION_DESC));

        options.addOption(createNoArgOpt(DEBUG_OPT, Messages.DEBUG_DESC));

        options.addOption(createNoArgOpt(NO_COLOR_OPT, Messages.NO_COLOR_DESC));
        options.addOption(createNoArgOpt(INVERT_COLOR_OPT, Messages.INVERT_COLOR_DESC));

        options.addOption(createNoArgOpt(SHOW_RULES_OPT, Messages.SHOW_RULES_DESC));

        argName = "path/to/.tailor.yml";
        options.addOption(createSingleArgOpt(CONFIG_SHORT_OPT, CONFIG_LONG_OPT, argName, Messages.CONFIG_FILE_DESC));

        options.addOption(createNoArgOpt(LIST_FILES_OPT, Messages.LIST_FILES_DESC));

        argName = Format.getFormats();
        options.addOption(createSingleArgOpt(FORMAT_SHORT_OPT, FORMAT_LONG_OPT, argName, Messages.FORMAT_DESC));
    }

    /**
     * Create command line option with short name, long name, and no argument.
     *
     * @param shortOpt short version of option
     * @param longOpt  long version of option
     * @param desc     description of option
     */
    private Option createNoArgOpt(String shortOpt, String longOpt, String desc) {
        return Option.builder(shortOpt).longOpt(longOpt).desc(desc).build();
    }

    /**
     * Create command line option with only long name and no argument.
     *
     * @param longOpt long version of option
     * @param desc    description of option
     */
    private Option createNoArgOpt(String longOpt, String desc) {
        return Option.builder().longOpt(longOpt).desc(desc).build();
    }

    /**
     * Create command line option with short name, long name, and only one argument.
     *
     * @param shortOpt short version of option
     * @param longOpt  long version of option
     * @param argName  name of argument for help message
     * @param desc     description of option
     */
    private Option createSingleArgOpt(String shortOpt, String longOpt, String argName, String desc) {
        return Option.builder(shortOpt).longOpt(longOpt).hasArg().argName(argName).desc(desc).build();
    }

    /**
     * Create command line option with only long name and only one argument.
     *
     * @param longOpt long version of option
     * @param argName name of argument for help message
     * @param desc    description of option
     */
    private Option createSingleArgOpt(String longOpt, String argName, String desc) {
        return Option.builder().longOpt(longOpt).hasArg().argName(argName).desc(desc).build();
    }

    /**
     * Create command line option with only long name and multiple arguments.
     * Multiple arguments can be separated by comma or by space.
     *
     * @param longOpt long version of option
     * @param argName name of argument for help message
     * @param desc    description of option
     */
    private Option createMultiArgOpt(String longOpt, String argName, String desc) {
        return Option.builder().longOpt(longOpt).hasArgs().argName(argName).valueSeparator(',').desc(desc).build();
    }

    private int getIntegerArgument(String opt) throws CliArgumentParserException {
        try {
            return Integer.parseInt(this.cmd.getOptionValue(opt, DEFAULT_INT_ARG));
        } catch (NumberFormatException e) {
            throw new CliArgumentParserException("Invalid value provided for integer argument " + opt + ".");
        }
    }

    /**
     * Collects all rules enabled by default and then filters out rules according to command line options.
     *
     * @return list of enabled rules after filtering
     * @throws CliArgumentParserException if rule names specified in command line options are not valid
     */
    public Set<Rules> getEnabledRules() throws CliArgumentParserException {
        Set<Rules> enabledRules = new HashSet<>(Arrays.asList(Rules.values()));
        Set<String> enabledRuleNames = enabledRules.stream().map(Rules::getName).collect(Collectors.toSet());

        // ONLY_OPT before EXCEPT_OPT gives precedence to ONLY_OPT if both are specified on the command line
        if (this.cmd.hasOption(ONLY_OPT)) {
            Set<String> onlySpecificRules = new HashSet<>(Arrays.asList(this.cmd.getOptionValues(ONLY_OPT)));
            checkValidRules(enabledRuleNames, onlySpecificRules);

            return enabledRules.stream()
                .filter(rule -> onlySpecificRules.contains(rule.getName())).collect(Collectors.toSet());
        } else if (this.cmd.hasOption(EXCEPT_OPT)) {
            Set<String> excludedRules = new HashSet<>(Arrays.asList(this.cmd.getOptionValues(EXCEPT_OPT)));
            checkValidRules(enabledRuleNames, excludedRules);

            return enabledRules.stream()
                .filter(rule -> !excludedRules.contains(rule.getName())).collect(Collectors.toSet());
        }

        return enabledRules;
    }

    /**
     * Checks if rules specified in command line option is valid.
     *
     * @param enabledRules   all valid rule names
     * @param specifiedRules rule names specified from command line
     * @throws CliArgumentParserException if rule name specified in command line is not valid
     */
    private void checkValidRules(Set<String> enabledRules, Set<String> specifiedRules)
        throws CliArgumentParserException {
        if (!enabledRules.containsAll(specifiedRules)) {
            specifiedRules.removeAll(enabledRules);
            throw new CliArgumentParserException("The following rules were not recognized: " + specifiedRules);
        }
    }

    /*
     * Retrieve Xcode project path specified for --xcode.
     *
     * @return path of Xcode project
     */
    public String getXcodeprojPath() {
        return cmd != null ? cmd.getOptionValue(XCODE_INTEGRATION_OPT) : null;
    }

    /*
     * Retrieve .tailor.yml config file path specified for --config.
     *
     * @return path of config file
     */
    public Optional<String> getConfigFilePath() {
        return cmd != null ? Optional.ofNullable(cmd.getOptionValue(CONFIG_LONG_OPT)) : Optional.empty();
    }

    /**
     * Returns maximum severity configured by user or 'warning' if not specified.
     *
     * @return Maximum severity
     * @throws CliArgumentParserException if invalid value specified for --max-severity
     */
    public Severity getMaxSeverity() throws CliArgumentParserException {
        try {
            return Severity.parseSeverity(this.cmd.getOptionValue(MAX_SEVERITY_OPT, Messages.WARNING));
        } catch (Severity.IllegalSeverityException ex) {
            throw new CliArgumentParserException(INVALID_OPTION_VALUE + MAX_SEVERITY_OPT + ".");
        }
    }

    public boolean debugFlagSet() throws CliArgumentParserException {
        return cmd != null && cmd.hasOption(DEBUG_OPT);
    }

    public boolean shouldColorOutput() {
        return cmd != null && !cmd.hasOption(NO_COLOR_OPT);
    }

    public boolean shouldInvertColorOutput() {
        return cmd != null && cmd.hasOption(INVERT_COLOR_OPT);
    }

    /**
     * Returns format specified by user, defaults to Xcode format.
     * @return Format type
     */
    public Format getFormat() throws CliArgumentParserException {
        if (cmd != null) {
            try {
                return Format.parseFormat(cmd.getOptionValue(FORMAT_LONG_OPT, Format.XCODE.getName()));
            } catch (Format.IllegalFormatException e) {
                throw new CliArgumentParserException(INVALID_OPTION_VALUE + FORMAT_LONG_OPT + "."
                    + " Options are <" + Format.getFormats() + ">.");
            }
        }
        return Format.XCODE;
    }
}
