package org.g73.skanedweller.model.colliders;

import org.g73.skanedweller.model.Position;

import java.util.ArrayList;
import java.util.List;

public class CompositeCollider extends Collider {
    private List<Collider> colliders;

    public CompositeCollider(Position pos, List<Collider> colliders) {
        super(pos);
        this.colliders = colliders;
    }

    public CompositeCollider(int x, int y, List<Collider> colliders) {
        this(new Position(x, y), colliders);
    }

    public CompositeCollider(Position pos) {
        this(pos, new ArrayList<Collider>());
    }

    public CompositeCollider(int x, int y) {
        this(new Position(x, y));
    }

    public void addCollider(Collider col) {
        int relativeX = col.getX() - this.getX();
        int relativeY = col.getY() - this.getY();
        col.setPos(new Position(relativeX, relativeY));
        colliders.add(col);
    }

    public List<Collider> getColliders() {
        return colliders;
    }

    public void removeCollider(Collider col) {
        colliders.remove(col);
    }

    @Override
    public boolean collidesWith(Collider col) {
        Position oldPos = col.getPos();
        int relativeX = col.getX() - this.getX();
        int relativeY = col.getY() - this.getY();
        col.setPos(new Position(relativeX, relativeY));

        for (Collider c : this.colliders) {
            if (c.collidesWith(col)) {
                col.setPos(oldPos);
                return true;
            }
        }
        col.setPos(oldPos);
        return false;
    }
}
