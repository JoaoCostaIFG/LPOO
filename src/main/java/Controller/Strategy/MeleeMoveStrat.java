package Controller.Strategy;

import room.Position;
import room.Room;
import room.element.MovableElement;
import room.element.MoveStrategy;

import java.util.List;

public class MeleeMoveStrat implements MoveStrategy {
    private Room room;

    public MeleeMoveStrat(Room room) {
        this.room = room;
    }

    @Override
    public List<Position> execute(MovableElement e) {
        return null;
    }
}
