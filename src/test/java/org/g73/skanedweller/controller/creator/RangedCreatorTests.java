package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.RangedGuy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RangedCreatorTests {
    private RangedCreator rangedCreator;

    @Before
    public void setUp() {
        rangedCreator = new RangedCreator(1, 1, 1);
    }

    @Test
    public void create() {
        Position pos = new Position(1, 1);
        RangedGuy ranged = rangedCreator.create(pos);
        assertEquals(1, ranged.getAtk());
        assertEquals(1, ranged.getX());
        assertEquals(1, ranged.getY());
    }
}
