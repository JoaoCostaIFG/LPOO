package org.g73.skanedweller.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class PositionTests {
    private final int initx = 5;
    private final int inity = 5;
    private Position p;

    @Before
    public void setUp() {
        this.p = new Position(initx, inity);
    }

    @Test
    public void testPositionInit() {
        assertEquals(p.getX(), initx);
        assertEquals(p.getY(), inity);

        Position p2 = new Position();
        assertEquals(p2.getX(), 0);
        assertEquals(p2.getY(), 0);
    }

    @Test
    public void testPositionSetters() {
        p.setX(1);
        assertEquals(p.getX(), 1);
        p.setX(0);
        assertEquals(p.getX(), 0);
        p.setX(-1);
        assertEquals(p.getX(), -1);

        p.setY(1);
        assertEquals(p.getY(), 1);
        p.setY(0);
        assertEquals(p.getY(), 0);
        p.setY(-1);
        assertEquals(p.getY(), -1);
    }

    @Test
    public void testEqualsHashCode() {
        assertEquals(p, p);
        assertNotEquals(p, null);
        assertNotEquals(p, new Object());

        Position p2 = new Position(initx, inity);
        assertEquals(p.hashCode(), Objects.hash(initx, inity));
        assertTrue(p.equals(p2) && p2.equals(p));
        assertTrue(p.hashCode() == p2.hashCode());
    }

    @Test
    public void testPositionToString() {
        assertEquals(p.toString(), "Position{x=" + initx + ", y=" + inity + '}');
    }
}
