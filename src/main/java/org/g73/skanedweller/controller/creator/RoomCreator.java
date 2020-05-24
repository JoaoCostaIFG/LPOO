package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.creator.elements_creator.*;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.RayCast;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.Wall;

import java.io.IOException;
import java.util.List;

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

    private void createEnemies(Room room, List<Position> civs, List<Position> melees, List<Position> rangeds) {
        CivieCreator cc = new CivieCreator();
        for (Position pos: civs)
            addRoomElement(room, cc.create(pos));

        MeleeCreator mc = new MeleeCreator(); // Mc Champions - Ebola
        for (Position pos: melees)
            addRoomElement(room, mc.create(pos));

        RangedCreator rc = new RangedCreator();
        for (Position pos: rangeds)
            addRoomElement(room, rc.create(pos));
    }

    public Room createRoom(MapReader mr) {
        int height = mr.getHeight();
        int width = mr.getLength();
        Room room = new Room(width, height);

        for (Position p: mr.getWalls())
            addRoomElement(room, new Wall(p));
        createEnemies(room, mr.getCivilians(), mr.getMeleeEnem(), mr.getRangedEnem());
        addRoomElement(room, new SkaneCreator().create(mr.getSkanePos()));

        room.setRayCasting(new RayCast());
        return room;
    }
}
