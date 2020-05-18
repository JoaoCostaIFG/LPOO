package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.RayCast;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.*;

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
        createEnemies(room, width, height);

        room.setRayCasting(new RayCast());
        return room;
    }
}
