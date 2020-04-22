package room.element;

import room.Position;
import room.colliders.Collider;
import room.colliders.RectangleCollider;

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
    public RectangleCollider getCollider() {
        return (RectangleCollider) collider;
    }

    @Override
    public boolean collidesWith(CollidableElement element) {
        return this.collider.collidesWith(element.getCollider());
    }
}
