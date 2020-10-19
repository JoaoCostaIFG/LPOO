package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.collision_strategy.CollisionStrategy;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;

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
