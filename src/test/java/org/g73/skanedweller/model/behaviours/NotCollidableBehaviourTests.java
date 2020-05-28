package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.element_behaviours.NotCollidableBehaviour;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;


public class NotCollidableBehaviourTests {
    private NotCollidableBehaviour behaviour;

    @BeforeEach
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
