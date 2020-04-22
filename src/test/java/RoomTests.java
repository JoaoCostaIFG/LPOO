import org.junit.Test;
import room.Position;
import room.Room;
import room.element.Element;
import room.element.skane.Skane;
import room.element.Wall;

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
        assertEquals(room.getSkane().getPos(), skane.getPos());

        Wall wall = new Wall(2, 3);
        room.addElement(wall);
        assertEquals(room.getWalls().size(), 1);
        assertEquals(room.getWalls().get(0).getPos(), wall.getPos());
    }

    @Test
    public void unobstructedRayCast() {
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
    public void obstructedRayCast() {
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
            assertEquals(e.getPos(), p2);
    }
}
