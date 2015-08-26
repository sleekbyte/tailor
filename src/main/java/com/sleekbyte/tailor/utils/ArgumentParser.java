package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Severity;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
    private static final String NO_COLOR_OPT = "no-color";
    private static final String INVERT_COLOR_OPT = "invert-color";
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

        final Option maxClassLength = addArgument(MAX_CLASS_LENGTH_OPT, Messages.MAX_CLASS_LENGTH_DESC);
        final Option maxClosureLength = addArgument(MAX_CLOSURE_LENGTH_OPT, Messages.MAX_CLOSURE_LENGTH_DESC);
        final Option maxFileLength = addArgument(MAX_FILE_LENGTH_OPT, Messages.MAX_FILE_LENGTH_DESC);
        final Option maxFunctionLength = addArgument(MAX_FUNCTION_LENGTH_OPT, Messages.MAX_FUNCTION_LENGTH_DESC);
        final Option maxLineLength = addArgument(MAX_LINE_LENGTH_SHORT_OPT, MAX_LINE_LENGTH_LONG_OPT,
            Messages.MAX_LINE_LENGTH_DESC);
        final Option maxNameLength = addArgument(MAX_NAME_LENGTH_OPT, Messages.MAX_NAME_LENGTH_DESC);
        final Option maxStructLength = addArgument(MAX_STRUCT_LENGTH_OPT, Messages.MAX_STRUCT_LENGTH_DESC);
        final Option maxSeverity = addArgument(MAX_SEVERITY_OPT, Messages.MAX_SEVERITY_DESC);
        final Option noColor = Option.builder().longOpt(NO_COLOR_OPT).desc(Messages.NO_COLOR_DESC).build();
        final Option invertColor = Option.builder().longOpt(INVERT_COLOR_OPT).desc(INVERT_COLOR_OPT).build();

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
        options.addOption(noColor);
        options.addOption(invertColor);
    }

    /**
     * Add argument with short and long name to command line options.
     *
     * @param shortOpt short version of option
     * @param longOpt  long version of option
     * @param desc     description of option
     */
    private Option addArgument(String shortOpt, String longOpt, String desc) {
        return Option.builder(shortOpt).longOpt(longOpt).hasArg().desc(desc).build();
    }

    /**
     * Add argument with only long name to command line options.
     *
     * @param longOpt long version of option
     * @param desc    description of option
     */
    private Option addArgument(String longOpt, String desc) {
        return Option.builder().longOpt(longOpt).hasArg().desc(desc).build();
    }

    private int getIntegerArgument(String opt) throws ArgumentParserException {
        try {
            return Integer.parseInt(this.cmd.getOptionValue(opt, DEFAULT_INT_ARG));
        } catch (NumberFormatException e) {
            throw new ArgumentParserException("Invalid value provided for integer argument " + opt + ".");
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

    public boolean shouldColorOutput() {
        return !this.cmd.hasOption(NO_COLOR_OPT);
    }

    public boolean shouldInvertColorOutput() {
        return this.cmd.hasOption(INVERT_COLOR_OPT);
    }

}
