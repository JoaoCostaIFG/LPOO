package room.colliders;

import observe.Observer;
import room.Position;
import room.element.Element;

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
