package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.MeleeAtkStrat;
import org.g73.skanedweller.controller.attack_strategy.RangedGuyAtkStrat;
import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.controller.movement_strategy.RangedMoveStrat;
import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.view.element_views.CivieView;

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
        skane_opts.attack_dmg = 10;
        skane_opts.hp = 4;
        skane_opts.oxygen_lvl = 200;
        skane_opts.size = 3;

    }

    private void createEnemies(Room room, int width, int height) {
        CivieCreator cc = new CivieCreator();
        for (int i = 0; i < 1; ++i) {
            Position pos = creatorUtls.getRdmPosRoom(width, height);
            addRoomElement(room, cc.create(pos));
        }

        MeleeCreator mc = new MeleeCreator(); // Mc Champions - Ebola
        for (int i = 0; i < 1; ++i) {
            Position pos = creatorUtls.getRdmPosRoom(width, height);
            addRoomElement(room, mc.create(pos));
        }

        RangedCreator rc = new RangedCreator();
        for (int i = 0; i < 1; ++i) {
            Position pos = creatorUtls.getRdmPosRoom(width, height);
            addRoomElement(room, rc.create(pos));
        }
    }

    public Room createRoom(int width, int height) {
        Room room = new Room(width, height);

        createWalls(room, width, height);
        Position skaPos = creatorUtls.getRdmPosRoom(width, height);
        addRoomElement(room, new SkaneCreator().create(skaPos));
        createSkane(room, width, height);
        createEnemies(room, width, height);

        return room;
    }
}
