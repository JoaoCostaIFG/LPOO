package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.MeleeAtkStrat;
import org.g73.skanedweller.controller.creator.elements_creator.MeleeCreator;
import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeleeCreatorTests {
    private MeleeCreator meleeCreator;

    @Before
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
