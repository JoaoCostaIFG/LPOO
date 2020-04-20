package room.element;

import room.Position;

public class MeleeGuy extends EntityQueMorde {
    MoveStrategy strategy;

    public MeleeGuy(Position pos, int hp, int atk) {
        super(pos, hp, atk);
    }

    public MeleeGuy(int x, int y, int hp, int atk) {
        this(new Position(x, y), hp, atk);
    }

    void setStrategy(MoveStrategy strat) {
        this.strategy = strat;
    }
}
