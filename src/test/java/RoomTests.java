import org.junit.Test;
import room.Room;
import room.element.Skane;
import room.element.Wall;

import static org.junit.Assert.assertEquals;

public class RoomTests {
    private Room room = new Room(60, 30);

    @Test
    public void creation() {
        assertEquals(1, 1);
        assertEquals(room.getWidth(), 60);
        assertEquals(room.getHeight(), 30);

        Wall wall = new Wall(2, 3);
        Skane skane = new Skane(1, 5, 10, 5, 5, 3);

        room.addElement(wall);
        room.addElement(skane);

        assertEquals(room.getWalls().size(), 1);
        assertEquals(room.getSkane(), skane);
    }
}
