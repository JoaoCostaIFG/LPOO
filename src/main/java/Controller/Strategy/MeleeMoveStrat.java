package Controller.Strategy;

import room.Position;
import room.Room;
import room.element.Element;
import room.element.Entity;
import room.element.MoveStrategy;
import room.element.skane.Skane;
import room.element.skane.SkaneBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private List<Position> convertToSortPosList(List<PosDist> pdList, Position p) {
        Collections.sort(pdList, new Comparator<PosDist>() {
            @Override
            public int compare(PosDist lhs, PosDist rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                if (rhs.dist > lhs.dist)
                    return -1;
                else if (rhs.dist < lhs.dist)
                    return 1;
                else
                    return 0;
            }
        });

        double a, b, c;
        List<Position> posList = new ArrayList<>();
        for (PosDist pd : pdList) {
            a = pd.p.getX() - p.getX();
            b = pd.p.getY() - p.getY();
            c = Math.sqrt(a * a + b * b);
            a /= c;
            b /= c;

            // NOTE: removeing abs allows diagonal movement
            if (Math.abs(a) > Math.abs(b))
                posList.add(new Position(p.getX() + (int) Math.round(a), p.getY()));
            else if (Math.abs(a) < Math.abs(b))
                posList.add(new Position(p.getX(), p.getY() + (int) Math.round(b)));
        }

        return posList;
    }

    private void addRayPos(List<PosDist> posDistList, Position s, Position t) {
        List<Element> rayResult = room.raycast(s, t);
        if (rayResult.size() > 0) {
            if (room.isSkanePos(rayResult.get(0).getPos()))
                posDistList.add(new PosDist(t, s.dist(t)));
        }
    }

    private Room room;

    public MeleeMoveStrat(Room room) {
        this.room = room;
    }

    private int i = 0;

    @Override
    public List<Position> execute(Entity e) {
        ++i;
        if (i < 6) {
            return new ArrayList<>();
        }
        i = 0;
        /*
         * Attempts to get to the first part of the skane it sees
         * or follow some scent.
         */
        if (room.isSkaneBury())
            return new ArrayList<>();

        Position p;
        Position ePos = e.getPos();
        Skane ska = room.getSkane();
        List<PosDist> listPos = new ArrayList<>();

        // Head
        addRayPos(listPos, ePos, ska.getPos());

        // Body
        for (SkaneBody sb : ska.getBody())
            addRayPos(listPos, ePos, sb.getPos());

        // Scent
        if (listPos.size() == 0) { // Can't see skane
            // TODO
        }

        return convertToSortPosList(listPos, ePos);
    }
}
