package org.g73.skanedweller.model;

import org.g73.skanedweller.model.colliders.Collider;
import org.g73.skanedweller.model.colliders.CompositeCollider;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class ColliderTests {
    @Test
    public void testRectangleCollider() { // Hehe ca pt de sustoo
        RectangleCollider r1 = new RectangleCollider(10, 10, 10, 10);
        RectangleCollider r2 = new RectangleCollider(11, 11, 1, 1);
        assertTrue(r1.collidesWith(r1));
        assertTrue(r2.collidesWith(r2));

        assertTrue(r1.collidesWith(r2));
        assertTrue(r2.collidesWith(r1));

        r1.setHeight(1);
        r1.setWidth(1);
        assertFalse(r1.collidesWith(r2));
        assertFalse(r2.collidesWith(r1));

        r1.setPos(new Position(11, 10));
        r1.setHeight(2);
        assertTrue(r1.collidesWith(r2));
        assertTrue(r2.collidesWith(r1));

        r1.setPos(new Position(10, 11));
        r1.setWidth(2);
        r1.setHeight(1);
        assertTrue(r1.collidesWith(r2));
        assertTrue(r2.collidesWith(r1));
    }

    @Test
    public void testRectangleColliderEq() { // Hehe ca pt de sustoo
        RectangleCollider r1 = new RectangleCollider(10, 10, 10, 10);
        RectangleCollider r2 = new RectangleCollider(9, 9, 1, 1);
        assertTrue(r1.collidesWith(r1));
        assertTrue(r2.collidesWith(r2));
        assertFalse(r1.collidesWith(r2));
        assertFalse(r2.collidesWith(r1));
    }

    @Test
    public void testCompositeCollider() {
        CompositeCollider c1 = new CompositeCollider(1, 1);
        RectangleCollider r1 = new RectangleCollider(1, 1, 1, 1);
        RectangleCollider r2 = new RectangleCollider(2, 1, 1, 1);
        RectangleCollider r3 = new RectangleCollider(10, 2, 1, 1);
        RectangleCollider r4 = new RectangleCollider(11, 2, 1, 1);
        c1.addCollider(r1);
        c1.addCollider(r2);
        c1.addCollider(r3);
        c1.addCollider(r4);

        assertEquals(c1.getColliders(), new ArrayList<Collider>(Arrays.asList(r1, r2, r3, r4)));

        RectangleCollider rect = Mockito.spy(new RectangleCollider(9, 2, 5, 1));
        assertTrue(rect.collidesWith(c1));
        Mockito.verify(rect).setPos(new Position(8, 1));
        Mockito.verify(rect).setPos(new Position(9, 2));

        Mockito.reset(rect);
        assertTrue(c1.collidesWith(rect));
        Mockito.verify(rect).setPos(new Position(8, 1));
        Mockito.verify(rect).setPos(new Position(9, 2));

        Mockito.reset(rect);
        rect.setPos(new Position(3, 1));
        assertFalse(rect.collidesWith(c1));
        assertFalse(c1.collidesWith(rect));
    }
}
