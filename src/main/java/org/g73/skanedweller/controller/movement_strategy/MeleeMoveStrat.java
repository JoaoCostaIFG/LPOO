package org.g73.skanedweller.controller.movement_strategy;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;

import java.util.ArrayList;
import java.util.List;

public class MeleeMoveStrat extends ChaseStrat {
    /*
     * Attempts to get to the closest part of the skane it can see. Otherwise,
     * follows the 'freshest' scent (from the 'Skanes' scent trail) it can see.
     */
    private int ticksBetweenMoves;

    public MeleeMoveStrat(int ticksBetweenMoves) {
        this.ticksBetweenMoves = ticksBetweenMoves;
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
        addRayPos(r, listPos, ePos, ska.getPos());

        // Body
        for (SkaneBody sb : ska.getBody())
            addRayPos(r, listPos, ePos, sb.getPos());

        // Scent
        if (listPos.size() == 0) // If can't see skane
            chaseScent(r, ePos, listPos);

        return convertToSortPosList(listPos, ePos);
    }
}
