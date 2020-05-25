package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.Laser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LaserTests {
    private Laser laser;

    @Before
    public void setUp() {
        this.laser = new Laser(10, 10, 2, 2, 0);
    }

    @Test
    public void testLaserInit() {
        assertFalse(laser.getReadiness());
        assertEquals(laser.getDuration(), 2);
    }

    @Test
    public void testLaserReadiness() {
        laser.makeUnready();
        assertFalse(laser.getReadiness());

        laser.makeReady();
        assertTrue(laser.getReadiness());
        laser.makeReady();
        assertTrue(laser.getReadiness());

        laser.makeUnready();
        assertFalse(laser.getReadiness());
    }

    @Test
    public void testLaserDuration() {
        assertEquals(laser.getDuration(), 2);
        laser.tickLaser();
        assertEquals(laser.getDuration(), 1);
        laser.tickLaser();
        assertEquals(laser.getDuration(), 0);
        laser.tickLaser();
        assertEquals(laser.getDuration(), -1);
    }
}
