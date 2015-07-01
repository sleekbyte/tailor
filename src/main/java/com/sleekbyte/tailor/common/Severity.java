package com.sleekbyte.tailor.common;

public enum Severity {
    ERROR(Messages.ERROR, 2),
    WARNING(Messages.WARNING, 1);

    private String value;
    private int severity;

    Severity(String value, int severity) {
        this.value = value;
        this.severity = severity;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static class IllegalSeverityException extends Exception {

    }

    public static Severity parseSeverity(String str) throws IllegalSeverityException {
        if (str.toLowerCase().equals(Messages.ERROR)) {
            return ERROR;
        }
        else if (str.toLowerCase().equals(Messages.WARNING)) {
            return WARNING;
        }
        else {
            throw new IllegalSeverityException();
        }
    }

    public static Severity min(Severity s1, Severity s2) {
        return (s1.severity < s2.severity) ? s1 : s2;
    }
}
