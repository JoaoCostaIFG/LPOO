package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.Element;

public class CivieCreator extends Creator<Civilian> {
    private Integer hp;

    public CivieCreator(Integer hp) {
        this.hp = hp;
    }

    public CivieCreator() {
        this(1);
    }

    @Override
    public Civilian create(Position pos) {
        ScaredMoveStrat scared_strat = new ScaredMoveStrat(12);
        Civilian c = new Civilian(pos, hp);
        c.setMoveStrat(scared_strat);
        return c;
    }
}
