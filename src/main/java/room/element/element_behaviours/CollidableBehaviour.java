package room.element.element_behaviours;

import observe.Observer;
import room.Position;
import room.colliders.Collider;

public class CollidableBehaviour implements Collidable{
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
