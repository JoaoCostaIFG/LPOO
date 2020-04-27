package controller.collision_strategy;

import room.element.element_behaviours.Collidable;
import room.element.element_behaviours.Movable;

public class BlockCollision extends CollisionStrategy<Movable, Collidable> {
    @Override
    public boolean handle(Movable mov_ell, Collidable coll_ell) {
        return false;
    }
}
