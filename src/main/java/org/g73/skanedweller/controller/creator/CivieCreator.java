package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.Element;

public class CivieCreator implements Creator {
    @Override
    public Element create(Position pos) {
        ScaredMoveStrat scared_strat = new ScaredMoveStrat(12);
        Civilian c = new Civilian(pos, 1);
        c.setMoveStrat(scared_strat);
        return c;
    }
}
