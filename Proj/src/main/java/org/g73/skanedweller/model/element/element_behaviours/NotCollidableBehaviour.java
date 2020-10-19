package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.Collider;
import org.g73.skanedweller.observe.Observer;

public class NotCollidableBehaviour implements Collidable {
    @Override
    public Collider getCollider() {
        return null;
    }

    @Override
    public boolean collidesWith(Collidable colidee) {
        return false;
    }

    @Override
    public Position shadowStep(Position pos) {
        return null;
    }

    @Override
    public void addObserver(Observer<Position> observer) {
    }

    @Override
    public void removeObserver(Observer<Position> observer) {
    }

    @Override
    public void notifyObservers(Position subject) {
    }
}
