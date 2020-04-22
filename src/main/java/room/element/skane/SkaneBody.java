package room.element.skane;

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
    }

    public SkaneBody(int x, int y, Collider collider) {
        this(new Position(x, y));
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
