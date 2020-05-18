package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.RangedGuyAtkStrat;
import org.g73.skanedweller.controller.movement_strategy.RangedMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.RangedGuy;

public class RangedCreator implements Creator {
    @Override
    public Element create(Position pos) {
        RangedMoveStrat rangedMoveStrat = new RangedMoveStrat(8);
        RangedGuyAtkStrat rangedAtkStrat = new RangedGuyAtkStrat(60, 0);
        RangedGuy rg = new RangedGuy(pos, 1, 2, 12);
        rg.setMoveStrat(rangedMoveStrat);
        rg.setAtkStrat(rangedAtkStrat);
        return rg;
    }
}
