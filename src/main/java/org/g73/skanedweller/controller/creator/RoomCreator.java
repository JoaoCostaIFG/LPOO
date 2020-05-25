package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.creator.elements_creator.CivieCreator;
import org.g73.skanedweller.controller.creator.elements_creator.MeleeCreator;
import org.g73.skanedweller.controller.creator.elements_creator.RangedCreator;
import org.g73.skanedweller.controller.creator.elements_creator.SkaneCreator;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.RayCast;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Wall;

import java.util.List;

public class RoomCreator {
    private void createEnemies(Room room, List<Position> civs, List<Position> melees, List<Position> rangeds) {
        CivieCreator cc = new CivieCreator();
        for (Position pos: civs)
            room.addElement(cc.create(pos));

        MeleeCreator mc = new MeleeCreator(); // Mc Champions - Ebola
        for (Position pos: melees)
            room.addElement(mc.create(pos));

        RangedCreator rc = new RangedCreator();
        for (Position pos: rangeds)
            room.addElement(rc.create(pos));
    }

    private void genOutterWalls(Room room) {
        int height = room.getHeight(), length = room.getWidth();
        for (int i=0; i<height+2; ++i) {
            room.addElement(new Wall(new Position(0, i)));
            room.addElement(new Wall(new Position(length + 1, i)));
        }
        for (int i=1; i<length + 1; ++i) {
            room.addElement(new Wall(new Position(i, 0)));
            room.addElement(new Wall(new Position(i, height + 1)));
        }
    }
    
    public Room createRoom(MapReader mr) {
        int height = mr.getHeight();
        int width = mr.getLength();
        Room room = new Room(width, height);

        for (Position p: mr.getWalls())
            room.addElement(new Wall(p));
        genOutterWalls(room);
        createEnemies(room, mr.getCivilians(), mr.getMeleeEnem(), mr.getRangedEnem());
        room.addElement(new SkaneCreator().create(mr.getSkanePos()));

        room.setRayCasting(new RayCast());
        return room;
    }
}
