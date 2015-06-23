package com.sleekbyte.tailor.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link CharFormatUtil}
 */
@RunWith(MockitoJUnitRunner.class)
public class CharFormatUtilTest {

    @Test
    public void testUpperCamelCaseInvalidWord() {
        assertFalse(CharFormatUtil.isUpperCamelCase("helloWorld"));
        assertFalse(CharFormatUtil.isUpperCamelCase(""));
        assertFalse(CharFormatUtil.isUpperCamelCase("Hello_World"));
        assertFalse(CharFormatUtil.isUpperCamelCase("1ello_world"));
        assertFalse(CharFormatUtil.isUpperCamelCase("!ello_world"));
    }

    @Test
    public void testUpperCamelCaseValidWord() {
        assertTrue(CharFormatUtil.isUpperCamelCase("HelloWorld"));
    }

    @Test
    public void testLowerCamelCaseInvalidWord() {
        assertFalse(CharFormatUtil.isLowerCamelCase("HelloWorld"));
        assertFalse(CharFormatUtil.isLowerCamelCase(""));
        assertFalse(CharFormatUtil.isLowerCamelCase("hello_World"));
        assertFalse(CharFormatUtil.isLowerCamelCase("1ello_world"));
        assertFalse(CharFormatUtil.isLowerCamelCase("$elloWorld"));
    }

    @Test
    public void testLowerCamelCaseValidWord() {
        assertTrue(CharFormatUtil.isLowerCamelCase("helloWorld"));
    }

}
