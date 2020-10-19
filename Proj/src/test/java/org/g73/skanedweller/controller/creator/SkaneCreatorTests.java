package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.creator.elements_creator.SkaneCreator;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.skane.Skane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SkaneCreatorTests {
    private SkaneCreator skaCreator;

    @BeforeEach
    public void setUp() {
        skaCreator = new SkaneCreator(1, 1, 1, 1);
    }

    @Test
    public void create() {
        Position pos = new Position(1, 1);
        Skane ska = skaCreator.create(pos);
        assertEquals(1, ska.getMaxOxygenLevel());
        assertEquals(1, ska.getOxygenLevel());
        assertEquals(1, ska.getSize());
        assertEquals(1, ska.getAtk());
        assertEquals(1, ska.getX());
        assertEquals(1, ska.getY());
    }
}
