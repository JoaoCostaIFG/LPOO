package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.collision_strategy.BlockCollision;
import org.g73.skanedweller.controller.collision_strategy.NullCollision;
import org.g73.skanedweller.controller.collision_strategy.SkaneAttackCollision;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.element_behaviours.Movable;
import org.g73.skanedweller.model.element.skane.Skane;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Mockito.when(s.getAtk())
                .thenReturn(5);
        Element e = Mockito.mock(Element.class);
        Mockito.when(e.isAlive())
                .thenReturn(true);
        
        assertFalse(col.handle(s, e));
        Mockito.verify(e).takeDamage(5);

        Mockito.reset(e);
        Mockito.when(e.isAlive())
                .thenReturn(false);
        Mockito.when(s.getHp())
                .thenReturn(1);
        assertTrue(col.handle(s, e));
        Mockito.verify(e).takeDamage(5);
        Mockito.verify(s).grow();
        Mockito.verify(s).setHp(2);
    }
}
