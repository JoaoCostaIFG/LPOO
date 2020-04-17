import org.junit.Before;
import org.junit.Test;
import room.Position;
import room.Room;
import room.element.Wall;
import room.element.skane.Skane;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CollisionTests {
    private Skane ska;
    private Room room;
    private CollisionHandler colhandler;

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        walls.add(new Wall(new Position(4, 5)));
        walls.add(new Wall(new Position(6, 5)));
        walls.add(new Wall(new Position(5, 4)));
        walls.add(new Wall(new Position(7, 5)));
        /*    5
              W
         5  W S W W
         */

        return walls;
    }

    @Before
    public void setUp() {
        room = mock(Room.class);
        ska = mock(Skane.class);
        when(ska.getPos()).thenReturn(new Position(5, 5));
        when(room.getSkane()).thenReturn(ska);
        when(room.getHeight()).thenReturn(10);
        when(room.getWidth()).thenReturn(10);

        List<Wall> mocked_walls = createWalls();
        when(room.getWalls()).thenReturn(mocked_walls);

        this.colhandler = new CollisionHandler(room);
    }

    @Test
    public void skaneWallCollision() {
        Position pos = new Position(5, 5);
        /* Dead Skane */
        when(ska.isAlive()).thenReturn(false);
        assertEquals(false, colhandler.canSkaneMove(pos));
        pos = new Position(5, 6);
        assertEquals(false, colhandler.canSkaneMove(pos));

        when(ska.isAlive()).thenReturn(true);
        pos = new Position(5, 5); // Don't Move
        assertEquals(false, colhandler.canSkaneMove(pos));

        pos = new Position(11, 6); // Out of Bounds
        assertEquals(false, colhandler.canSkaneMove(pos));

        // Into Walls
        pos = new Position(7, 5);
        assertEquals(false, colhandler.canSkaneMove(pos));
        pos = new Position(5, 4);
        assertEquals(false, colhandler.canSkaneMove(pos));

        // Move South Normally
        pos = new Position(5, 6);
        assertEquals(true, colhandler.canSkaneMove(pos));
    }
}
