package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.model.element.skane.Scent;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.g73.skanedweller.observe.Observer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class RoomTests {
    private Room room;
    private Skane ska;

    @Before
    public void setUp() {
        this.room = new Room(300, 100);
        this.ska = Mockito.mock(Skane.class);
    }

    @Test
    public void testRoomInit() {
        assertEquals(room.getWidth(), 300);
        assertEquals(room.getHeight(), 100);
    }

    @Test
    public void testRoomSets() {
        room.setSize(300, 400);
        assertEquals(room.getWidth(), 300);
        assertEquals(room.getHeight(), 400);

        RayCasting rayCasting = Mockito.mock(RayCasting.class);
        room.setRayCasting(rayCasting);
        assertEquals(room.getRayCasting(), rayCasting);
    }

    @Test
    public void testAddElement() {
        Observer obs = Mockito.mock(Observer.class);
        int num_obs = 0;
        room.addObserver(obs);

        assertNull(room.getSkane());
        room.addElement(ska);
        assertEquals(room.getSkane(), ska);
        Mockito.verify(obs, times(++num_obs)).changed(room);

        assertEquals(room.getWalls().size(), 0);
        Wall wall = Mockito.mock(Wall.class);
        room.addElement(wall);
        assertEquals(room.getWalls().size(), 1);
        assertEquals(room.getWalls().get(0), wall);
        Mockito.verify(obs, times(++num_obs)).changed(room);

        assertEquals(room.getEnemies().size(), 0);
        Civilian civie = Mockito.mock(Civilian.class);
        room.addElement(civie);
        assertEquals(room.getEnemies().size(), 1);
        assertEquals(room.getEnemies().get(0), civie);
        Mockito.verify(obs, times(++num_obs)).changed(room);

        MeleeGuy melee = Mockito.mock(MeleeGuy.class);
        room.addElement(melee);
        assertEquals(room.getEnemies().size(), 2);
        assertEquals(room.getEnemies().get(1), melee);
        Mockito.verify(obs, times(++num_obs)).changed(room);

        RangedGuy ranged = Mockito.mock(RangedGuy.class);
        room.addElement(ranged);
        assertEquals(room.getEnemies().size(), 3);
        assertEquals(room.getEnemies().get(2), ranged);
        Mockito.verify(obs, times(++num_obs)).changed(room);

        room.addElement(Mockito.mock(Laser.class));
        Mockito.verify(obs, times(++num_obs)).changed(room);
        room.addElement(Mockito.mock(Scent.class));
        Mockito.verify(obs, times(++num_obs)).changed(room);
        room.addElement(Mockito.mock(SkaneBody.class));
        Mockito.verify(obs, times(++num_obs)).changed(room);

        assertEquals(room.getWalls().size(), 1);
        assertEquals(room.getEnemies().size(), 3);
        assertEquals(room.getSkane(), ska);
    }

    @Test
    public void testGetElement() {
        // no skane in room
        List<Element> es = new ArrayList<>();
        es.add(Mockito.mock(Wall.class));
        es.add(Mockito.mock(Civilian.class));
        for (Element e : es)
            room.addElement(e);

        List<Element> roomEs = room.getElements();
        assertEquals(roomEs, es);
        for (Element e : es) {
            if (e == null)
                fail("Added null object (most likely skane");
        }

        // there's a skane in the room
        es.add(ska);
        room.addElement(ska);

        roomEs = room.getElements();
        assertEquals(roomEs, es);
        for (Element e : es) {
            if (e == null)
                fail("Added null object (most likely skane");
        }
    }

    @Test
    public void testRoomRemoveDead() {
        Observer obs = Mockito.mock(Observer.class);
        int num_notifications = 0;
        room.addObserver(obs);

        Civilian c1 = Mockito.mock(Civilian.class);
        Mockito.when(c1.isAlive())
                .thenReturn(false);
        Civilian c2 = Mockito.mock(Civilian.class);
        Mockito.when(c2.isAlive())
                .thenReturn(true);

        room.addElement(c1);
        Mockito.verify(obs, times(++num_notifications)).changed(room);
        room.addElement(c2);
        Mockito.verify(obs, times(++num_notifications)).changed(room);
        assertEquals(room.getElements().size(), 2);
        assertEquals(room.getElements(), new ArrayList<Element>(Arrays.asList(c1, c2)));

        room.removeDeadEnemies();
        Mockito.verify(obs, times(++num_notifications)).changed(room);
        assertEquals(room.getElements().size(), 1);
        assertEquals(room.getElements(), new ArrayList<Element>(Collections.singletonList(c2)));

        room.removeDeadEnemies();
        Mockito.verify(obs, times(num_notifications)).changed(room);
        assertEquals(room.getElements().size(), 1);
        assertEquals(room.getElements(), new ArrayList<Element>(Collections.singletonList(c2)));
    }

    @Test
    public void testRoomObsRm() {
        Observer obs = Mockito.mock(Observer.class);
        room.addObserver(obs);
        Mockito.verify(obs, never()).changed(room);

        room.addElement(Mockito.mock(Element.class));
        Mockito.verify(obs).changed(room);

        room.removeObserver(obs);
        room.addElement(Mockito.mock(Element.class));
        Mockito.verify(obs, times(1)).changed(room);
    }

    @Test
    public void testRoomSkane() {
        room.addElement(ska);

        Mockito.when(ska.isBury())
                .thenReturn(false);
        assertFalse(room.isSkaneBury());

        Mockito.when(ska.isBury())
                .thenReturn(true);
        assertTrue(room.isSkaneBury());

        Position skaP = new Position(3, 3);
        Mockito.when(ska.getPos())
                .thenReturn(skaP);
        assertEquals(room.getSkanePos(), skaP);
    }

    @Test
    public void testIsSkanePos() {
        Position skaP = new Position(3, 3);
        Position skaBodyP = new Position(4, 4);
        Position otherP = new Position(5, 5);

        Mockito.when(ska.getPos())
                .thenReturn(skaP);
        room.addElement(ska);

        List<SkaneBody> skaBody = new ArrayList<>();
        skaBody.add(Mockito.mock(SkaneBody.class));
        Mockito.when(ska.getBody())
                .thenReturn(skaBody);
        Mockito.when(skaBody.get(0).getPos())
                .thenReturn(skaBodyP);

        assertTrue(room.isSkanePos(skaP));
        assertTrue(room.isSkanePos(skaBodyP));
        assertFalse(room.isSkanePos(otherP));
    }

    @Test
    public void testGetCollidingElements() {
        Civilian c = Mockito.mock(Civilian.class);
        assertNotEquals(c, ska);
        room.addElement(c);
        room.addElement(ska);

        Mockito.when(c.collidesWith(ska))
                .thenReturn(true);
        assertEquals(room.getColliding(ska), new ArrayList<Element>(Collections.singletonList(c)));

        Mockito.when(c.collidesWith(ska))
                .thenReturn(false);
        assertEquals(room.getColliding(ska), new ArrayList<>());
    }

    @Test
    public void testGetCollidingElemsInPos() {
        Civilian c = Mockito.mock(Civilian.class);
        assertNotEquals(c, ska);
        room.addElement(c);
        room.addElement(ska);

        Mockito.when(ska.collidesWith(c))
                .thenReturn(true);

        Position cPos = new Position(2, 2);
        Position newPos = new Position(3, 3);
        Mockito.when(c.shadowStep(cPos))
                .thenReturn(newPos);

        assertEquals(room.getCollidingElemsInPos(c, cPos), new ArrayList<Element>(Collections.singletonList(ska)));
        Mockito.verify(c).shadowStep(cPos);
        Mockito.verify(c).shadowStep(newPos);
    }

    @Test
    public void testRoomRayCastDelegate() {
        RayCasting rc = Mockito.mock(RayCasting.class);
        room.setRayCasting(rc);
        assertEquals(room.getRayCasting(), rc);

        Position s = Mockito.mock(Position.class);
        Position t = Mockito.mock(Position.class);

        List<Position> posL = new ArrayList<>(Collections.singletonList(t));
        Mockito.when(rc.posRay(room, s, t))
                .thenReturn(posL);
        assertEquals(room.posRay(s, t), posL);
        Mockito.verify(rc).posRay(room, s, t);

        Mockito.reset(rc);
        List<Element> elemL = new ArrayList<>(Collections.singletonList(Mockito.mock(Element.class)));
        Mockito.when(rc.elemRay(room, s, t))
                .thenReturn(elemL);
        assertEquals(room.elemRay(s, t), elemL);
        Mockito.verify(rc).elemRay(room, s, t);
    }

    @Test
    public void testRoomSamePos() {
        Position p1 = new Position(1, 1);
        Position p2 = new Position(2, 2);

        Civilian c1 = Mockito.mock(Civilian.class);
        Mockito.when(c1.getPos())
                .thenReturn(p1);
        Civilian c2 = Mockito.mock(Civilian.class);
        Mockito.when(c2.getPos())
                .thenReturn(p2);

        assertEquals(room.getSamePos(p1).size(), 0);
        assertEquals(room.getSamePos(p2).size(), 0);

        room.addElement(c1);
        assertEquals(room.getSamePos(p1), new ArrayList<Element>(Collections.singletonList(c1)));
        assertEquals(room.getSamePos(p2).size(), 0);

        room.addElement(c1);
        room.addElement(c2);
        assertEquals(room.getSamePos(p1), new ArrayList<Element>(Arrays.asList(c1, c1)));
        assertEquals(room.getSamePos(p2), new ArrayList<Element>(Collections.singletonList(c2)));
    }
}
