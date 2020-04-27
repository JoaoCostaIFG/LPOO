package controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import room.Position;
import room.Room;
import room.element.Entity;
import room.element.MeleeGuy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class EnemyControllerTests {
    Room room;
    EnemyController controller;
    MeleeGuy enemy1;
    MeleeGuy enemy2;


    @Before
    public void setUp() {
        this.room = Mockito.mock(Room.class);
        this.controller = new EnemyController();
        this.enemy1 = Mockito.mock(MeleeGuy.class);
        this.enemy2 = Mockito.mock(MeleeGuy.class);

        // set up enemies
        Mockito.when(enemy1.getMovCounter()).thenReturn(5);
        Mockito.when(enemy2.getMovCounter()).thenReturn(0);
        List<Position> retPositions = new ArrayList<>(); retPositions.add(new Position(1, 1));
        Mockito.when(enemy2.genMoves(this.room)).thenReturn(retPositions);
        List<Entity> list = new ArrayList<>();
        list.add(enemy1); list.add(enemy2);

        // set up room
        Mockito.when(room.getEnemies()).thenReturn(list);
    }

    @Test
    public void moveEnemies() {
        controller.update(room);
        Mockito.verify(room).getEnemies();
        Mockito.verify(enemy1, Mockito.never()).genMoves(room);
        Mockito.verify(enemy2).genMoves(room);

        Mockito.verify(enemy1, Mockito.never()).setPos(Mockito.any(Position.class));
        Mockito.verify(enemy2).setPos(Mockito.eq(new Position(1, 1)));
    }
}