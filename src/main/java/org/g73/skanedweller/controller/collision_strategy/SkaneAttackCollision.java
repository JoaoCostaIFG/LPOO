package org.g73.skanedweller.controller.collision_strategy;

import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Skane;

public class SkaneAttackCollision extends CollisionStrategy<Skane, Element> {
    @Override
    public boolean handle(Skane skane, Element prey) {
        prey.takeDamage(skane.getAtk());

        // TODO
        if (!prey.isAlive()) {
            skane.setHp(skane.getHp() + 1);
            skane.grow();
            return true;
        }
        return false;
    }
}
