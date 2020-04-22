package room.element;

import room.Position;

public class MeleeGuy extends EntityQueMorde {
    public MeleeGuy(Position pos, int hp, int atk) {
        super(pos, hp, atk);
    }

    public MeleeGuy(int x, int y, int hp, int atk) {
        this(new Position(x, y), hp, atk);
    }
}
