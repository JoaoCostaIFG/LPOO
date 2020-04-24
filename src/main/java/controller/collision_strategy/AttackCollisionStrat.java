package controller.collision_strategy;

import room.element.*;

public class AttackCollisionStrat extends CollisionStrategy<EntityQueMorde, Entity> {
    // TODO cleanup dead enemies later
    @Override
    public boolean handle(EntityQueMorde predator, Entity prey) {
        prey.takeDamage(predator.getAtk());
        return !prey.isAlive();
    }
}
