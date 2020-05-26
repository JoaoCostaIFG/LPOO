package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;

import java.util.ArrayList;
import java.util.List;

public class ImovableBehaviour implements Movable{
    @Override
    public int getMovCounter() {
        return 1;
    }

    @Override
    public void setMovCounter(int numTicks) {
    }

    @Override
    public void tickMovCounter() {
    }

    @Override
    public void setMoveStrat(MoveStrategy moveStrat) {
    }

    @Override
    public MoveStrategy getMoveStrat() {
        return null;
    }

    @Override
    public List<Position> genMoves(Room r, Element e) {
        return new ArrayList<>();
    }
}
