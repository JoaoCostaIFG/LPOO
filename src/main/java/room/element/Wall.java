package room.element;

import room.Position;

public class Wall extends Element {
    public Wall(Position pos) {
        super(pos);
    }

    public Wall(int x, int y) {
        this(new Position(x, y));
    }
}
