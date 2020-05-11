package org.g73.skanedweller.controller.movement_strategy;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.MoveStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class ChaseStrat implements MoveStrategy {
    protected class PosDist {
        public Position p;
        public double dist;

        public PosDist(Position p, double dist) {
            this.p = p;
            this.dist = dist;
        }
    }

    protected double addRayPos(Room r, List<PosDist> posDistList, Position s, Position t) {
        double dist = -1;
        List<Element> rayResult = r.raycast(s, t);
        if (rayResult.size() > 0) {
            if (r.isSkanePos(rayResult.get(0).getPos())) {
                dist = s.dist(t);
                posDistList.add(new PosDist(t, dist));
            }
        }

        return dist;
    }

    protected boolean checkRayScent(Room r, Position sourcePos, Position scentPos) {
        List<Element> rayResult = r.raycast(sourcePos, scentPos);

        return rayResult.size() == 0;
    }

    protected List<Position> convertToSortPosList(List<PosDist> pdList, Position p) {
        pdList.sort(Comparator.comparingDouble(lhs -> lhs.dist));

        List<Position> posList = new ArrayList<>();
        double a, b, c;
        for (PosDist pd : pdList) {
            a = pd.p.getX() - p.getX();
            b = pd.p.getY() - p.getY();
            c = Math.sqrt(a * a + b * b);
            a /= c;
            b /= c;

            // TODO check if second position puts u further away from target
            if (Math.abs(a) > Math.abs(b)) {
                posList.add(new Position(p.getX() + (a > 0 ? 1 : -1), p.getY()));
                posList.add(new Position(p.getX(), p.getY() + (b > 0 ? 1 : -1)));
            } else {
                posList.add(new Position(p.getX(), p.getY() + (b > 0 ? 1 : -1)));
                posList.add(new Position(p.getX() + (a > 0 ? 1 : -1), p.getY()));
            }
        }

        return posList;
    }
}
