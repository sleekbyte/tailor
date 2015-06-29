package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Messages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link ViolationMessage}
 */
@RunWith(MockitoJUnitRunner.class)

public class ViolationMessageTest {

    @Before
    public void setUp() throws IOException {

    }

    @After
    public void tearDown() { }

    @Test
    public void testCompareTo() throws Exception { }

    @Test
    public void testEquals() throws Exception {
        ViolationMessage violationMessageOne = new ViolationMessage("/usr/bin/local", 10, 1, Messages.ERROR, "errMsg");

        // Equal ViolationMessage
        ViolationMessage unequalViolationMessage = new ViolationMessage("/usr/bin/local", 12, 5, Messages.ERROR, "errMsg");
        assertFalse(violationMessageOne.equals(unequalViolationMessage));

        // Unequal ViolationMessage
        ViolationMessage equalViolationMessage = new ViolationMessage("/usr/bin/local", 10, 1, Messages.ERROR, "errMsg");
        assertTrue(violationMessageOne.equals(equalViolationMessage));
    }

    @Test
    public void testToString() throws Exception {

    }
}
