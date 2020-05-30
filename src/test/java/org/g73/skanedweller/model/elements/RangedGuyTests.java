package org.g73.skanedweller.model.elements;

import org.g73.skanedweller.controller.attack_strategy.RangedGuyAtkStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Laser;
import org.g73.skanedweller.model.element.RangedGuy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangedGuyTests {
    @Test
    public void testRangedGuyShooting() {
        RangedGuy rg = new RangedGuy(10, 10, 2, 2, 5);
        assertTrue(rg.getLasers().isEmpty());

        RangedGuyAtkStrat atkStrat = Mockito.mock(RangedGuyAtkStrat.class);

        Laser l = rg.shoot(new Position(1, 1), 3, 1, atkStrat);
        assertEquals(rg.getLasers(), new ArrayList<>(Collections.singletonList(l)));
        assertEquals(l.getAtkStrat(), atkStrat);
    }
}
