package com.sleekbyte.tailor.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for {@link Severity}
 */
@RunWith(MockitoJUnitRunner.class)
public class SeverityTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSeverityParserWithValidInputs() throws Severity.IllegalSeverityException {
        assertEquals(Severity.parseSeverity("error"), Severity.ERROR);
        assertEquals(Severity.parseSeverity("warning"), Severity.WARNING);
        assertEquals(Severity.parseSeverity("Error"), Severity.ERROR);
        assertEquals(Severity.parseSeverity("ERROR"), Severity.ERROR);
    }

    @Test(expected=Severity.IllegalSeverityException.class)
    public void testSeverityParserWithInvalidInputs() throws Severity.IllegalSeverityException {
        Severity.parseSeverity("invalid");
    }

    @Test
    public void testSeverityToString() {
        assertEquals(Severity.ERROR.toString(), Messages.ERROR);
        assertEquals(Severity.WARNING.toString(), Messages.WARNING);
    }

    @Test
    public void testSeverityMinimum() {
        assertEquals(Severity.min(Severity.ERROR, Severity.ERROR), Severity.ERROR);
        assertEquals(Severity.min(Severity.ERROR, Severity.WARNING), Severity.WARNING);
        assertEquals(Severity.min(Severity.WARNING, Severity.ERROR), Severity.WARNING);
    }

}
