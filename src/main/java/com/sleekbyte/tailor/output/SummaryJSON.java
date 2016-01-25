package com.sleekbyte.tailor.output;

/**
 * Primitive representation of summary for JSON serialization.
 */
public class SummaryJSON {

    long analyzed;
    long skipped;
    long violations;
    long errors;
    long warnings;

    /**
     * Construct a primitive representation of a summary.
     * @param numFilesAnalyzed files successfully analyzed
     * @param numSkipped files skipped due to parse failures
     * @param numViolations total violations found
     * @param numErrors errors found
     * @param numWarnings warnings found
     */
    public SummaryJSON(long numFilesAnalyzed, long numSkipped, long numViolations, long numErrors, long numWarnings) {
        this.analyzed = numFilesAnalyzed;
        this.skipped = numSkipped;
        this.violations = numViolations;
        this.errors = numErrors;
        this.warnings = numWarnings;
    }

    public long getAnalyzed() {
        return analyzed;
    }

    public long getSkipped() {
        return skipped;
    }

    public long getViolations() {
        return violations;
    }

    public long getErrors() {
        return errors;
    }

    public long getWarnings() {
        return warnings;
    }

}
