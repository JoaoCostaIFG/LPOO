package creator;

import java.util.HashSet;
import java.util.Random;

import room.Position;
import room.Room;
import room.element.Civilian;
import room.element.skane.Skane;
import room.element.Wall;
import room.element.skane.SkaneOpts;

public class RoomCreator {
    private final Random random;
    private HashSet<Position> filled_pos;

    public RoomCreator() {
        this.random = new Random();
        this.filled_pos = new HashSet<>(100);
    }

    private Position getRandomPos(int startx, int endx, int starty, int endy) {
        /* we generate random postions until we find one that hasn't been taken yet */
        boolean repeated = true;
        Position position;
        do {
            position = new Position(random.nextInt(endx - 1) + startx,
                    random.nextInt(endy - 1) + starty);

            repeated = false;
            for (Position p : filled_pos) {
                if (p.equals(position)) {
                    repeated = true;
                    break;
                }
            }
        } while (repeated);

        filled_pos.add(position);
        return position;
    }

    private Position getRdmPosRoom(int width, int heigth) {
        /* get random inbound postion */
        return getRandomPos(1, width - 1, 1, heigth - 1);
    }

    private void createSkane(Room room, int width, int height) {
        SkaneOpts skane_opts = new SkaneOpts();
        skane_opts.pos = getRdmPosRoom(width, height);
        skane_opts.attack_dmg = 10;
        skane_opts.hp = 3;
        skane_opts.oxygen_lvl = 200;
        skane_opts.size = 3;

        Skane skane = new Skane(skane_opts);
        room.addElement(skane);
    }

    private void createWalls(Room room, int width, int height) {
        for (int c = 0; c < width; ++c) {
            room.addElement(new Wall(c, 0));
            room.addElement(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; ++r) {
            room.addElement(new Wall(0, r));
            room.addElement(new Wall(width - 1, r));
        }
    }

    private void createEnemies(Room room, int width, int height) {
        Position civie_pos;
        for (int i = 0; i < 5; ++i) {
            civie_pos = getRdmPosRoom(width, height);
            room.addElement(new Civilian(civie_pos, 1));
        }
    }

    public Room createRoom(int width, int height) {
        Room room = new Room(width, height);

        createSkane(room, width, height);
        createWalls(room, width, height);
        createEnemies(room, width, height);

        return room;
    }
}
