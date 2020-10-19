package org.g73.skanedweller.controller;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class EnemyControllerTests {
    Room room;
    EnemyController controller;
    MeleeGuy enemy1;
    MeleeGuy enemy2;
    Skane ska;
    CollisionHandler colH;

    @BeforeEach
    public void setUp() {
        this.room = Mockito.mock(Room.class);
        this.colH = Mockito.mock(CollisionHandler.class);
        Mockito.when(colH.handleCollision(any(), any())).thenReturn(true);
        this.controller = new EnemyController(this.colH);
        this.enemy1 = Mockito.mock(MeleeGuy.class);
        this.enemy2 = Mockito.mock(MeleeGuy.class);

        this.ska = Mockito.mock(Skane.class);
        Mockito.when(room.getSkane()).thenReturn(this.ska);
        Mockito.when(ska.isBury()).thenReturn(false);

        // set up enemies
        // movement
        Mockito.when(enemy1.getMovCounter()).thenReturn(5);
        Mockito.when(enemy2.getMovCounter()).thenReturn(0);
        List<Position> retPositions = new ArrayList<>();
        retPositions.add(new Position(1, 1));
        Mockito.when(enemy2.genMoves(this.room)).thenReturn(retPositions);
        // attack
        Mockito.when(enemy1.getAtkCounter()).thenReturn(0);
        Mockito.when(enemy2.getAtkCounter()).thenReturn(5);

        // set up room
        List<Element> enemyList = new ArrayList<>();
        enemyList.add(enemy1);
        enemyList.add(enemy2);
        Mockito.when(room.getEnemies()).thenReturn(enemyList);
    }

    @Test
    public void testEnemiesMove() {
        controller.update(room);
        Mockito.verify(room).getEnemies();

        Mockito.verify(enemy1).getMovCounter();
        Mockito.verify(enemy1).tickMovCounter();
        Mockito.verify(enemy2).getMovCounter();
        Mockito.verify(enemy2, Mockito.never()).tickMovCounter();

        Mockito.verify(enemy1, Mockito.never()).genMoves(room);
        Mockito.verify(enemy2).genMoves(room);

        Mockito.verify(enemy1, Mockito.never()).setPos(any(Position.class));
        Mockito.verify(enemy2).setPos(Mockito.eq(new Position(1, 1)));
    }

    @Test
    public void testMoveWithCol() {
        // enemy2 wants to move from Pos(1, 2) to Pos(1, 1)
        Position e2Pos = new Position(1, 2);
        Position targetPos = new Position(1, 1);
        Mockito.when(enemy2.getPos()).thenReturn(e2Pos);

        List<Collidable> colElems = new ArrayList<>();
        colElems.add(enemy1);
        Mockito.when(room.getCollidingElemsInPos(enemy2, targetPos)).thenReturn(colElems);
        Mockito.when(colH.handleCollision(enemy2, enemy1)).thenReturn(false);

        controller.update(room);

        Mockito.verify(enemy2, Mockito.never()).setPos(targetPos);
    }

    @Test
    public void testEnemiesAttackBury() {
        Mockito.when(ska.isBury()).thenReturn(true);
        Mockito.when(enemy1.attack(room, ska)).thenReturn(true);

        controller.update(room);

        Mockito.verify(enemy1).getAtkCounter();
        Mockito.verify(enemy1, Mockito.never()).tickAtkCounter();
        Mockito.verify(enemy1, Mockito.never()).attack(room, ska);
        Mockito.verify(enemy1).getMovCounter();

        Mockito.verify(enemy2).getAtkCounter();
        Mockito.verify(enemy2).tickAtkCounter();
        Mockito.verify(enemy2, Mockito.never()).attack(room, ska);
        Mockito.verify(enemy2).getMovCounter();
    }


    public void testEnemiesAttackUnbury() {
        List<SkaneBody> sbs = new ArrayList<>();
        sbs.add(new SkaneBody(new Position(2, 2), ska));
        Mockito.when(ska.getBody()).thenReturn(sbs);
        Mockito.when(enemy1.attack(room, ska)).thenReturn(true);

        controller.update(room);

        Mockito.verify(enemy1).getAtkCounter();
        Mockito.verify(enemy1, Mockito.never()).tickAtkCounter();
        Mockito.verify(enemy1).attack(room, ska);
        Mockito.verify(enemy1, Mockito.never()).attack(room, sbs.get(0));
        Mockito.verify(enemy1, Mockito.never()).getMovCounter();

        Mockito.verify(enemy2).getAtkCounter();
        Mockito.verify(enemy2).tickAtkCounter();
        Mockito.verify(enemy2, Mockito.never()).attack(room, ska);
        Mockito.verify(enemy2).getMovCounter();
    }


    @Test
    public void testEnemiesAttackUnburyBody() {
        List<SkaneBody> sbs = new ArrayList<>();
        sbs.add(new SkaneBody(new Position(2, 2), ska));
        Mockito.when(ska.getBody()).thenReturn(sbs);

        Mockito.when(enemy1.attack(room, ska)).thenReturn(false);
        Mockito.when(enemy1.attack(room, sbs.get(0))).thenReturn(true);
        controller.update(room);

        Mockito.verify(enemy1).getAtkCounter();
        Mockito.verify(enemy1, Mockito.never()).tickAtkCounter();
        Mockito.verify(enemy1).attack(room, ska);
        Mockito.verify(enemy1).attack(room, sbs.get(0));
        Mockito.verify(enemy1, Mockito.never()).getMovCounter();

        Mockito.verify(enemy2).getAtkCounter();
        Mockito.verify(enemy2).tickAtkCounter();
        Mockito.verify(enemy2, Mockito.never()).attack(room, ska);
        Mockito.verify(enemy2).getMovCounter();
    }

    @Test
    public void removeDead() {
        Civilian c = Mockito.mock(Civilian.class);
        Mockito.when(c.getAtkCounter()).thenReturn(1);
        Mockito.when(c.getMovCounter()).thenReturn(1);

        List<Element> enemyList = room.getEnemies();
        enemyList.add(c);
        Mockito.when(room.getEnemies()).thenReturn(enemyList);

        controller.update(room);

        Mockito.verify(c).getAtkCounter();
        Mockito.verify(c).tickAtkCounter();
        Mockito.verify(c).getMovCounter();
        Mockito.verify(c).tickMovCounter();

        Mockito.verify(room).removeDeadEnemies();
    }
}
