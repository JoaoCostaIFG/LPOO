package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.RangedGuyAtkStrat;
import org.g73.skanedweller.controller.movement_strategy.RangedMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.RangedGuy;

public class RangedCreator extends Creator<RangedGuy> {
    private Integer hp, atk, range;

    public RangedCreator(Integer hp, Integer atk, Integer range) {
        this.hp = hp;
        this.atk = atk;
        this.range = range;
    }

    public RangedCreator() {
        this(1, 2, 12);
    }

    @Override
    public RangedGuy create(Position pos) {
        RangedMoveStrat rangedMoveStrat = new RangedMoveStrat(8);
        RangedGuyAtkStrat rangedAtkStrat = new RangedGuyAtkStrat(60, 0);
        RangedGuy rg = new RangedGuy(pos, hp, atk, range);
        rg.setMoveStrat(rangedMoveStrat);
        rg.setAtkStrat(rangedAtkStrat);
        return rg;
    }
}
