package com.sleekbyte.tailor.output;

/**
 * Note: this class has a natural ordering that is inconsistent with equals.
 */
public class ViolationMessage implements Comparable<ViolationMessage> {

    public static final ViolationMessage EMPTY = new ViolationMessage("", 0, 0, "", "");

    private String filePath;
    private int lineNumber;
    private int columnNumber;
    private String classification;
    private String violationMessage;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setViolationMessage(String violationMessage) {
        this.violationMessage = violationMessage;
    }

    public ViolationMessage() {

    }

    public ViolationMessage(String filePath, int lineNumber, int columnNumber, String classification,
                            String violationMessage) {
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.classification = classification;
        this.violationMessage = violationMessage;
    }

    @Override
    public int compareTo(final ViolationMessage message) {
        int ret = this.lineNumber - message.lineNumber;
        if (ret == 0) {
            ret = this.columnNumber - message.columnNumber;
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ViolationMessage)) return false;

        ViolationMessage candidateObject = (ViolationMessage) o;

        // Test primitives (int) for equality first, then objects (String)
        return (this.lineNumber == candidateObject.lineNumber)
            && (this.columnNumber == candidateObject.columnNumber)
            && (this.filePath.equals(candidateObject.filePath))
            && (this.classification.equals(candidateObject.classification))
            && (this.violationMessage.equals(candidateObject.violationMessage));
    }

    @Override
    public String toString() {
        // filePath, classification, violationMessage are optional in the output, but at least one must be present
        if (this.filePath.isEmpty() && this.classification.isEmpty() && this.violationMessage.isEmpty()) { return ""; }

        if (this.columnNumber == 0) {
            return String.format("%s:%d: %s: %s", this.filePath, this.lineNumber, this.classification,
                this.violationMessage);
        }
        return String.format("%s:%d:%d: %s: %s", this.filePath, this.lineNumber, this.columnNumber, this.classification,
            this.violationMessage);
    }

}
