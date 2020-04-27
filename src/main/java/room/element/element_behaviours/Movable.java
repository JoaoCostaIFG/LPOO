package room.element.element_behaviours;

import room.Position;
import room.Room;
import room.element.Element;

import java.util.List;

public interface Movable {
    int getMovCounter();

    void setMovCounter(int numTicks);

    void tickMovCounter();

    void setMoveStrat(MoveStrategy moveStrat);

    List<Position> genMoves(Room r, Element e);
}
