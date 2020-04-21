package room.element;

import room.Position;
import room.colliders.Collider;
import room.colliders.RectangleCollider;

public abstract class Element {
    private Collider collider;
    private Position pos;

    public Element(Position pos) {
        this.pos = pos;
        // FIXME todos os elements por default de width e height 1?
        this.collider = new RectangleCollider(pos, 1, 1);
    }

    public Element(Position pos, Collider col) {
        this(pos);
        this.collider = col;
    }

    public Element(int x, int y) {
        this(new Position(x, y));
    }

    public int getX() {
        return this.pos.getX();
    }

    public void setX(int x) {
        this.pos.setX(x);
    }

    public int getY() {
        return this.pos.getY();
    }

    public void setY(int y) {
        this.pos.setY(y);
    }

    public Position getPos() {
        return this.pos;
    }

    public void setPos(Position position) {
        this.pos = position;
    }

    public Collider getCollider() { return this.collider; }

    public boolean collidesWith(Element element) {
        return this.collider.collidesWith(element.getCollider());
    }
}
