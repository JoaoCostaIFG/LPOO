package creator;

import java.util.Random;

import room.Position;
import room.Room;
import room.element.Skane;
import room.element.Wall;

public class RoomCreator {
    private final Random random;

    public RoomCreator() {
        this.random = new Random();
    }

    public Room createRoom(int width, int height) {
        Room room = new Room(width, height);

        /* spawn skane at random pos (inside bounds) */
        Position skane_pos = new Position(random.nextInt(width - 2) + 1,
                random.nextInt(height - 2) + 1);
        Skane skane = new Skane(skane_pos, 10, 3, 200, 3);
        room.addElement(skane);

        for (int c = 0; c < width; ++c) {
            room.addElement(new Wall(c, 0));
            room.addElement(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; ++r) {
            room.addElement(new Wall(0, r));
            room.addElement(new Wall(width - 1, r));
        }

        return room;
    }
}
