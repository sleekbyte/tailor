package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Parse command line options and arguments.
 */
public class ArgumentParser {

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

    private static final String MAX_SEVERITY_OPT = "max-severity";
    private static final String ONLY_OPT = "only";
    private static final String EXCEPT_OPT = "except";
    private static final String DEBUG_OPT = "debug";
    private static final String NO_COLOR_OPT = "no-color";
    private static final String INVERT_COLOR_OPT = "invert-color";
    private static final String DEFAULT_INT_ARG = "0";
    private static final String XCODE_INTEGRATION_OPT = "xcode";
    private static final String SHOW_RULES_OPT = "show-rules";

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
     * Parse maximum construct length flags into MaxLengths object.
     */
    public MaxLengths parseMaxLengths() throws ArgumentParserException {
        MaxLengths maxLengths = new MaxLengths();
        maxLengths.setMaxClassLength(getIntegerArgument(MAX_CLASS_LENGTH_OPT));
        maxLengths.setMaxClosureLength(getIntegerArgument(MAX_CLOSURE_LENGTH_OPT));
        maxLengths.setMaxFileLength(getIntegerArgument(MAX_FILE_LENGTH_OPT));
        maxLengths.setMaxFunctionLength(getIntegerArgument(MAX_FUNCTION_LENGTH_OPT));
        maxLengths.setMaxLineLength(getIntegerArgument(MAX_LINE_LENGTH_LONG_OPT));
        maxLengths.setMaxNameLength(getIntegerArgument(MAX_NAME_LENGTH_OPT));
        maxLengths.setMaxStructLength(getIntegerArgument(MAX_STRUCT_LENGTH_OPT));
        return maxLengths;
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

    private int getIntegerArgument(String opt) throws ArgumentParserException {
        try {
            return Integer.parseInt(this.cmd.getOptionValue(opt, DEFAULT_INT_ARG));
        } catch (NumberFormatException e) {
            throw new ArgumentParserException("Invalid value provided for integer argument " + opt + ".");
        }
    }

    /**
     * Collects all rules enabled by default and then filters out rules according to command line options.
     *
     * @return list of enabled rules after filtering
     * @throws ArgumentParserException if rule names specified in command line options are not valid
     */
    public Set<Rules> getEnabledRules() throws ArgumentParserException {
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
     * @throws ArgumentParserException if rule name specified in command line is not valid
     */
    private void checkValidRules(Set<String> enabledRules, Set<String> specifiedRules)
        throws ArgumentParserException {
        if (!enabledRules.containsAll(specifiedRules)) {
            specifiedRules.removeAll(enabledRules);
            throw new ArgumentParserException("The following rules were not recognized: " + specifiedRules);
        }
    }

    /*
     * Retrieve Xcode project path specified for --configuration.
     *
     * @return path of Xcode project
     */
    public String getXcodeprojPath() {
        return cmd != null ? cmd.getOptionValue(XCODE_INTEGRATION_OPT) : null;
    }

    /**
     * Returns maximum severity configured by user or 'warning' if not specified.
     *
     * @return Maximum severity
     * @throws ArgumentParserException if invalid value specified for --max-severity
     */
    public Severity getMaxSeverity() throws ArgumentParserException {
        try {
            return Severity.parseSeverity(this.cmd.getOptionValue(MAX_SEVERITY_OPT, Messages.WARNING));
        } catch (Severity.IllegalSeverityException ex) {
            throw new ArgumentParserException("Invalid value provided for argument " + MAX_SEVERITY_OPT + ".");
        }
    }

    public boolean debugFlagSet() throws ArgumentParserException {
        return cmd != null && cmd.hasOption(DEBUG_OPT);
    }

    public boolean shouldColorOutput() {
        return cmd != null && !cmd.hasOption(NO_COLOR_OPT);
    }

    public boolean shouldInvertColorOutput() {
        return cmd != null && cmd.hasOption(INVERT_COLOR_OPT);
    }

    public void printRules() {
        Rules[] rules = Rules.values();

        AnsiConsole.out.println(Ansi.ansi().render(String.format("@|bold # Available rules: %d|@\n", rules.length)));
        for (Rules rule : rules) {
            AnsiConsole.out.println(Ansi.ansi().render(String.format("@|bold %s|@:\n" +
                "@|underline Description:|@ %s\n" +
                "@|underline Style Guide:|@ %s\n", rule.getName(), rule.getDescription(), "link")));
        }
    }
}
