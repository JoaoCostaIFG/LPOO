package controller;

import controller.collision_strategy.CollisionStrategy;
import room.element.CollidableElement;
import room.element.Entity;
import room.element.MovableElement;

import java.util.Map;

public class CollisionHandler {
    private Map<Class<? extends CollidableElement>, CollisionStrategy> colHandlerMap;

    public CollisionHandler(Map<Class<? extends CollidableElement>, CollisionStrategy> colHandlerMap) {
        this.colHandlerMap = colHandlerMap;
    }

    public boolean handleCollision(Entity entity, CollidableElement element) {
        CollisionStrategy strat = this.colHandlerMap.get(element.getClass());

        return strat.handle(entity, element);
    }
}
