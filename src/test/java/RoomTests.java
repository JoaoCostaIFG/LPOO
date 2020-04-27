import org.junit.Test;
import room.Position;
import room.Room;

import room.element.*;
import room.element.element_behaviours.Collidable;
import room.element.skane.Skane;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoomTests {
    private Room room = new Room(300, 100);

    @Test
    public void creation() {
        assertEquals(room.getWidth(), 300);
        assertEquals(room.getHeight(), 100);
    }

    @Test
    public void addElement() {
        Skane skane = new Skane(1, 5, 10, 5, 5, 3);
        room.addElement(skane);
        assertEquals(room.getSkane(), skane);
        assertEquals(room.getSkane(), skane);

        Wall wall = new Wall(2, 3);
        room.addElement(wall);
        assertEquals(room.getWalls().size(), 1);
        assertEquals(room.getWalls().get(0), wall);

        Civilian civie = new Civilian(3,7, 3);
        room.addElement(civie);
        assertEquals(room.getEnemies().size(), 1);
        assertEquals(room.getEnemies().get(0), civie);

        MeleeGuy melee = new MeleeGuy(55,67, 3, 10);
        room.addElement(melee);
        assertEquals(room.getEnemies().size(), 2);
        assertEquals(room.getEnemies().get(1), melee);
    }

    @Test
    public void unobstructedRayCastOct03() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(232, 57);

        Wall w1 = new Wall(p1);
        Wall w2 = new Wall(p2);

        room.addElement(w1);
        room.addElement(w2);
        room.addElement(w2);

        // Unobstructed view
        List<Element> unobstructedElemList = room.raycast(p1, p2);
        assertEquals(unobstructedElemList.size(), 2);
        for (Element e : unobstructedElemList)
            assertEquals(e, w2);
    }

    @Test
    public void unobstructedRayCastOct12() {
        Position p1 = new Position(200, 5);
        Position p2 = new Position(1, 280);

        Wall w1 = new Wall(p1);
        Wall w2 = new Wall(p2);

        room.addElement(w1);
        room.addElement(w1);
        room.addElement(w2);

        // Unobstructed view
        List<Element> unobstructedElemList = room.raycast(p1, p2);
        assertEquals(unobstructedElemList.size(), 1);
        for (Element e : unobstructedElemList)
            assertEquals(e, w2);
    }

    @Test
    public void obstructedRayCast03() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(232, 57);
        Position p3 = new Position(233, 57);

        Wall w1 = new Wall(p1);
        Wall w2 = new Wall(p2);
        Wall w3 = new Wall(p3);

        room.addElement(w1);
        room.addElement(w2);
        room.addElement(w3);

        // Obstructed view
        List<Element> obstructedElemList = room.raycast(p1, p3);
        assertEquals(obstructedElemList.size(), 1);
        for (Element e : obstructedElemList)
            assertEquals(e, w2);
    }

    @Test
    public void obstructedRayCastOct12() {
        Position p1 = new Position(200, 5);
        Position p2 = new Position(2, 279);
        Position p3 = new Position(1, 280);

        Wall w1 = new Wall(p1);
        Wall w2 = new Wall(p2);
        Wall w3 = new Wall(p3);

        room.addElement(w1);
        room.addElement(w2);
        room.addElement(w3);

        // Unobstructed view
        List<Element> obstructedElemList = room.raycast(p1, p3);
        assertEquals(obstructedElemList.size(), 1);
        for (Element e : obstructedElemList)
            assertEquals(e, w2);
    }

    @Test
    public void getCollidingElements() {
        room = new Room(100, 100);
        Skane ska = new Skane(100, 100, 1, 1, 1, 1);
        ska.setPos(new Position(101, 100));
        Civilian civ = new Civilian(1, 1, 1);
        room.addElement(ska);
        room.addElement(civ);
        room.addElement(new Wall(1, 1));
        room.addElement(new Wall(20, 1));
        room.addElement(new Wall(20, 1));

        List<Collidable> list = room.getColliding(civ);
        assertEquals(1, list.size());
        list = room.getCollidingElemsInPos(civ, new Position(20, 1));
        assertEquals(2, list.size()); // Collides with clone copy, what do?

        list = room.getColliding(ska);
        assertEquals(0, list.size());

        list = room.getCollidingElemsInPos(civ, new Position(1, 1));
        assertEquals(1, list.size());
        list = room.getCollidingElemsInPos(civ, new Position(20, 1));
        assertEquals(2, list.size());
        list = room.getCollidingElemsInPos(civ, new Position(10, 10));
        assertEquals(0, list.size());

        Wall wall = new Wall(new Position(102, 100)); // Right next to skane
        room.addElement(wall);

        list = room.getCollidingElemsInPos(ska, new Position(102, 100));
        assertEquals(1, list.size());
        assertEquals(wall, list.get(0));
    }
}
