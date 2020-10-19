package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.creator.elements_creator.CivieCreator;
import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Civilian;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CivCreatorTests {
    private CivieCreator civCreator;

    @BeforeEach
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
