package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.creator.elements_creator.CivieCreator;
import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CivCreatorTests {
    private CivieCreator civCreator;

    @Before
    public void setUp() {
        civCreator = new CivieCreator();
    }

    @Test
    public void create() {
        Position pos = new Position(1, 1);
        Civilian civ = civCreator.create(pos);
        assertEquals(pos, civ.getPos());
        assertTrue(civ.getMoveStrat() instanceof ScaredMoveStrat);
    }
}
