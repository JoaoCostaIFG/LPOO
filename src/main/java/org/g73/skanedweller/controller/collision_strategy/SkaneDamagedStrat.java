package org.g73.skanedweller.controller.collision_strategy;

import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Skane;

public class SkaneDamagedStrat extends CollisionStrategy<Element, Skane> {
    @Override
    public boolean handle(Element predator, Skane skane) {
        skane.takeDamage(predator.getAtk());

        for (int i = 0; i < predator.getAtk() && skane.getSize() > 0; ++i)
            skane.shrink();

        return !skane.isAlive();
    }
}
