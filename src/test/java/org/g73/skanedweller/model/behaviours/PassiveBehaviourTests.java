package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.AttackStrategy;
import org.g73.skanedweller.model.element.element_behaviours.PassiveBehaviour;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PassiveBehaviourTests {
    private AttackStrategy strat;
    private Room room;
    private PassiveBehaviour behaviour;

    @BeforeEach
    public void setUp() {
        this.strat = Mockito.mock(AttackStrategy.class);
        this.room = Mockito.mock(Room.class);

        this.behaviour = new PassiveBehaviour();
    }

    @Test
    public void testPasBehavInit() {
        assertEquals(behaviour.getAtk(), 0);
        assertEquals(behaviour.getRange(), 0);

        behaviour.setAtk(999);
        assertEquals(behaviour.getAtk(), 0);

        behaviour.setRange(777);
        assertEquals(behaviour.getRange(), 0);
    }

    @Test
    public void testAtkCounter() {
        assertEquals(behaviour.getAtkCounter(), 1);

        behaviour.setAtkCounter(9999);
        assertEquals(behaviour.getAtkCounter(), 1);

        behaviour.tickAtkCounter();
        assertEquals(behaviour.getAtkCounter(), 1);
    }

    @Test
    public void testAtkStratDeleg() {
        Element me = Mockito.mock(Element.class);
        Element target = Mockito.mock(Element.class);

        behaviour.setAtkStrat(strat);

        Mockito.when(strat.attack(room, me, target))
                .thenReturn(true);
        assertFalse(behaviour.attack(room, me, target));

        Mockito.when(strat.attack(room, me, target))
                .thenReturn(false);
        assertFalse(behaviour.attack(room, me, target));
    }
}
