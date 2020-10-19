package org.g73.skanedweller.controller;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class MovableControllerTests {
    MovableController<Element> controller;
    Element ent;
    Room room;
    Collidable ele;
    CollisionHandler collisionHandler;

    @BeforeEach
    public void setUp() {
        ent = Mockito.mock(Element.class);
        room = Mockito.mock(Room.class);
        ele = Mockito.mock(Collidable.class);
        collisionHandler = Mockito.mock(CollisionHandler.class);
    }

    @Test
    public void colHandler() {
        Position p = new Position(1, 1);
        List<Collidable> list = new ArrayList<>(); list.add(ele);
        controller = new EnemyController(collisionHandler);

        Mockito.when(room.getCollidingElemsInPos(ent, p)).thenReturn(list);
        controller.canMove(new Position(1, 1), ent, room);

        Mockito.verify(collisionHandler).handleCollision(any(Element.class), any(Collidable.class));
    }
}
