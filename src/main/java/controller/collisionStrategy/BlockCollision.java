package controller.collisionStrategy;

import room.element.CollidableElement;
import room.element.MovableElement;

public class BlockCollision extends CollisionStrategy<MovableElement, CollidableElement> {
    @Override
    public boolean handle(MovableElement mov_ell, CollidableElement coll_ell) {
        return false;
    }
}
