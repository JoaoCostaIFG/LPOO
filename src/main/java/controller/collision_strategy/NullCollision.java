package controller.collision_strategy;

import model.element.element_behaviours.Collidable;
import model.element.element_behaviours.Movable;

public class NullCollision extends CollisionStrategy {
    @Override
    public boolean handle(Movable ellMov, Collidable ellColl) {
        // Ignore collision and just set the position
        return true;
    }
}
