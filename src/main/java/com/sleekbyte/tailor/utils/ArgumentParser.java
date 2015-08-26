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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Parse command line options and arguments.
 */
public class ArgumentParser {

    private static final String HELP_SHORT_OPT = "h";
    private static final String HELP_LONG_OPT = "help";
    private static final String MAX_CLASS_LENGTH_OPT = "max-class-length";
    private static final String MAX_CLOSURE_LENGTH_OPT = "max-closure-length";
    private static final String MAX_FILE_LENGTH_OPT = "max-file-length";
    private static final String MAX_FUNCTION_LENGTH_OPT = "max-function-length";
    private static final String MAX_LINE_LENGTH_SHORT_OPT = "l";
    private static final String MAX_LINE_LENGTH_LONG_OPT = "max-line-length";
    private static final String MAX_NAME_LENGTH_OPT = "max-name-length";
    private static final String MAX_STRUCT_LENGTH_OPT = "max-struct-length";
    private static final String MAX_SEVERITY_OPT = "max-severity";
    private static final String ONLY_OPT = "only";
    private static final String EXCLUDE_OPT = "exclude";
    private static final String DEFAULT_INT_ARG = "0";

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
     * Print usage message with flag descriptions to STDOUT.
     */
    public void printHelp() {
        new HelpFormatter().printHelp(Messages.CMD_LINE_SYNTAX, this.options);
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
        Option help = Option.builder(HELP_SHORT_OPT)
            .longOpt(HELP_LONG_OPT)
            .desc(Messages.HELP_DESC)
            .build();

        final Option maxClassLength = createOptionWithSingleArg(MAX_CLASS_LENGTH_OPT, Messages.MAX_CLASS_LENGTH_DESC);
        final Option maxClosureLength =
            createOptionWithSingleArg(MAX_CLOSURE_LENGTH_OPT, Messages.MAX_CLOSURE_LENGTH_DESC);
        final Option maxFileLength = createOptionWithSingleArg(MAX_FILE_LENGTH_OPT, Messages.MAX_FILE_LENGTH_DESC);
        final Option maxFunctionLength =
            createOptionWithSingleArg(MAX_FUNCTION_LENGTH_OPT, Messages.MAX_FUNCTION_LENGTH_DESC);
        final Option maxLineLength =
            createOptionWithSingleArg(MAX_LINE_LENGTH_SHORT_OPT, MAX_LINE_LENGTH_LONG_OPT,
                Messages.MAX_LINE_LENGTH_DESC);
        final Option maxNameLength = createOptionWithSingleArg(MAX_NAME_LENGTH_OPT, Messages.MAX_NAME_LENGTH_DESC);
        final Option maxStructLength =
            createOptionWithSingleArg(MAX_STRUCT_LENGTH_OPT, Messages.MAX_STRUCT_LENGTH_DESC);
        final Option maxSeverity = createOptionWithSingleArg(MAX_SEVERITY_OPT, Messages.MAX_SEVERITY_DESC);
        final Option onlySpecificRules = createOptionWithMultipleArgs(ONLY_OPT, Messages.ONLY_SPECIFIC_RULES_DESC);
        final Option excludedRules = createOptionWithMultipleArgs(EXCLUDE_OPT, Messages.EXCLUDE_RULES_DESC);

        options = new Options();
        options.addOption(help);
        options.addOption(maxClassLength);
        options.addOption(maxClosureLength);
        options.addOption(maxFileLength);
        options.addOption(maxFunctionLength);
        options.addOption(maxLineLength);
        options.addOption(maxNameLength);
        options.addOption(maxStructLength);
        options.addOption(maxSeverity);
        options.addOption(onlySpecificRules);
        options.addOption(excludedRules);
    }

    /**
     * Create command line option with short name, long name, and only one argument.
     *
     * @param shortOpt short version of option
     * @param longOpt  long version of option
     * @param desc     description of option
     */
    private Option createOptionWithSingleArg(String shortOpt, String longOpt, String desc) {
        return Option.builder(shortOpt).longOpt(longOpt).hasArg().desc(desc).build();
    }

    /**
     * Create command line option with only long name and only one argument.
     *
     * @param longOpt long version of option
     * @param desc    description of option
     */
    private Option createOptionWithSingleArg(String longOpt, String desc) {
        return Option.builder().longOpt(longOpt).hasArg().desc(desc).build();
    }

    /**
     * Create command line option with long name and multiple arguments.
     * Multiple arguments can be separated by comma or by space.
     *
     * @param longOpt long version of option
     * @param desc    description of option
     */
    private Option createOptionWithMultipleArgs(String longOpt, String desc) {
        return Option.builder().longOpt(longOpt).hasArgs().valueSeparator(',').desc(desc).build();
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

        if (this.cmd.getOptionValues(ONLY_OPT) != null) {
            Set<String> onlySpecificRules = new HashSet<>(Arrays.asList(this.cmd.getOptionValues(ONLY_OPT)));
            checkValidRules(enabledRuleNames, onlySpecificRules);

            return enabledRules.stream()
                .filter(rule -> onlySpecificRules.contains(rule.getName())).collect(Collectors.toSet());
        } else if (this.cmd.getOptionValues(EXCLUDE_OPT) != null) {
            Set<String> excludedRules = new HashSet<>(Arrays.asList(this.cmd.getOptionValues(EXCLUDE_OPT)));
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

}
