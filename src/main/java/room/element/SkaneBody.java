package room.element;

import room.Position;

public class SkaneBody extends Element {
    public SkaneBody(Position pos) {
        super(pos);
    }

    public SkaneBody(int x, int y) {
        this(new Position(x, y));
    }
}
