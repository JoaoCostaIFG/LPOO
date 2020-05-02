package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.collision_strategy.CollisionStrategy;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;

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
