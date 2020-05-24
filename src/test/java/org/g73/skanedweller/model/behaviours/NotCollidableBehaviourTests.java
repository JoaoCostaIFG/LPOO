package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.element_behaviours.NotCollidableBehaviour;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class NotCollidableBehaviourTests {
    private NotCollidableBehaviour behaviour;

    @Before
    public void setUp() {
        this.behaviour = new NotCollidableBehaviour();
    }

    @Test
    public void testNotColBehavInit() {
        assertNull(behaviour.getCollider());
    }

    @Test
    public void testNotColCollisions() {
        assertFalse(behaviour.collidesWith(Mockito.mock(Collidable.class)));
        assertNull(behaviour.shadowStep(Mockito.mock(Position.class)));
    }
}
