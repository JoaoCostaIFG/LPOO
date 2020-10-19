package org.g73.skanedweller.controller.creator.elements_creator;

import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Civilian;

public class CivieCreator extends ElementCreator {
    public CivieCreator(Integer hp) {
        super(hp, 0, 0);
    }

    public CivieCreator() {
        this(1);
    }

    @Override
    public Civilian create(Position pos) {
        ScaredMoveStrat scared_strat = new ScaredMoveStrat(12);
        Civilian c = new Civilian(pos, super.getHp());
        c.setMoveStrat(scared_strat);
        return c;
    }
}
