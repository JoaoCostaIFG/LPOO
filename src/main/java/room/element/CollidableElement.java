package room.element;

import observe.Observable;
import observe.Observer;
import room.Position;
import room.colliders.Collider;

public interface CollidableElement extends Observable<Position> {
    Collider getCollider();

    boolean collidesWith(CollidableElement element);

    Position shadowStep(Position pos);
}
