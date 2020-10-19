package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.element.element_behaviours.MortalBehaviour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MortalBehaviourTests {
    private final int hp = 2;

    private MortalBehaviour behaviour;

    @BeforeEach
    public void setUp() {
        this.behaviour = new MortalBehaviour(hp);
    }

    @Test
    public void testMortBehavInit() {
        assertEquals(behaviour.getHp(), hp);

        behaviour.setHp(999);
        assertEquals(behaviour.getHp(), 999);
    }

    @Test
    public void testDmgAndAlive() {
        behaviour.takeDamage(1);
        assertEquals(behaviour.getHp(), hp - 1);
        assertTrue(behaviour.isAlive());

        behaviour.takeDamage(hp);
        assertEquals(behaviour.getHp(), 0);
        assertFalse(behaviour.isAlive());

        behaviour.takeDamage(hp);
        assertEquals(behaviour.getHp(), 0);
        assertFalse(behaviour.isAlive());
    }
}
