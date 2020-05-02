package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.Collider;
import org.g73.skanedweller.observe.Observable;

public interface Collidable extends Observable<Position> {
    Collider getCollider();

    boolean collidesWith(Collidable colidee);

    Position shadowStep(Position pos);
}
