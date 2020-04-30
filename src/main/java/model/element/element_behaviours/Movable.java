package model.element.element_behaviours;

import model.Position;
import model.Room;
import model.element.Element;

import java.util.List;

public interface Movable {
    int getMovCounter();

    void setMovCounter(int numTicks);

    void tickMovCounter();

    void setMoveStrat(MoveStrategy moveStrat);

    List<Position> genMoves(Room r, Element e);
}
