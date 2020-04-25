package room.element;

import room.Position;
import room.Room;

import java.util.List;

public interface MoveStrategy {
    List<Position> genMoves(Room r, Entity e);
}
