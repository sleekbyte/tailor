package com.sleekbyte.tailor.common;

public enum Severity {
    ERROR(Messages.ERROR),
    WARNING(Messages.WARNING);

    private String value;

    Severity(String strRepr) {
        value = strRepr;
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
}
