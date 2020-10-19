package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.RangedGuyAtkStrat;
import org.g73.skanedweller.controller.creator.elements_creator.RangedCreator;
import org.g73.skanedweller.controller.movement_strategy.RangedMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.RangedGuy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangedCreatorTests {
    private RangedCreator rangedCreator;

    @BeforeEach
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

        assertTrue(ranged.getMoveStrat() instanceof RangedMoveStrat);
        assertTrue(ranged.getAtkStrat() instanceof RangedGuyAtkStrat);
        assertEquals(60, ranged.getAtkCounter());
    }
}
