package org.g73.skanedweller.controller.creator.elements_creator;

import org.g73.skanedweller.controller.creator.Creator;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.skane.Skane;

public class SkaneCreator extends ElementCreator {
    private Integer size;
    private Integer oxygen_lvl;

    public SkaneCreator(Integer attack_dmg, Integer hp, Integer oxygen_lvl, Integer size) {
        super(hp, attack_dmg, 0);
        this.size = size;
        this.oxygen_lvl = oxygen_lvl;
    }

    public SkaneCreator() {
        this(10, 4, 200, 3);
    }

    @Override
    public Skane create(Position pos) {
        Skane.SkaneOpts skane_opts = new Skane.SkaneOpts();
        skane_opts.pos = pos;
        skane_opts.attack_dmg = super.getAtk();
        skane_opts.hp = super.getHp();
        skane_opts.oxygen_lvl = oxygen_lvl;
        skane_opts.size = size;

        return new Skane(skane_opts);
    }
}
