package com.sleekbyte.tailor.output;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import com.sleekbyte.tailor.common.Messages;
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
        this.violationMessage = new ViolationMessage("/usr/bin/local", 10, 1, Messages.ERROR, "errMsg");
    }

    @Test
    public void testCompareToMessageWithGreaterLineNumber() {
        // global message has lower line number than test message
        ViolationMessage messageWithGreaterLineNumber =
            new ViolationMessage("/usr/bin/local", 12, 5, Messages.ERROR, "errMsg");
        int ret = this.violationMessage.compareTo(messageWithGreaterLineNumber);
        assertThat(ret, lessThan(0));
    }

    @Test
    public void testCompareToMessageWithLesserLineNumber() {
        // global message has lower line number than test message
        ViolationMessage messageWithGreaterLineNumber =
            new ViolationMessage("/usr/bin/local", 12, 5, Messages.ERROR, "errMsg");
        int ret = this.violationMessage.compareTo(messageWithGreaterLineNumber);
        assertThat(ret, lessThan(0));
    }

    @Test
    public void testCompareToMessageWithGreaterColumnNumber() {
        // global message has equal line number but greater column number than test message
        ViolationMessage messageWithGreaterColumnNumber =
            new ViolationMessage("/usr/bin/local", 10, 2, Messages.ERROR, "errMsg");
        int ret = this.violationMessage.compareTo(messageWithGreaterColumnNumber);
        assertThat(ret, lessThan(0));
    }

    @Test
    public void testCompareToMessageWithLesserColumnNumber() {
        // global message has equal line number but lesser column number than test message
        ViolationMessage messageWithLesserColumnNumber =
            new ViolationMessage("/usr/bin/local", 10, 0, Messages.WARNING, "warningMsg");
        int ret = this.violationMessage.compareTo(messageWithLesserColumnNumber);
        assertThat(ret, greaterThan(0));
    }

    @Test
    public void testCompareToMessageWithEqualLineAndColumnNumbers() {
        // global message has equal line number and column number wrt test message
        ViolationMessage messageWithEqualLineColumnNumber =
            new ViolationMessage("/usr/bin/local", 10, 1, Messages.WARNING, "warningMsg");
        int ret = this.violationMessage.compareTo(messageWithEqualLineColumnNumber);
        assertEquals(ret, 0);
    }

    @Test
    public void testEqualsUnequalMessage() {
        // Unequal ViolationMessages, verify that each field is checked for differences
        ViolationMessage unequalViolationMessage =
            new ViolationMessage("/usr/bin/local", 12, 1, Messages.ERROR, "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage("/usr/bin/local", 10, 5, Messages.ERROR, "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage("/usr/bin/local/diff", 10, 1, Messages.ERROR, "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage("/usr/bin/local", 10, 1, Messages.WARNING, "errMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);

        unequalViolationMessage = new ViolationMessage("/usr/bin/local", 10, 1, Messages.ERROR, "warningMsg");
        assertNotEquals(this.violationMessage, unequalViolationMessage);
    }

    @Test
    public void testEqualsSameMessage() {
        // Equal ViolationMessages
        ViolationMessage equalViolationMessage =
            new ViolationMessage("/usr/bin/local", 10, 1, Messages.ERROR, "errMsg");
        assertEquals(this.violationMessage, equalViolationMessage);
    }

    @Test
    public void testToString() {
        String expectedOutput = "/usr/bin/local:10:1: error: errMsg";
        assertEquals(expectedOutput, this.violationMessage.toString());
    }
}
