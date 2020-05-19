package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.skane.Skane;

public class SkaneCreator extends Creator<Skane> {
    private Integer attack_dmg, hp, oxygen_lvl, size;

    public SkaneCreator(Integer attack_dmg, Integer hp, Integer oxygen_lvl, Integer size) {
        this.attack_dmg = attack_dmg;
        this.hp = hp;
        this.oxygen_lvl = oxygen_lvl;
        this.size = size;
    }

    public SkaneCreator() {
        this(10, 4, 200, 3);
    }

    @Override
    public Skane create(Position pos) {
        Skane.SkaneOpts skane_opts = new Skane.SkaneOpts();
        skane_opts.pos = pos;
        skane_opts.attack_dmg = attack_dmg;
        skane_opts.hp = hp;
        skane_opts.oxygen_lvl = oxygen_lvl;
        skane_opts.size = size;

        return new Skane(skane_opts);
    }
}
