package room.element;

import room.colliders.Collider;

public interface CollidableElement {
    public Collider getCollider();

    public abstract boolean collidesWith(CollidableElement element);
}
