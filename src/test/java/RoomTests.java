import org.junit.Test;
import room.Room;
import room.element.Skane;
import room.element.Wall;

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
        Skane skane = new Skane(1, 5, 10, 5, 5, 3);
        room.addElement(skane);
        assertEquals(room.getSkane(), skane);
        assertEquals(room.getSkane().getPos(), skane.getPos());

        Wall wall = new Wall(2, 3);
        room.addElement(wall);
        assertEquals(room.getWalls().size(), 1);
        assertEquals(room.getWalls().get(0).getPos(), wall.getPos());
    }
}
