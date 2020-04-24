package controller;

import controller.collisionStrategy.CollisionStrategy;
import room.Position;
import room.Room;
import room.element.*;

import java.util.Map;

public abstract class MovableController<T> implements Controller {
    protected Map<Class<? extends CollidableElement>, CollisionStrategy> colHandlerMap;

    public MovableController(Map<Class<? extends CollidableElement>, CollisionStrategy> colHandlerMap) {
        this.colHandlerMap = colHandlerMap;
    }

    public boolean handleCollision(Entity entity, CollidableElement element) {
        CollisionStrategy<MovableElement, CollidableElement> strat =
                this.colHandlerMap.get(element.getClass());

        return strat.handle(entity, element);
    }

    public boolean canMove(Position newPos, Entity entity, Room room) {
        for (CollidableElement c: room.getCollidingElemsInPos(entity, newPos)) {
            if (!handleCollision(entity, c))
                return false;
        }
        return true;
    }

    public abstract void move(T entity, Position pos);
}
