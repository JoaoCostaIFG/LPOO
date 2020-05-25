package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.element.element_behaviours.ImmortalBehaviour;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImmortalBehaviourTests {
    private ImmortalBehaviour behaviour;

    @Before
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
