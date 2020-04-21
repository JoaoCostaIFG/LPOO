package Controller.Strategy;

import room.Position;
import room.Room;
import room.element.Entity;
import room.element.MovableElement;
import room.element.MoveStrategy;

import java.util.*;

public class ScaredMoveStrat implements MoveStrategy {
    private Room room;

    public ScaredMoveStrat(Room room) {
        this.room = room;
    }

    private void sortListBySize(List<Position> l, Position p) {
        Collections.sort(l, new Comparator<Position>() {
            @Override
            public int compare(Position lhs, Position rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                double a = lhs.dist(p);
                double b = rhs.dist(p);
                if (a < b)
                    return -1;
                else if (a > b)
                    return 1;
                else
                    return 0;
            }
        });
    }

    @Override
    public List<Position> execute(Entity e) {
        /*
         * Attempts to get as far away from the skane's mouth
         * as possible
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

        sortListBySize(finalPos, ska_pos);
        return finalPos;
    }
}
