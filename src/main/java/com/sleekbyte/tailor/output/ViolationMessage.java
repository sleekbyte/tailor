package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Severity;

/**
 * Note: this class has a natural ordering that is inconsistent with equals.
 */
public class ViolationMessage implements Comparable<ViolationMessage> {

    private String filePath = "";
    private int lineNumber;
    private int columnNumber;
    private Severity severity = null;
    private String violationMessage = "";

    /**
     * Constructs a ViolationMessage with the specified message components.
     *
     * @param lineNumber       the logical line number in the source file
     * @param columnNumber     the logical column number in the source file
     * @param severity         the severity of the violation message
     * @param violationMessage the description of the violation message
     */
    public ViolationMessage(int lineNumber, int columnNumber, Severity severity, String violationMessage) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.severity = severity;
        this.violationMessage = violationMessage;
    }

    /**
     * Constructs a ViolationMessage with the specified message components.
     *
     * @param filePath         the path of the source file
     * @param lineNumber       the logical line number in the source file
     * @param columnNumber     the logical column number in the source file
     * @param severity         the severity of the violation message
     * @param violationMessage the description of the violation message
     */
    public ViolationMessage(String filePath, int lineNumber, int columnNumber, Severity severity,
                            String violationMessage) {
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.severity = severity;
        this.violationMessage = violationMessage;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int compareTo(final ViolationMessage message) {
        int ret = this.lineNumber - message.lineNumber;
        if (ret == 0) {
            ret = this.columnNumber - message.columnNumber;
        }
        if (ret == 0) {
            ret = this.severity.toString().compareTo(message.severity.toString());
        }
        if (ret == 0) {
            return this.violationMessage.compareTo(message.violationMessage);
        }
        return ret;
    }

    @Override
    /**
     * Implementation required since equals() is overridden.
     */
    public int hashCode() {
        int result = filePath.hashCode();
        result = 31 * result + lineNumber;
        result = 31 * result + columnNumber;
        result = 31 * result + severity.hashCode();
        result = 31 * result + violationMessage.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ViolationMessage)) {
            return false;
        }

        ViolationMessage candidateObject = (ViolationMessage) obj;

        // Test primitives (int) for equality first, then objects (String)
        return (this.lineNumber == candidateObject.lineNumber)
            && (this.columnNumber == candidateObject.columnNumber)
            && (this.filePath.equals(candidateObject.filePath))
            && (this.severity.equals(candidateObject.severity))
            && (this.violationMessage.equals(candidateObject.violationMessage));
    }

    @Override
    public String toString() {
        // filePath, severity, violationMessage are optional in the output, but at least one must be present
        if (this.filePath.isEmpty() && this.severity == null && this.violationMessage.isEmpty()) {
            return "";
        }

        if (this.columnNumber == 0) {
            return String.format("%s:%d: %s: %s", this.filePath, this.lineNumber, this.severity,
                this.violationMessage);
        }
        return String.format("%s:%d:%d: %s: %s", this.filePath, this.lineNumber, this.columnNumber, this.severity,
            this.violationMessage);
    }

}
