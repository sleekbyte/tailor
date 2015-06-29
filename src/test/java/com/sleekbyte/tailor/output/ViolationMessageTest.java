package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link ViolationMessage}
 */
@RunWith(MockitoJUnitRunner.class)

public class ViolationMessageTest {

    ViolationMessage violationMessage;

    @Before
    public void setUp() {
        this.violationMessage = new ViolationMessage("/usr/bin/local", 10, 1, Messages.ERROR, "errMsg");
    }

    @Test
    public void testCompareTo() {
        // global message has lower line number than test message
        ViolationMessage messageWithGreaterLineNumber = new ViolationMessage("/usr/bin/local", 12, 5, Messages.ERROR, "errMsg");
        int ret = this.violationMessage.compareTo(messageWithGreaterLineNumber);
        assertTrue(ret < 0);

        // global message has greater line number than test message
        ViolationMessage messageWithLesserLineNumber = new ViolationMessage("/usr/bin/local", 8, 5, Messages.ERROR, "errMsg");
        ret = this.violationMessage.compareTo(messageWithLesserLineNumber);
        assertTrue(ret > 0);

        // global message has equal line number but greater column number than test message
        ViolationMessage messageWithGreaterColumnNumber = new ViolationMessage("/usr/bin/local", 10, 2, Messages.ERROR, "errMsg");
        ret = this.violationMessage.compareTo(messageWithGreaterColumnNumber);
        assertTrue(ret < 0);

        // global message has equal line number but lesser column number than test message
        ViolationMessage messageWithLesserColumnNumber = new ViolationMessage("/usr/bin/local", 10, 0, Messages.WARNING, "warningMsg");
        ret = this.violationMessage.compareTo(messageWithLesserColumnNumber);
        assertTrue(ret > 0);

        // global message has equal line number and column number wrt test message
        ViolationMessage messageWithEqualLineColumnNumber = new ViolationMessage("/usr/bin/local", 10, 1, Messages.WARNING, "warningMsg");
        ret = this.violationMessage.compareTo(messageWithEqualLineColumnNumber);
        assertTrue(ret == 0);
    }

    @Test
    public void testEquals() {
        // Equal ViolationMessage
        ViolationMessage unequalViolationMessage = new ViolationMessage("/usr/bin/local", 12, 5, Messages.ERROR, "errMsg");
        assertFalse(this.violationMessage.equals(unequalViolationMessage));

        // Unequal ViolationMessage
        ViolationMessage equalViolationMessage = new ViolationMessage("/usr/bin/local", 10, 1, Messages.ERROR, "errMsg");
        assertTrue(this.violationMessage.equals(equalViolationMessage));
    }

    @Test
    public void testToString() {
        String expectedOutput = "/usr/bin/local:10:1: error: errMsg";
        assertEquals(expectedOutput, this.violationMessage.toString());
    }
}
