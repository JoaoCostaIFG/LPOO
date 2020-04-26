package room.element.skane;

import observe.Observer;
import room.Position;
import room.colliders.Collider;
import room.colliders.RectangleCollider;
import room.element.CollidableElement;
import room.element.Element;

public class SkaneBody extends Element implements CollidableElement {
    private Collider collider;

    public SkaneBody(Position pos) {
        super(pos);
        this.collider = new RectangleCollider(pos, 1, 1);
        this.addObserver(collider);
    }

    public SkaneBody(int x, int y) {
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
        this.collider.changed(subject);
    }

    @Override
    public RectangleCollider getCollider() {
        return (RectangleCollider) collider;
    }

    @Override
    public boolean collidesWith(CollidableElement element) {
        return this.collider.collidesWith(element.getCollider());
    }

    @Override
    public Position shadowStep(Position pos) {
        notifyObservers(pos);
        return this.getPos();
    }
}
