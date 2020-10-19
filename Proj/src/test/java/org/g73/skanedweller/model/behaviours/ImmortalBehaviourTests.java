package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.element.element_behaviours.ImmortalBehaviour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ImmortalBehaviourTests {
    private ImmortalBehaviour behaviour;

    @BeforeEach
    public void setUp() {
        this.behaviour = new ImmortalBehaviour();
    }

    @Test
    public void testImmortBehavInit() {
        assertEquals(behaviour.getHp(), 1);

        behaviour.setHp(999);
        assertEquals(behaviour.getHp(), 1);
    }

    @Test
    public void testDmgAndAlive() {
        behaviour.takeDamage(1);
        assertEquals(behaviour.getHp(),  1);
        assertTrue(behaviour.isAlive());

        behaviour.takeDamage(1);
        assertEquals(behaviour.getHp(), 1);
        assertTrue(behaviour.isAlive());
    }
}
