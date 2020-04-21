package room.element;

import room.Position;

import java.util.List;

public class Civilian extends Entity {
    private MoveStrategy strategy = null;

    public Civilian(Position pos, Integer hp) {
        super(pos, hp);
    }

    public Civilian(int x, int y, int hp) {
        this(new Position(x, y), hp);
    }

    public void setStrategy(MoveStrategy strat) {
        this.strategy = strat;
    }

    public List<Position> executeStrategy() {
        return strategy.execute(this);
    }
}
