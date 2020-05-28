package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.AgressiveBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.AttackStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


public class AgressiveBehaviourTests {
    private final int atk = 3;
    private final  int range = 5;
    private final  int atkCounter = 2;

    private AttackStrategy strat;
    private Room room;
    private AgressiveBehaviour behaviour;

    @BeforeEach
    public void setUp() {
        this.strat = Mockito.mock(AttackStrategy.class);
        this.room = Mockito.mock(Room.class);

        this.behaviour = new AgressiveBehaviour(atk, range);
    }

    @Test
    public void testAgrBehavInit() {
        assertEquals(behaviour.getAtk(), atk);
        assertEquals(behaviour.getRange(), range);

        behaviour.setAtk(999);
        assertEquals(behaviour.getAtk(), 999);

        behaviour.setRange(777);
        assertEquals(behaviour.getRange(), 777);
    }

    @Test
    public void testAtkCounter() {
        assertEquals(behaviour.getAtkCounter(), 0);

        behaviour.setAtkCounter(atkCounter);
        assertEquals(behaviour.getAtkCounter(), atkCounter);

        behaviour.tickAtkCounter();
        assertEquals(behaviour.getAtkCounter(), 1);

        behaviour.tickAtkCounter();
        assertEquals(behaviour.getAtkCounter(), 0);

        behaviour.tickAtkCounter();
        assertEquals(behaviour.getAtkCounter(), -1);
    }

    @Test
    public void testAtkStratDeleg() {
        Element me = Mockito.mock(Element.class);
        Element target = Mockito.mock(Element.class);

        behaviour.setAtkStrat(strat);

        Mockito.when(strat.attack(room, me, target))
                .thenReturn(true);
        assertTrue(behaviour.attack(room, me, target));

        Mockito.when(strat.attack(room, me, target))
                .thenReturn(false);
        assertFalse(behaviour.attack(room, me, target));
    }
}
