package creator;

import controller.strategy.MeleeMoveStrat;
import controller.strategy.ScaredMoveStrat;
import room.Room;
import room.element.Civilian;
import room.element.Element;
import room.element.MeleeGuy;
import room.element.skane.Skane;
import room.element.Wall;

public class RoomCreator {
    private CreatorUtilities creatorUtls;

    public RoomCreator() {
        this.creatorUtls = new CreatorUtilities();
    }

    public RoomCreator(CreatorUtilities creatorUtls) {
        this.creatorUtls = creatorUtls;
    }

    private void addRoomElement(Room room, Element e) {
        creatorUtls.regPos(e.getPos());
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
        Skane.SkaneOpts skane_opts = new Skane.SkaneOpts();
        skane_opts.pos = creatorUtls.getRdmPosRoom(width, height);
        skane_opts.attack_dmg = 10;
        skane_opts.hp = 3;
        skane_opts.oxygen_lvl = 200;
        skane_opts.size = 30;

        addRoomElement(room, new Skane(skane_opts));
    }

    private void createEnemies(Room room, int width, int height) {
        ScaredMoveStrat scared_strat = new ScaredMoveStrat(12);
        Civilian c;
        for (int i = 0; i < 1; ++i) {
            c = new Civilian(creatorUtls.getRdmPosRoom(width, height), 1);
            c.setStrategy(scared_strat);
            addRoomElement(room, c);
        }

        MeleeMoveStrat meleeStrat = new MeleeMoveStrat(4);
        MeleeGuy m;
        for (int i = 0; i < 1; ++i) {
            m = new MeleeGuy(creatorUtls.getRdmPosRoom(width, height), 1, 1);
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
