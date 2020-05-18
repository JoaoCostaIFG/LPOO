package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.MeleeAtkStrat;
import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.MeleeGuy;

public class MeleeCreator implements Creator {
    @Override
    public Element create(Position pos) {
        MeleeMoveStrat meleeStrat = new MeleeMoveStrat(4);
        MeleeAtkStrat meleeAtkStrat = new MeleeAtkStrat(30);
        MeleeGuy m = new MeleeGuy(pos, 1, 1, 1);
        m.setMoveStrat(meleeStrat);
        m.setAtkStrat(meleeAtkStrat);
        return m;
    }
}
