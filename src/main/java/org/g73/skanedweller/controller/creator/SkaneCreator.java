package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Skane;

public class SkaneCreator implements Creator {
    @Override
    public Element create(Position pos) {
        Skane.SkaneOpts skane_opts = new Skane.SkaneOpts();
        skane_opts.pos = pos;
        skane_opts.attack_dmg = 10;
        skane_opts.hp = 4;
        skane_opts.oxygen_lvl = 200;
        skane_opts.size = 3;

        return new Skane(skane_opts);
    }
}
