package controller.collision_strategy;

import room.element.element_behaviours.Collidable;
import room.element.element_behaviours.Movable;

public abstract class CollisionStrategy<T extends Movable, S extends Collidable> {
    public abstract boolean handle(T mov_ell, S coll_ell);
}
