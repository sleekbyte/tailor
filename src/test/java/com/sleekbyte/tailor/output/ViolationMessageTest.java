package com.sleekbyte.tailor.output;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link ViolationMessage}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ViolationMessageTest {

    private ViolationMessage violationMessage;

    @Before
    public void setUp() {
        this.violationMessage = new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 1, Severity.ERROR,
            "errMsg");
    }

    @Test
    public void testCompareToMessageWithGreaterLineNumber() {
        // global message has lower line number than test message
        ViolationMessage messageWithGreaterLineNumber =
            new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 12, 5, Severity.ERROR, "errMsg");
        int ret = this.violationMessage.compareTo(messageWithGreaterLineNumber);
        assertThat(ret, lessThan(0));
    }

    @Test
    public void testCompareToMessageWithLesserLineNumber() {
        // global message has lower line number than test message
        ViolationMessage messageWithGreaterLineNumber =
            new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 12, 5, Severity.ERROR, "errMsg");
        int ret = this.violationMessage.compareTo(messageWithGreaterLineNumber);
        assertThat(ret, lessThan(0));
    }

    @Test
    public void testCompareToMessageWithGreaterColumnNumber() {
        // global message has equal line number but greater column number than test message
        ViolationMessage messageWithGreaterColumnNumber =
            new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 2, Severity.ERROR, "errMsg");
        int ret = this.violationMessage.compareTo(messageWithGreaterColumnNumber);
        assertThat(ret, lessThan(0));
    }

    @Test
    public void testCompareToMessageWithLesserColumnNumber() {
        // global message has equal line number but lesser column number than test message
        ViolationMessage messageWithLesserColumnNumber =
            new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 0, Severity.WARNING, "warningMsg");
        int ret = this.violationMessage.compareTo(messageWithLesserColumnNumber);
        assertThat(ret, greaterThan(0));
    }

    @Test
    public void testCompareToMessageWithEqualLineAndColumnNumbers() {
        // global message has equal line number and column number wrt test message
        ViolationMessage messageWithEqualLineColumnNumberButDifferentText =
            new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 1, Severity.ERROR, "warningMsg");
        int ret = this.violationMessage.compareTo(messageWithEqualLineColumnNumberButDifferentText);
        assertTrue(ret < 0);
        ret = this.violationMessage.compareTo(this.violationMessage);
        assertEquals(0, ret);
        ViolationMessage messageWithEqualLineColumnNumberAndSameTextButDifferentSeverity =
            new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 1, Severity.WARNING, "errorMsg");
        ret = this.violationMessage.compareTo(messageWithEqualLineColumnNumberAndSameTextButDifferentSeverity);
        assertTrue(ret < 0);
    }

    @Test
    public void testEqualsUnequalMessage() {
        // Unequal ViolationMessages, verify that each field is checked for differences
        ViolationMessage unequalViolationMessage =
            new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 12, 1, Severity.ERROR, "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 5, Severity.ERROR,
            "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local/diff", 10, 1,
            Severity.ERROR, "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 1,
            Severity.WARNING, "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 1,
            Severity.ERROR, "warningMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage(Rules.BRACE_STYLE, "/usr/bin/local", 12, 1,
            Severity.ERROR, "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);
    }

    @Test
    public void testEqualsSameMessage() {
        // Equal ViolationMessages
        ViolationMessage equalViolationMessage =
            new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 1, Severity.ERROR, "errMsg");
        assertEquals(this.violationMessage, equalViolationMessage);
    }


    @Test
    public void testToStringNonNullValues() {
        String expectedOutput = "/usr/bin/local:10:1: error: [lower-camel-case] errMsg";
        assertEquals(expectedOutput, this.violationMessage.toString());
    }

    @Test
    public void testToStringNullValues() {
        ViolationMessage violationMessage = new ViolationMessage(null, "", 0, 0, null, "");
        String output = violationMessage.toString();
        assertEquals("", output);
    }

    @Test
    public void testEqualsNotInstanceOfViolationMessage() {
        ViolationMessage violationMessage = new ViolationMessage(null, "", 0, 0, null, "");
        assertEquals("", violationMessage.toString());
    }

    @Test
    public void testEqualsSameInstanceOfViolationMessage() {
        ViolationMessage violationMessage = new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 10, 1,
            Severity.ERROR, "errMsg");
        assertEquals(violationMessage, violationMessage);
    }

    @Test
    public void testHashCode() {
        ViolationMessage msg1 = new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 20, 23, Severity.WARNING,
            "errMsg");
        ViolationMessage msg2 = new ViolationMessage(Rules.LOWER_CAMEL_CASE, "/usr/bin/local", 20, 23, Severity.WARNING,
            "errMsg");
        assertEquals(msg1, msg2);
        assertEquals(msg1.hashCode(), msg2.hashCode());
    }

}
