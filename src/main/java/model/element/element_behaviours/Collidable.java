package model.element.element_behaviours;

import observe.Observable;
import model.Position;
import model.colliders.Collider;

public interface Collidable extends Observable<Position> {
    Collider getCollider();

    boolean collidesWith(Collidable colidee);

    Position shadowStep(Position pos);
}
