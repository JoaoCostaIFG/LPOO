package org.g73.skanedweller.model.colliders;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.observe.Observer;

public abstract class Collider extends Element implements Observer<Position> {
    private Position position;

    public Collider(Position pos) {
        super(pos);
    }

    public Collider(int x, int y) {
        super(x, y);
    }

    public abstract boolean collidesWith(Collider col);

    @Override
    public void changed(Position observable) {
        setPos(observable);
    }
}
