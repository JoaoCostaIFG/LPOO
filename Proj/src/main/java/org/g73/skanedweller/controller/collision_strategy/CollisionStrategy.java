package org.g73.skanedweller.controller.collision_strategy;

import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.element_behaviours.Movable;

public abstract class CollisionStrategy<T extends Movable, S extends Collidable> {
    public abstract boolean handle(T movElem, S collElem);
}
