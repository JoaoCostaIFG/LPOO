package controller.collision_strategy;

import room.element.Element;
import room.element.skane.Skane;

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
