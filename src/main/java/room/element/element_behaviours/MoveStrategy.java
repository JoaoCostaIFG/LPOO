package room.element.element_behaviours;

import room.Position;
import room.Room;
import room.element.Element;

import java.util.List;

public interface MoveStrategy {
    List<Position> genMoves(Room r, Element e);
}
