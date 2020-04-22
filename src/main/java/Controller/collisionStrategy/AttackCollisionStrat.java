package Controller.collisionStrategy;

import room.Position;
import room.element.*;

public class AttackCollisionStrat extends CollisionStrategy<EntityQueMorde, Entity> {
    public AttackCollisionStrat(EntityQueMorde colliding_elem) {
        super(colliding_elem);
    }

    @Override
    public EntityQueMorde getColliding_elem() {
        return super.getColliding_elem();
    }

    // TODO cleanup dead enemies later
    @Override
    public void handle(Entity element, Position wanted_pos) {
        element.takeDamage(colliding_elem.getAtk());
        if (!element.isAlive())
            colliding_elem.setPos(wanted_pos);
    }

}
