package controller;

import controller.collision_strategy.CollisionStrategy;
import room.Position;
import room.Room;
import room.element.CollidableElement;
import room.element.Entity;

import java.util.Map;

public abstract class MovableController<T> implements Controller {
    private CollisionHandler colHandler;

    public MovableController(Map<Class<? extends CollidableElement>, CollisionStrategy> colHandlerMap) {
        this.colHandler = new CollisionHandler(colHandlerMap);
    }

    public MovableController(CollisionHandler colHandler) {
        this.colHandler = colHandler;
    }

    public boolean canMove(Position newPos, Entity entity, Room room) {
        for (CollidableElement c : room.getCollidingElemsInPos(entity, newPos)) {
            if (!colHandler.handleCollision(entity, c))
                return false;
        }

        return true;
    }

    public abstract void move(T entity, Position pos);
}
