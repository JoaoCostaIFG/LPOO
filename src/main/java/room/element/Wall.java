package room.element;

import observe.Observer;
import room.Position;
import room.colliders.Collider;
import room.colliders.RectangleCollider;

// TODO Code smell, repeated code with Skane Body and Entity
public class Wall extends Element implements CollidableElement {
    private Collider collider;

    public Wall(Position pos) {
        super(pos);
        this.collider = new RectangleCollider(pos, 1, 1);
    }

    public Wall(int x, int y) {
        this(new Position(x, y));
    }

    @Override
    public void setPos(Position pos) {
        super.setPos(pos);
        notifyObservers(pos);
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
        this.collider.changed(this.getPos());
    }

    @Override
    public RectangleCollider getCollider() {
        return (RectangleCollider) collider;
    }

    @Override
    public boolean collidesWith(CollidableElement element) {
        return this.collider.collidesWith(element.getCollider());
    }
}
