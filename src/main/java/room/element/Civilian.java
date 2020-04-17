package room.element;

import room.Position;

public class Civilian extends Entity {
    public Civilian(Position pos, Integer hp) {
        super(pos, hp);
    }

    public Civilian(Integer x, Integer y, Integer hp) {
        this(new Position(x, y), hp);
    }
}
