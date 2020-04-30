package model.element.element_behaviours;

import model.Position;
import model.Room;
import model.element.Element;

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
    public List<Position> genMoves(Room r, Element e) {
        return new ArrayList<>();
    }
}
