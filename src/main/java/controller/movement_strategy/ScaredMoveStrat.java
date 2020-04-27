package controller.movement_strategy;

import room.Position;
import room.Room;
import room.element.Element;
import room.element.element_behaviours.MoveStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScaredMoveStrat implements MoveStrategy {
    /*
     * Attempts to get as far away from the skane's mouth as possible.
     * Only runs away if it can see the Skane.
     */
    private int ticksBetweenMoves;

    public ScaredMoveStrat(int ticksBetweenMoves) {
        this.ticksBetweenMoves = ticksBetweenMoves;
    }

    private boolean canSeeSkane(Room room, Position s) {
        List<Element> skaHeadRay = room.raycast(s, room.getSkanePos());
        if (skaHeadRay.size() == 0 || !room.isSkanePos(skaHeadRay.get(0).getPos()))
            return false;

        return true;
    }

    @Override
    public List<Position> genMoves(Room room, Element e) {
        List<Position> finalPos = new ArrayList<>();

        e.setMovCounter(ticksBetweenMoves);
        if (room.isSkaneBury() || !canSeeSkane(room, e.getPos()))
            return finalPos;

        Position ska_pos = room.getSkanePos();
        Position a, b;
        double distA, distB;

        a = e.moveDown();
        b = e.moveUp();
        distA = a.dist(ska_pos);
        distB = b.dist(ska_pos);
        if (distA >= distB)
            finalPos.add(a);
        if (distA <= distB)
            finalPos.add(b);

        a = e.moveLeft();
        b = e.moveRight();
        distA = a.dist(ska_pos);
        distB = b.dist(ska_pos);
        if (distA >= distB)
            finalPos.add(a);
        if (distA <= distB)
            finalPos.add(b);

        finalPos.sort(Comparator.comparingDouble(lhs -> lhs.dist(ska_pos)));
        return finalPos;
    }
}
