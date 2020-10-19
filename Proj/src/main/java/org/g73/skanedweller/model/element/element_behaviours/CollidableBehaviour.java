package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.Collider;
import org.g73.skanedweller.observe.Observer;

public class CollidableBehaviour implements Collidable {
    private Collider collider;

    public CollidableBehaviour(Collider col) {
        this.collider = col;
    }

    @Override
    public Collider getCollider() {
        return this.collider;
    }

    @Override
    public boolean collidesWith(Collidable colidee) {
        return collider.collidesWith(colidee.getCollider());
    }

    @Override
    public Position shadowStep(Position pos) {
        Position retPos = collider.getPos();
        notifyObservers(pos);
        return retPos;
    }

    @Override
    public void addObserver(Observer<Position> observer) {
        this.collider = (Collider) observer;
    }

    @Override
    public void removeObserver(Observer<Position> observer) {
        this.collider = null;
    }

    @Override
    public void notifyObservers(Position subject) {
        this.collider.changed(subject);
    }
}
