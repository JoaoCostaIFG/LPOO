package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.MeleeAtkStrat;
import org.g73.skanedweller.controller.attack_strategy.RangedGuyAtkStrat;
import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.controller.movement_strategy.RangedMoveStrat;
import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.RayCast;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.model.element.skane.Skane;

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
        skane_opts.hp = 4;
        skane_opts.oxygen_lvl = 200;
        skane_opts.size = 3;

        addRoomElement(room, new Skane(skane_opts));
    }

    private void createEnemies(Room room, int width, int height) {
        ScaredMoveStrat scared_strat = new ScaredMoveStrat(12);
        Civilian c;
        for (int i = 0; i < 0; ++i) {
            c = new Civilian(creatorUtls.getRdmPosRoom(width, height), 1);
            c.setMoveStrat(scared_strat);
            addRoomElement(room, c);
        }

        MeleeMoveStrat meleeMoveStrat = new MeleeMoveStrat(4);
        MeleeAtkStrat meleeAtkStrat = new MeleeAtkStrat(30);
        MeleeGuy m;
        for (int i = 0; i < 0; ++i) {
            m = new MeleeGuy(creatorUtls.getRdmPosRoom(width, height), 1, 1, 1);
            m.setMoveStrat(meleeMoveStrat);
            m.setAtkStrat(meleeAtkStrat);
            addRoomElement(room, m);
        }

        RangedMoveStrat rangedMoveStrat = new RangedMoveStrat(8);
        RangedGuyAtkStrat rangedAtkStrat = new RangedGuyAtkStrat(60, 0);
        RangedGuy rg;
        for (int i = 0; i < 1; ++i) {
            rg = new RangedGuy(creatorUtls.getRdmPosRoom(width, height), 1, 2, 12);
            rg.setMoveStrat(rangedMoveStrat);
            rg.setAtkStrat(rangedAtkStrat);
            addRoomElement(room, rg);
        }
    }

    public Room createRoom(int width, int height) {
        Room room = new Room(width, height);

        createWalls(room, width, height);
        createSkane(room, width, height);
        createEnemies(room, width, height);

        room.setRayCasting(new RayCast());
        return room;
    }
}
