package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.skane.Scent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScentTests {
    private final int initx = 20;
    private final int inity = 20;
    private final int initDur = 2;
    private Scent s;

    @Before
    public void setUp() {
        s = new Scent(initx, inity, initDur);
    }

    @Test
    public void testEquals() {
        assertEquals(s, s);
        assertNotEquals(s, null);
        assertNotEquals(s, new Object());

        /* testing symmetry */
        Scent s2 = new Scent(initx, inity, initDur);
        assertTrue(s.equals(s2) && s2.equals(s));
    }

    @Test
    public void testHashCode() {
        Scent s2 = new Scent(initx, inity, initDur);
        /* == for ints is already symmetric */
        assertTrue(s.hashCode() == s2.hashCode());
    }
}
