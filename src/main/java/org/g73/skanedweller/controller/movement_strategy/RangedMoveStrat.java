package org.g73.skanedweller.controller.movement_strategy;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Scent;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;

import java.util.ArrayList;
import java.util.List;

public class RangedMoveStrat extends ChaseStrat {
    /*
     * Attempts to get to the closest part of the skane it can see. Otherwise,
     * follows the 'freshest' scent (from the 'Skanes' scent trail) it can see.
     * When inside its shooting range, attempts to shoot.
     */
    private int ticksBetweenMoves;
    private int range;

    public RangedMoveStrat(int ticksBetweenMoves, int range) {
        this.ticksBetweenMoves = ticksBetweenMoves;
        this.range = range;
    }

    @Override
    public List<Position> genMoves(Room r, Element e) {
        e.setMovCounter(ticksBetweenMoves);
        if (r.isSkaneBury())
            return new ArrayList<>();

        Position ePos = e.getPos();
        Skane ska = r.getSkane();
        List<PosDist> listPos = new ArrayList<>();

        // Head
        if (addRayPos(r, listPos, ePos, ska.getPos()) < this.range)
            return new ArrayList<>();

        // Body
        for (SkaneBody sb : ska.getBody()) {
            if (addRayPos(r, listPos, ePos, sb.getPos()) < this.range)
                return new ArrayList<>();
        }

        // Scent
        if (listPos.size() == 0) { // If can't see skane
            Scent freshestScent = null;
            for (Scent s : ska.getScentTrail()) {
                if (checkRayScent(r, ePos, s.getPos()))
                    freshestScent = s;
            }

            if (freshestScent != null)
                listPos.add(new PosDist(freshestScent.getPos(), ePos.dist(freshestScent.getPos())));
        }

        return convertToSortPosList(listPos, ePos);
    }
}
