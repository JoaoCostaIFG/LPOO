package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.MeleeGuy;

public class MeleeCreator implements Creator {
    @Override
    public Element create(Position pos) {
        MeleeMoveStrat meleeStrat = new MeleeMoveStrat(4);
        MeleeGuy m = new MeleeGuy(pos, 1, 1);
        m.setMoveStrat(meleeStrat);
        return m;
    }
}
