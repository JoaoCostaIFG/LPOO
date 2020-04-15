package arena.element;

import arena.Position;

public abstract class Element {
    private Position pos;

    public Element(int x, int y) {
        this.pos = new Position(x, y);
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
}
