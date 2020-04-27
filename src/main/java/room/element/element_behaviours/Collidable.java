package room.element.element_behaviours;

import observe.Observable;
import room.Position;
import room.colliders.Collider;

public interface Collidable extends Observable<Position> {
    Collider getCollider();

    boolean collidesWith(Collidable element);

    Position shadowStep(Position pos);
}
