package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.ConstructLengths;
import com.sleekbyte.tailor.common.Messages;
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

/**
 * Parse command line options and arguments.
 */
public final class CLIArgumentParser {

    private Options options;
    private CommandLine cmd;

    /**
     * Exception thrown when option parsing fails.
     */
    public static class CLIArgumentParserException extends Exception {
        public CLIArgumentParserException(String message) {
            super(message);
        }
    }

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
        return cmd != null && cmd.hasOption(Messages.HELP_SHORT_OPT);
    }

    /**
     * Check if "-v" or "--version" option was specified.
     */
    public boolean shouldPrintVersion() {
        return cmd != null && cmd.hasOption(Messages.VERSION_SHORT_OPT);
    }

    /**
     * Check if "--show-rules" option was specified.
     */
    public boolean shouldPrintRules() {
        return cmd != null && cmd.hasOption(Messages.SHOW_RULES_OPT);
    }

    /**
     * Check if "--list-files" option was specified.
     */
    public boolean shouldListFiles() {
        return cmd != null && cmd.hasOption(Messages.LIST_FILES_OPT);
    }

    /**
     * Check if "--purge" option was specified.
     */
    public boolean shouldClearDFAs() {
        return cmd != null && cmd.hasOption(Messages.PURGE_OPT);
    }

    /**
     * Returns number specified with --purge option, or 0 if not specified.
     */
    public int numberOfFilesBeforePurge() throws CLIArgumentParserException {
        return getIntegerArgument(Messages.PURGE_OPT);
    }

    /**
     * Print usage message with flag descriptions to STDOUT.
     */
    public void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setSyntaxPrefix(Messages.SYNTAX_PREFIX);
        String newLine = helpFormatter.getNewLine();
        String header = newLine + Messages.TAILOR_DESC + newLine + newLine + Messages.TAILOR_ARGS_INFO + newLine
            + newLine + Messages.OPTIONS_PREFIX;
        helpFormatter.setLongOptSeparator("=");
        helpFormatter.printHelp(Messages.HELP_WIDTH, Messages.CMD_LINE_SYNTAX, header, this.options, "");
    }

    /**
     * Parse construct length flags into ConstructLengths object.
     */
    public ConstructLengths parseConstructLengths() throws CLIArgumentParserException {
        ConstructLengths constructLengths = new ConstructLengths();

        constructLengths.setMaxClassLength(getIntegerArgument(Messages.MAX_CLASS_LENGTH_OPT));
        constructLengths.setMaxClosureLength(getIntegerArgument(Messages.MAX_CLOSURE_LENGTH_OPT));
        constructLengths.setMaxFileLength(getIntegerArgument(Messages.MAX_FILE_LENGTH_OPT));
        constructLengths.setMaxFunctionLength(getIntegerArgument(Messages.MAX_FUNCTION_LENGTH_OPT));
        constructLengths.setMaxLineLength(getIntegerArgument(Messages.MAX_LINE_LENGTH_LONG_OPT));
        constructLengths.setMaxNameLength(getIntegerArgument(Messages.MAX_NAME_LENGTH_OPT));
        constructLengths.setMaxStructLength(getIntegerArgument(Messages.MAX_STRUCT_LENGTH_OPT));

        constructLengths.setMinNameLength(getIntegerArgument(Messages.MIN_NAME_LENGTH_OPT));

        return constructLengths;
    }

    private void addOptions() {
        options = new Options();

        options.addOption(createNoArgOpt(Messages.HELP_SHORT_OPT, Messages.HELP_LONG_OPT, Messages.HELP_DESC));
        options.addOption(createNoArgOpt(Messages.VERSION_SHORT_OPT, Messages.VERSION_LONG_OPT, Messages.VERSION_DESC));

        String argName = "0-999";
        options.addOption(createSingleArgOpt(Messages.MAX_CLASS_LENGTH_OPT, argName, Messages.MAX_CLASS_LENGTH_DESC));
        options.addOption(createSingleArgOpt(Messages.MAX_CLOSURE_LENGTH_OPT, argName,
            Messages.MAX_CLOSURE_LENGTH_DESC));
        options.addOption(createSingleArgOpt(Messages.MAX_FILE_LENGTH_OPT, argName, Messages.MAX_FILE_LENGTH_DESC));
        options.addOption(createSingleArgOpt(Messages.MAX_FUNCTION_LENGTH_OPT, argName,
            Messages.MAX_FUNCTION_LENGTH_DESC));
        options.addOption(createSingleArgOpt(
            Messages.MAX_LINE_LENGTH_SHORT_OPT, Messages.MAX_LINE_LENGTH_LONG_OPT, argName,
            Messages.MAX_LINE_LENGTH_DESC));
        options.addOption(createSingleArgOpt(Messages.MAX_NAME_LENGTH_OPT, argName, Messages.MAX_NAME_LENGTH_DESC));
        options.addOption(createSingleArgOpt(Messages.MAX_STRUCT_LENGTH_OPT, argName, Messages.MAX_STRUCT_LENGTH_DESC));

        argName = "1-999";
        options.addOption(createSingleArgOpt(Messages.MIN_NAME_LENGTH_OPT, argName, Messages.MIN_NAME_LENGTH_DESC));
        options.addOption(createSingleArgOpt(Messages.PURGE_OPT, argName, Messages.PURGE_DESC));


        argName = "error|warning (default)";
        options.addOption(createSingleArgOpt(Messages.MAX_SEVERITY_OPT, argName, Messages.MAX_SEVERITY_DESC));

        argName = "rule1,rule2,...";
        options.addOption(createMultiArgOpt(Messages.ONLY_OPT, argName, Messages.ONLY_SPECIFIC_RULES_DESC));
        options.addOption(createMultiArgOpt(Messages.EXCEPT_OPT, argName, Messages.EXCEPT_RULES_DESC));

        argName = "path/to/project.xcodeproj";
        options.addOption(createSingleArgOpt(Messages.XCODE_INTEGRATION_OPT, argName, Messages.XCODE_INTEGRATION_DESC));

        options.addOption(createNoArgOpt(Messages.DEBUG_OPT, Messages.DEBUG_DESC));

        options.addOption(createNoArgOpt(Messages.NO_COLOR_OPT, Messages.NO_COLOR_DESC));
        options.addOption(createNoArgOpt(Messages.INVERT_COLOR_OPT, Messages.INVERT_COLOR_DESC));

        options.addOption(createNoArgOpt(Messages.SHOW_RULES_OPT, Messages.SHOW_RULES_DESC));

        argName = "path/to/.tailor.yml";
        options.addOption(createSingleArgOpt(Messages.CONFIG_SHORT_OPT, Messages.CONFIG_LONG_OPT, argName,
            Messages.CONFIG_FILE_DESC));

        options.addOption(createNoArgOpt(Messages.LIST_FILES_OPT, Messages.LIST_FILES_DESC));

        argName = Format.getFormats();
        options.addOption(createSingleArgOpt(Messages.FORMAT_SHORT_OPT, Messages.FORMAT_LONG_OPT, argName,
            Messages.FORMAT_DESC));
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

    private int getIntegerArgument(String opt) throws CLIArgumentParserException {
        try {
            return Integer.parseInt(this.cmd.getOptionValue(opt, Messages.DEFAULT_INT_ARG));
        } catch (NumberFormatException e) {
            throw new CLIArgumentParserException("Invalid value provided for integer argument " + opt + ".");
        }
    }

    public Set<String> getOnlySpecificRules() {
        return cmd.hasOption(Messages.ONLY_OPT) ? new HashSet<>(Arrays.asList(cmd.getOptionValues(Messages.ONLY_OPT)))
                                       : new HashSet<>();
    }

    /**
     * Get list of disabled rules.
     *
     * @return Excluded rules
     */
    public Set<String> getExcludedRules() {
        return cmd.hasOption(Messages.EXCEPT_OPT)
            ? new HashSet<>(Arrays.asList(cmd.getOptionValues(Messages.EXCEPT_OPT)))
            : new HashSet<>();
    }

    /*
     * Retrieve Xcode project path specified for --xcode.
     *
     * @return path of Xcode project
     */
    public String getXcodeprojPath() {
        return cmd != null ? cmd.getOptionValue(Messages.XCODE_INTEGRATION_OPT) : null;
    }

    /*
     * Retrieve .tailor.yml config file path specified for --config.
     *
     * @return path of config file
     */
    public Optional<String> getConfigFilePath() {
        return cmd != null ? Optional.ofNullable(cmd.getOptionValue(Messages.CONFIG_LONG_OPT)) : Optional.empty();
    }

    /**
     * Returns maximum severity configured by user or 'warning' if not specified.
     *
     * @return Maximum severity
     * @throws CLIArgumentParserException if invalid value specified for --max-severity
     */
    public Severity getMaxSeverity() throws CLIArgumentParserException {
        try {
            return Severity.parseSeverity(this.cmd.getOptionValue(Messages.MAX_SEVERITY_OPT, Messages.WARNING));
        } catch (Severity.IllegalSeverityException ex) {
            throw new CLIArgumentParserException(Messages.INVALID_OPTION_VALUE + Messages.MAX_SEVERITY_OPT + ".");
        }
    }

    public boolean debugFlagSet() throws CLIArgumentParserException {
        return cmd != null && cmd.hasOption(Messages.DEBUG_OPT);
    }

    public boolean shouldColorOutput() {
        return cmd != null && !cmd.hasOption(Messages.NO_COLOR_OPT);
    }

    public boolean shouldInvertColorOutput() {
        return cmd != null && cmd.hasOption(Messages.INVERT_COLOR_OPT);
    }

    public boolean formatOptionSet() {
        return cmd != null && cmd.hasOption(Messages.FORMAT_LONG_OPT);
    }

    /**
     * Returns format specified by user, defaults to Xcode format.
     *
     * @return Format type
     */
    public Format getFormat() throws CLIArgumentParserException {
        if (cmd != null) {
            try {
                return Format.parseFormat(cmd.getOptionValue(Messages.FORMAT_LONG_OPT, Format.XCODE.getName()));
            } catch (Format.IllegalFormatException e) {
                throw new CLIArgumentParserException(Messages.INVALID_OPTION_VALUE + Messages.FORMAT_LONG_OPT + "."
                    + " Options are <" + Format.getFormats() + ">.");
            }
        }
        return Format.XCODE;
    }

}
