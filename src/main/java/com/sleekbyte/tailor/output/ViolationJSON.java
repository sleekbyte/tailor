package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;

/**
 * Primitive representation of violation for JSON serialization.
 */
public class ViolationJSON {

    Location location;
    String severity;
    String rule;
    String message;

    /**
     * Construct a primitive representation of a violation.
     * @param lineNumber line of the violation
     * @param columnNumber column of the violation
     * @param severity severity of the violation
     * @param rule name of the rule being violated
     * @param violationMessage reason for violation
     */
    public ViolationJSON(int lineNumber, int columnNumber, Severity severity, Rules rule, String violationMessage) {
        this.location = new Location(lineNumber, columnNumber);
        this.severity = severity.toString();
        this.rule = rule.getName();
        this.message = violationMessage;
    }

    public Location getLocation() {
        return location;
    }

    public String getSeverity() {
        return severity;
    }

    public String getRule() {
        return rule;
    }

    public String getMessage() {
        return message;
    }

}
