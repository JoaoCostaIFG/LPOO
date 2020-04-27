package controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import room.Position;
import room.Room;
import room.element.element_behaviours.Collidable;
import room.element.Entity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class MovableControllerTests {
    MovableController<Entity> controller;
    Entity ent;
    Room room;
    Collidable ele;
    CollisionHandler collisionHandler;

    @Before
    public void setUp() {
        ent = Mockito.mock(Entity.class);
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

        Mockito.verify(collisionHandler).handleCollision(any(Entity.class), any(Collidable.class));
    }
}
