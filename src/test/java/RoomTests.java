import org.junit.Test;
import room.Position;
import room.Room;
import room.colliders.RectangleCollider;
import room.element.Civilian;
import room.element.CollidableElement;
import room.element.Element;
import room.element.skane.Skane;
import room.element.Wall;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoomTests {
    private Room room = new Room(300, 100);

    @Test
    public void creation() {
        assertEquals(1, 1);
        assertEquals(room.getWidth(), 300);
        assertEquals(room.getHeight(), 100);
    }

    @Test
    public void addElement() {
        Skane skane = new Skane(1, 5, 10, 5, 5, 3, 10);
        room.addElement(skane);
        assertEquals(room.getSkane(), skane);
        assertEquals(room.getSkane().getPos(), skane.getPos());

        Wall wall = new Wall(2, 3);
        room.addElement(wall);
        assertEquals(room.getWalls().size(), 1);
        assertEquals(room.getWalls().get(0).getPos(), wall.getPos());
    }

    @Test
    public void getCollidingElements() {
        room = new Room(100, 100);
        Skane ska = new Skane(100, 100, 1, 1, 1, 1, 1);
        ska.setPos(new Position(101, 100));
        Civilian civ = new Civilian(1, 1, 1);
        room.addElement(ska);
        room.addElement(civ);
        room.addElement(new Wall(1, 1));
        room.addElement(new Wall(20, 1));
        room.addElement(new Wall(20, 1));

        List<CollidableElement> list = room.getCollidingElems(civ);
        assertEquals(1, list.size());
        list = room.getCollidingElemsInPos(civ, new Position(20, 1));
        assertEquals(2, list.size()); // Collides with clone copy, what do?

        list = room.getCollidingElems(ska);
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
