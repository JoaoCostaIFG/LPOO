package Controller.collisionStrategy;

import room.Position;
import room.element.CollidableElement;
import room.element.MovableElement;

public abstract class CollisionStrategy<T extends MovableElement, S extends CollidableElement> {
    public abstract boolean handle(T mov_ell, S coll_ell);
}
