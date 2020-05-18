package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.MeleeAtkStrat;
import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.MeleeGuy;

public class MeleeCreator extends Creator<MeleeGuy> {
    private Integer hp, atk, range;

    public MeleeCreator(Integer hp, Integer atk, Integer range) {
        this.hp = hp;
        this.atk = atk;
        this.range = range;
    }

    public MeleeCreator() {
        this(1, 1, 1);
    }

    @Override
    public MeleeGuy create(Position pos) {
        MeleeMoveStrat meleeStrat = new MeleeMoveStrat(4);
        MeleeAtkStrat meleeAtkStrat = new MeleeAtkStrat(30);
        MeleeGuy m = new MeleeGuy(pos, hp, atk, range);
        m.setMoveStrat(meleeStrat);
        m.setAtkStrat(meleeAtkStrat);
        return m;
    }
}
