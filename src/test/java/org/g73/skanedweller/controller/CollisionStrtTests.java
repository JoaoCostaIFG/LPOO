package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.collision_strategy.BlockCollision;
import org.g73.skanedweller.controller.collision_strategy.NullCollision;
import org.g73.skanedweller.controller.collision_strategy.SkaneAttackCollision;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.element_behaviours.Movable;
import org.g73.skanedweller.model.element.skane.Skane;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

public class CollisionStrtTests {
    @Test
    public void testNullCollision() {
        NullCollision col = new NullCollision();
        Movable m = Mockito.mock(Movable.class);
        Collidable c = Mockito.mock(Collidable.class);
        
        assertTrue(col.handle(m, c));
    }

    @Test
    public void testBlockCollision() {
        BlockCollision col = new BlockCollision();
        Movable m = Mockito.mock(Movable.class);
        Collidable c = Mockito.mock(Collidable.class);

        assertFalse(col.handle(m, c));
    }
    
    @Test
    public void testSkaneCollision() {
        SkaneAttackCollision col = new SkaneAttackCollision();
        Skane s = Mockito.mock(Skane.class);
        Mockito.when(s.getAtk()).thenReturn(5);
        Element e = Mockito.mock(Element.class);
        Mockito.when(e.isAlive()).thenReturn(true);
        
        assertFalse(col.handle(s, e));
        Mockito.verify(e).takeDamage(eq(5));

        Mockito.when(e.isAlive()).thenReturn(false);
        assertTrue(col.handle(s, e));
        Mockito.verify(e, times(2)).takeDamage(eq(5));
        Mockito.verify(s).grow();
    }
}
