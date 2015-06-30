package com.sleekbyte.tailor.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Test
    public void testKPrefixedInvalidVariableNamesStartingWithK() {
        assertTrue(CharFormatUtil.isKPrefixed("KBadConstantName"));
        assertTrue(CharFormatUtil.isKPrefixed("kBadConstantName"));
    }

    @Test
    public void testKPrefixedValidVariableNamesStartingWithK() {
        assertFalse(CharFormatUtil.isKPrefixed("koalasEatKale"));
        assertFalse(CharFormatUtil.isKPrefixed("KoalasEatKale"));
        assertFalse(CharFormatUtil.isKPrefixed("k"));
    }

    @Test
    public void testKPrefixedVariableNamesNotInCamelCase() {
        assertFalse(CharFormatUtil.isKPrefixed("k_valid_because_not_camel_case"));
        assertFalse(CharFormatUtil.isKPrefixed("K_valid_because_not_camel_case"));
    }

    @Test
    public void testKPrefixedVariableNamesNotStartingWithK() {
        assertFalse(CharFormatUtil.isKPrefixed("validConstantName"));
        assertFalse(CharFormatUtil.isKPrefixed("AlsoValidConstantName"));
    }

}
