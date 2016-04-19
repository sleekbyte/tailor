package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.ColorSettings;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
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
    private ColorSettings colorSettings = new ColorSettings(false, false);
    private int lineNumberWidth = 0;
    private int columnNumberWidth = 0;
    private String textColor = "black";
    private Rules rule;

    /**
     * Constructs a ViolationMessage with the specified message components.
     *
     * @param rule             the rule associated with the violation
     * @param filePath         the path of the source file
     * @param lineNumber       the logical line number in the source file
     * @param columnNumber     the logical column number in the source file
     * @param severity         the severity of the violation message
     * @param violationMessage the description of the violation message
     */
    public ViolationMessage(Rules rule, String filePath, int lineNumber, int columnNumber, Severity severity,
                            String violationMessage) {
        this.rule = rule;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.severity = severity;
        this.violationMessage = violationMessage;
    }

    /**
     * Constructs a ViolationMessage with the specified message components.
     *
     * @param rule             the rule associated with the violation message
     * @param lineNumber       the logical line number in the source file
     * @param columnNumber     the logical column number in the source file
     * @param severity         the severity of the violation message
     * @param violationMessage the description of the violation message
     */
    public ViolationMessage(Rules rule, int lineNumber, int columnNumber, Severity severity, String violationMessage) {
        this.rule = rule;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.severity = severity;
        this.violationMessage = violationMessage;
    }

    /**
     * Constructs a ViolationMessage where the rule name is replaced by tool name (tailor).
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
        this.violationMessage = Messages.TAILOR + violationMessage;
    }

    /**
     * Constructs a ViolationMessage where the rule name is replaced by tool name (tailor).
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
        this.violationMessage = Messages.TAILOR + violationMessage;
    }

    public Rules getRule() {
        return rule;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return violationMessage;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setColorSettings(ColorSettings colorSettings) {
        this.colorSettings = colorSettings;
        textColor = colorSettings.invertColor ? "white" : "black";
    }

    public void setLineNumberWidth(int lineNumberWidth) {
        this.lineNumberWidth = lineNumberWidth;
    }

    public void setColumnNumberWidth(int columnNumberWidth) {
        this.columnNumberWidth = columnNumberWidth;
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
            ret = this.violationMessage.compareTo(message.violationMessage);
        }
        if (ret == 0) {
            return this.rule.compareTo(message.rule);
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
            && (this.violationMessage.equals(candidateObject.violationMessage))
            && (this.rule.equals(candidateObject.rule));
    }

    @Override
    public String toString() {
        // filePath, severity, violationMessage are optional in the output, but at least one must be present
        if (this.filePath.isEmpty() && this.severity == null && this.violationMessage.isEmpty()) {
            return "";
        }

        if (formattedRule().isEmpty()) {
            return String.format("%s%s%s %s %s", formattedFilePath(), formattedLineNumber(), formattedColumnNumber(),
                formattedSeverity(), formattedViolationMessage());
        }

        return String.format("%s%s%s %s %s %s", formattedFilePath(), formattedLineNumber(), formattedColumnNumber(),
            formattedSeverity(), formattedRule(), formattedViolationMessage());
    }

    private String formattedRule() {
        return rule != null ? String.format("[%s]", rule.getName()) : "";
    }

    private String formattedFilePath() {
        return String.format("%s:", filePath);
    }

    private String formattedLineNumber() {
        String res;
        if (lineNumberWidth > 0 && colorSettings.colorOutput) {
            res = String.format("%0" + lineNumberWidth + "d:", lineNumber);
            res = "@|bg_blue," + textColor + " " + res + "|@";
        } else {
            res = String.format("%d:", lineNumber);
        }
        return res;
    }

    private String formattedColumnNumber() {
        String res;
        if (columnNumber == 0) {
            if (columnNumberWidth > 0 && colorSettings.colorOutput) {
                res = String.format("%" + (columnNumberWidth + 1) + "s", "");
                res = "@|bg_blue," + textColor + " " + res + "|@";
            } else {
                res = "";
            }
        } else {
            if (columnNumberWidth > 0 && colorSettings.colorOutput) {
                res = String.format("%0" + columnNumberWidth + "d:", columnNumber);
                res = "@|bg_blue," + textColor + " " + res + "|@";
            } else {
                res = String.format("%d:", columnNumber);
            }
        }
        return res;
    }

    private String formattedSeverity() {
        String res;
        if (colorSettings.colorOutput) {
            res = String.format("%7s:", severity);
            if (severity.equals(Severity.ERROR)) {
                res = "@|bg_red," + textColor + " " + res + "|@";
            } else if (severity.equals(Severity.WARNING)) {
                res = "@|bg_yellow," + textColor + " " + res + "|@";
            } else {
                res = "@|bg_white," + textColor + " " + res + "|@";
            }
        } else {
            res = String.format("%s:", severity);
        }
        return res;
    }

    private String formattedViolationMessage() {
        return violationMessage;
    }

}
