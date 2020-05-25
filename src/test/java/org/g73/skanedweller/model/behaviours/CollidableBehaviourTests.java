package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.Collider;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.element_behaviours.CollidableBehaviour;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class CollidableBehaviourTests {
    private Collider col;
    private CollidableBehaviour behaviour;

    @Before
    public void setUp() {
        this.col = Mockito.mock(Collider.class);
        this.behaviour = new CollidableBehaviour(col);
    }

    @Test
    public void testColBehavInit() {
        assertEquals(behaviour.getCollider(), this.col);
    }

    @Test
    public void testColCollisions() {
        Collidable collidable = Mockito.mock(Collidable.class);
        Mockito.when(collidable.getCollider())
                .thenReturn(Mockito.mock(Collider.class));

        Mockito.when(col.collidesWith(any(Collider.class)))
                .thenReturn(false);
        assertFalse(behaviour.collidesWith(collidable));

        Mockito.when(col.collidesWith(any(Collider.class)))
                .thenReturn(true);
        assertTrue(behaviour.collidesWith(collidable));
    }

    @Test
    public void testObs() {
        Collider col2 = Mockito.mock(Collider.class);
        assertNotEquals(behaviour.getCollider(), col2);

        behaviour.addObserver(col2);
        assertEquals(behaviour.getCollider(), col2);

        behaviour.removeObserver(col2);
        assertNull(behaviour.getCollider());
    }

    @Test
    public void testShadowStep() {
        Position p = new Position(1, 1);
        Position pRet = new Position(2, 2);

        Mockito.when(col.getPos())
                .thenReturn(pRet);
        assertEquals(behaviour.shadowStep(p), pRet);

        Mockito.verify(col, times(1)).changed(p);
    }
}
