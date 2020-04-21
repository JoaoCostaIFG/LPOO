package Controller.Strategy;

import room.Position;
import room.Room;
import room.element.Entity;
import room.element.MovableElement;
import room.element.MoveStrategy;
import room.element.skane.Skane;

import java.util.ArrayList;
import java.util.List;

public class MeleeMoveStrat implements MoveStrategy {
    private class PosDist {
        public Position p;
        public double dist;

        public PosDist(Position p, double dist) {
            this.p = p;
            this.dist = dist;
        }
    }

    private Room room;

    public MeleeMoveStrat(Room room) {
        this.room = room;
    }

    // TODO THIS IS NOT FINISHED
    @Override
    public List<Position> execute(Entity e) {
        /*
         * Attempts to get to the first part of the skane it sees
         * or follow some scent.
         */
        List<Position> finalPos = new ArrayList<>();
        if (room.isSkaneBury())
            return finalPos;

        Skane ska = room.getSkane();
        List<PosDist> listPos = new ArrayList<>();
        //if (room.raycast(e.getPos(),))

        return finalPos;
    }
}
