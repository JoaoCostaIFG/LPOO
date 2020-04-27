package controller.collision_strategy;

import room.element.Element;

public class AttackCollisionStrat extends CollisionStrategy<Element, Element> {
    // TODO cleanup dead enemies later
    @Override
    public boolean handle(Element predator, Element prey) {
        prey.takeDamage(predator.getAtk());
        return !prey.isAlive();
    }
}
