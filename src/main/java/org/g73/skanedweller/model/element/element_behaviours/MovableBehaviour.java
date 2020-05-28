package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;

import java.util.List;

public class MovableBehaviour implements Movable {
    private MoveStrategy moveStrat;
    private int movCounter = 0;

    @Override
    public int getMovCounter() {
        return this.movCounter;
    }

    @Override
    public void setMovCounter(int numTicks) {
        this.movCounter = numTicks;
    }

    @Override
    public void tickMovCounter() {
        --this.movCounter;
    }

    @Override
    public MoveStrategy getMoveStrat() {
        return this.moveStrat;
    }

    @Override
    public void setMoveStrat(MoveStrategy moveStrat) {
        this.moveStrat = moveStrat;
    }

    @Override
    public List<Position> genMoves(Room r, Element e) {
        return moveStrat.genMoves(r, e);
    }
}
