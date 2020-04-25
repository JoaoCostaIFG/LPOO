package room.colliders;

import room.Position;

public class RectangleCollider extends Collider {
    private int width, height;

    public RectangleCollider(Position pos, int width, int height) {
        super(pos);
        this.width = width;
        this.height = height;
    }

    public RectangleCollider(int x, int y, int width, int height) {
        this(new Position(x, y), width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private boolean collidesWith(RectangleCollider rect2) {
        int rect1X = super.getX();
        int rect2X = rect2.getX();
        int rect1Y = super.getY();
        int rect2Y = rect2.getY();
        return  (rect1X < rect2X + rect2.getWidth()&&
                rect1X + this.width > rect2X &&
                rect1Y < rect2Y + rect2.getHeight() &&
                rect1Y + this.height > rect2Y);
    }

    private boolean collidesWith(CompositeCollider comp1) {
        return comp1.collidesWith(this);
    }

    @Override
    public boolean collidesWith(Collider col) {
        if (col instanceof RectangleCollider)
            return collidesWith((RectangleCollider) col);
        else if (col instanceof CompositeCollider)
            return collidesWith((CompositeCollider) col);

        return false;
    }
}
