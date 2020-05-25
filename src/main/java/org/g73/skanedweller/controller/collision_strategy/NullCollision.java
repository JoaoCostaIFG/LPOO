package org.g73.skanedweller.controller.collision_strategy;

import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.element_behaviours.Movable;

public class NullCollision extends CollisionStrategy {
    @Override
    public boolean handle(Movable movElem, Collidable collElem) {
        // Ignore collision and just set the position
        return true;
    }
}
