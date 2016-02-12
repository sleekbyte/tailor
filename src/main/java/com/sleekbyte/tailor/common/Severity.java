package com.sleekbyte.tailor.common;

/**
 * Severity of violation messages.
 */
public enum Severity {
    ERROR(Messages.ERROR, 2),
    WARNING(Messages.WARNING, 1);

    private String value;
    private int severity;

    /**
     * Exception thrown when invalid severity is provided.
     */
    public static class IllegalSeverityException extends Exception {}

    Severity(String value, int severity) {
        this.value = value;
        this.severity = severity;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Parse str and convert to appropriate Severity.
     *
     * @param str Severity string (error|warning)
     * @return Parsed Severity value
     * @throws IllegalSeverityException if string is not recognized
     */
    public static Severity parseSeverity(String str) throws IllegalSeverityException {
        if (str.equalsIgnoreCase(Messages.ERROR)) {
            return ERROR;
        } else if (str.equalsIgnoreCase(Messages.WARNING)) {
            return WARNING;
        } else {
            throw new IllegalSeverityException();
        }
    }

    public static Severity min(Severity s1, Severity s2) {
        return (s1.severity < s2.severity) ? s1 : s2;
    }
}
