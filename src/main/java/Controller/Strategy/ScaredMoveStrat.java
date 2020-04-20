package Controller.Strategy;

import room.Position;
import room.Room;
import room.element.MovableElement;
import room.element.MoveStrategy;

import java.lang.reflect.Array;
import java.util.*;

public class ScaredMoveStrat implements MoveStrategy {
    private Room room;

    public ScaredMoveStrat(Room room) {
        this.room = room;
    }

    @Override
    public List<Position> execute(MovableElement e) {
        /*
         * Attempts to get as far away from the skane's mouth
         * as possible
         */
        Position ska_pos = room.getSkanePos();
        List<Position> final_pos = new ArrayList<>();

        Position a, b;
        double dist_a, dist_b;

        a = e.moveDown();
        b = e.moveUp();
        dist_a = a.dist(ska_pos);
        dist_b = b.dist(ska_pos);
        if (dist_a > dist_b) {
            final_pos.add(a);
        } else if (dist_a < dist_b) {
            final_pos.add(b);
        } else {
            final_pos.add(a);
            final_pos.add(b);
        }

        a = e.moveLeft();
        b = e.moveRight();
        dist_a = a.dist(ska_pos);
        dist_b = b.dist(ska_pos);
        if (dist_a > dist_b) {
            final_pos.add(a);
        } else if (dist_a < dist_b) {
            final_pos.add(b);
        } else {
            final_pos.add(a);
            final_pos.add(b);
        }

        Collections.sort(final_pos, new Comparator<Position>() {
            @Override
            public int compare(Position lhs, Position rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                double a = lhs.dist(ska_pos);
                double b = rhs.dist(ska_pos);
                if (a < b)
                    return -1;
                else if (a > b)
                    return 1;
                else
                    return 0;
            }
        });

        return final_pos;
    }
}
