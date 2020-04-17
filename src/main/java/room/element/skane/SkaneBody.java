package room.element.skane;

import room.Position;
import room.element.Element;

public class SkaneBody extends Element {
    public SkaneBody(Position pos) {
        super(pos);
    }

    public SkaneBody(int x, int y) {
        this(new Position(x, y));
    }
}
