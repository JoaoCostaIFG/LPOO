package Controller;

import Controller.collisionStrategy.CollisionStrategy;
import room.Position;
import room.Room;
import room.element.CollidableElement;
import room.element.Element;
import room.element.Entity;

import java.util.List;

public abstract class MovementController { // FIXME Room e Entity como membro dado?
    private Room room;
    private Entity entity;
    private CollisionStrategy skaneCollision, wallCollision, enemyCollision;

    public static List<CollidableElement> getCollidingElemsOnMovement(Room room, Entity ent, Position pos) {
        // What elems does it collide if we move ent to the pos position?
        Position old_pos = ent.getPos();
        ent.setPos(pos);
        List<CollidableElement> res = room.getCollidingElems(ent);
        ent.setPos(old_pos); // Reset position
        return res;
    }

    // Será que podemos generalizar colisões com Entities que mordem?
    public abstract void collideWithEnemy();
}
