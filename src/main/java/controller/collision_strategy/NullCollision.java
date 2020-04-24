package controller.collision_strategy;

import room.element.CollidableElement;
import room.element.MovableElement;

public class NullCollision extends CollisionStrategy {
    @Override
    public boolean handle(MovableElement ellMov, CollidableElement ellColl) {
        // Ignore collision and just set the position
        return true;
    }
}
