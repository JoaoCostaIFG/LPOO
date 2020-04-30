package model.element.element_behaviours;

import observe.Observer;
import model.Position;
import model.colliders.Collider;

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
