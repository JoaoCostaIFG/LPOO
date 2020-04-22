package creator;

import java.util.HashSet;
import java.util.Random;

import Controller.Strategy.MeleeMoveStrat;
import Controller.Strategy.ScaredMoveStrat;
import room.Position;
import room.Room;
import room.element.Civilian;
import room.element.Element;
import room.element.MeleeGuy;
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

        return position;
    }

    private Position getRdmPosRoom(int width, int heigth) {
        /* get random inbound postion */
        return getRandomPos(1, width - 1, 1, heigth - 1);
    }

    private void regPos(Position p) {
        filled_pos.add(p);
    }

    private void addRoomElement(Room room, Element e) {
        regPos(e.getPos());
        room.addElement(e);
    }

    private void createWalls(Room room, int width, int height) {
        for (int c = 0; c < width; ++c) {
            addRoomElement(room, new Wall(c, 0));
            addRoomElement(room, new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; ++r) {
            addRoomElement(room, new Wall(0, r));
            addRoomElement(room, new Wall(width - 1, r));
        }

        for (int m = height / 3; m < 2 * height / 3; ++m) {
            addRoomElement(room, new Wall(width / 2, m));
        }
    }

    private void createSkane(Room room, int width, int height) {
        SkaneOpts skane_opts = new SkaneOpts();
        skane_opts.pos = getRdmPosRoom(width, height);
        skane_opts.attack_dmg = 10;
        skane_opts.hp = 3;
        skane_opts.oxygen_lvl = 200;
        skane_opts.size = 3;
        skane_opts.scent_dur = 50;

        addRoomElement(room, new Skane(skane_opts));
    }

    private void createEnemies(Room room, int width, int height) {
        ScaredMoveStrat scared_strat = new ScaredMoveStrat();
        Civilian c;
        for (int i = 0; i < 1; ++i) {
            c = new Civilian(getRdmPosRoom(width, height), 1);
            c.setStrategy(scared_strat);
            addRoomElement(room, c);
        }

        MeleeMoveStrat meleeStrat = new MeleeMoveStrat();
        MeleeGuy m;
        for (int i = 0; i < 1; ++i) {
            m = new MeleeGuy(getRdmPosRoom(width, height), 1, 1);
            m.setStrategy(meleeStrat);
            addRoomElement(room, m);
        }
    }

    public Room createRoom(int width, int height) {
        Room room = new Room(width, height);

        createWalls(room, width, height);
        createSkane(room, width, height);
        createEnemies(room, width, height);

        return room;
    }
}
