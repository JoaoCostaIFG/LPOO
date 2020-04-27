package room.element.element_behaviours;

import room.Position;
import room.Room;
import room.element.Element;

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
    public void setMoveStrat(MoveStrategy moveStrat) {
        this.moveStrat = moveStrat;
    }

    @Override
    public List<Position> genMoves(Room r, Element e) {
        return moveStrat.genMoves(r, e);
    }
}
