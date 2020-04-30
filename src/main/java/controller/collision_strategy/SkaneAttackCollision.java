package controller.collision_strategy;

import room.element.Element;
import room.element.skane.Skane;

public class SkaneAttackCollision extends CollisionStrategy<Skane, Element> {
    @Override
    public boolean handle(Skane skane, Element prey) {
        prey.takeDamage(skane.getAtk());

        if (!prey.isAlive()) {
            skane.grow();
            return true;
        }
        return false;
    }
}
