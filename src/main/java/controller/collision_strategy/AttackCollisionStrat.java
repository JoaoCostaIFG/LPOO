package controller.collision_strategy;

import model.element.Element;

public class AttackCollisionStrat extends CollisionStrategy<Element, Element> {
    @Override
    public boolean handle(Element predator, Element prey) {
        prey.takeDamage(predator.getAtk());
        return false;
    }
}
