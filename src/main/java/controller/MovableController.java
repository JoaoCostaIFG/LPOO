package controller;

import controller.collision_strategy.CollisionStrategy;
import model.Position;
import model.Room;
import model.element.element_behaviours.Collidable;
import model.element.Element;

import java.util.Map;

public abstract class MovableController<T> implements Controller {
    private CollisionHandler colHandler;

    public MovableController(Map<Class<? extends Collidable>, CollisionStrategy> colHandlerMap) {
        this.colHandler = new CollisionHandler(colHandlerMap);
    }

    public MovableController(CollisionHandler colHandler) {
        this.colHandler = colHandler;
    }

    public boolean canMove(Position newPos, Element e, Room room) {
        for (Collidable c : room.getCollidingElemsInPos(e, newPos)) {
            if (!colHandler.handleCollision(e, c))
                return false;
        }

        return true;
    }

    public abstract void move(T entity, Position pos);
}
