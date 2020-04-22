package Controller.collisionStrategy;

import room.Position;
import room.element.CollidableElement;
import room.element.MovableElement;

public abstract class CollisionStrategy<T extends MovableElement, S extends CollidableElement> {
    protected T colliding_elem;

    public CollisionStrategy(T colliding_elem) {
        this.colliding_elem = colliding_elem;
    }

    public T getColliding_elem() {
        return colliding_elem;
    }

    public abstract void handle(S element, Position wanted_pos);
}
