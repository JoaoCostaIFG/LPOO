package org.g73.skanedweller.controller.collision_strategy;

import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Skane;

public class SkaneAttackCollision extends CollisionStrategy<Skane, Element> {
    @Override
    public boolean handle(Skane movElem, Element collElem) {
        collElem.takeDamage(movElem.getAtk());

        // TODO
        if (!collElem.isAlive()) {
            movElem.setHp(movElem.getHp() + 1);
            movElem.grow();
            return true;
        }
        return false;
    }
}
