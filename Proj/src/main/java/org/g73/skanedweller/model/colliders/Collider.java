package org.g73.skanedweller.model.colliders;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.observe.Observer;

public abstract class Collider implements Observer<Position> {
    private Position position;

    public Collider(Position pos) {
        this.position = pos;
    }

    public Collider(int x, int y) {
        this(new Position(x, y));
    }

    public void setPos(Position newPos) {
        this.position = newPos;
    }

    public Position getPos() {
        return this.position;
    }

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    public abstract boolean collidesWith(Collider col);

    @Override
    public void changed(Position observable) {
        setPos(observable);
    }
}
