package room.element.element_behaviours;

import observe.Observer;
import room.Position;
import room.colliders.Collider;

public class NotCollidableBehaviour implements Collidable {
    @Override
    public Collider getCollider() {
        return null;
    }

    @Override
    public boolean collidesWith(Collidable element) {
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
