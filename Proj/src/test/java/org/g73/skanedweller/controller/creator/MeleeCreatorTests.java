package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.MeleeAtkStrat;
import org.g73.skanedweller.controller.creator.elements_creator.MeleeCreator;
import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeleeCreatorTests {
    private MeleeCreator meleeCreator;

    @BeforeEach
    public void setUp() {
        meleeCreator = new MeleeCreator(1, 1, 1);
    }

    @Test
    public void create() {
        Position pos = new Position(1, 1);
        MeleeGuy melee = meleeCreator.create(pos);
        assertEquals(1, melee.getAtk());
        assertEquals(1, melee.getX());
        assertEquals(1, melee.getY());
        assertTrue(melee.getAtkStrat() instanceof MeleeAtkStrat);
        assertTrue(melee.getMoveStrat() instanceof MeleeMoveStrat);
    }
}
