package com.sleekbyte.tailor.output;

public class ViolationMessage implements Comparable<ViolationMessage> {
    private final String filePath;
    private final int lineNumber;
    private final int columnNumber;
    private final String classification;
    private final String violationMessage;

    public ViolationMessage(String filePath, int lineNumber, int columnNumber, String classification, String violationMessage) {
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.classification = classification;
        this.violationMessage = violationMessage;
    }

    @Override
    public int compareTo(final ViolationMessage message) {
        return this.filePath.compareToIgnoreCase(message.filePath);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ViolationMessage)) {
            return false;
        }
        ViolationMessage message;
        message = (ViolationMessage)o;
        return this.filePath == message.filePath
                && this.lineNumber == message.lineNumber
                && this.columnNumber == message.lineNumber
                && this.classification == message.classification
                && this.violationMessage == message.violationMessage;
    }

    public String toString() {
        return String.format("%s:%d:%d: %s: %s", this.filePath, this.lineNumber, this.columnNumber, this.classification, this.violationMessage);
    }
}
