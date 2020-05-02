package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;

import java.util.List;

public interface Movable {
    int getMovCounter();

    void setMovCounter(int numTicks);

    void tickMovCounter();

    void setMoveStrat(MoveStrategy moveStrat);

    List<Position> genMoves(Room r, Element e);
}
