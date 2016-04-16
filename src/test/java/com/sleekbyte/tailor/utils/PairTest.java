package com.sleekbyte.tailor.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link Pair}.
 */
@RunWith(MockitoJUnitRunner.class)
public final class PairTest {

    Pair pair;

    @Before
    public void setUp() {
        pair = new Pair<>("Left", "Right");
    }

    @Test
    public void testUnequalPairs() {
        Pair candidatePair = new Pair<>(1, 2);
        assertFalse(pair.equals(candidatePair));
    }

    @Test
    public void testEqualPairs() {
        Pair candidatePair = new Pair<>("Left", "Right");
        assertEquals(pair, candidatePair);
    }

}
