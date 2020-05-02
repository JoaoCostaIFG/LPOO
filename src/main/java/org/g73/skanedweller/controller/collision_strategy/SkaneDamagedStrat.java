package org.g73.skanedweller.controller.collision_strategy;

import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Skane;

public class SkaneDamagedStrat extends CollisionStrategy<Element, Skane>{
    @Override
    public boolean handle(Element predator, Skane skane) {
        skane.takeDamage(predator.getAtk());

        if (skane.isAlive()) {
            skane.shrink();
            return false;
        }
        return true;
    }
}
