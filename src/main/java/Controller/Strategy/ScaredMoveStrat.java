package Controller.Strategy;

import room.Position;
import room.Room;
import room.element.Entity;
import room.element.MoveStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScaredMoveStrat implements MoveStrategy {
    @Override
    public List<Position> execute(Room room, Entity e) {
        /*
         * Attempts to get as far away from the skane's mouth
         * as possible.
         */
        List<Position> finalPos = new ArrayList<>();
        if (room.isSkaneBury())
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
