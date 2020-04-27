package controller;

import controller.collision_strategy.CollisionStrategy;
import room.element.element_behaviours.Collidable;
import room.element.Element;

import java.util.Map;

public class CollisionHandler {
    private Map<Class<? extends Collidable>, CollisionStrategy> colHandlerMap;

    public CollisionHandler(Map<Class<? extends Collidable>, CollisionStrategy> colHandlerMap) {
        this.colHandlerMap = colHandlerMap;
    }

    public boolean handleCollision(Element element, Collidable colidee) {
        CollisionStrategy strat = this.colHandlerMap.get(colidee.getClass());

        return strat.handle(element, colidee);
    }
}
