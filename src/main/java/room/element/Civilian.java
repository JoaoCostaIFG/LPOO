package room.element;

import room.Position;

public class Civilian extends Entity {
    public Civilian(Position pos, Integer hp) {
        super(pos, hp);
    }

    public Civilian(int x, int y, int hp) {
        this(new Position(x, y), hp);
    }
}
