package com.sleekbyte.tailor.interfaces;

import com.sleekbyte.tailor.common.ConstructLengths;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.utils.CliArgumentParserException;

import java.io.IOException;
import java.util.Set;

/**
 * Interface implemented by tailor settings manager classes.
 */
public interface ConfigurationInterface {

    public boolean shouldPrintHelp();

    public boolean shouldPrintVersion();

    public boolean shouldPrintRules();

    public boolean shouldListFiles();

    public boolean shouldColorOutput();

    public boolean shouldInvertColorOutput();

    public boolean debugFlagSet() throws CliArgumentParserException;

    public Set<Rules> getEnabledRules() throws CliArgumentParserException;

    public Set<String> getFilesToAnalyze() throws IOException;

    public ConstructLengths parseConstructLengths() throws CliArgumentParserException;

    public Severity getMaxSeverity() throws CliArgumentParserException;

    public String getXcodeprojPath();

    public void printHelp();
}
