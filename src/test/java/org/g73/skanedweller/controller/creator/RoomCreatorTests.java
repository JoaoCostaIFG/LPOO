package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoomCreatorTests {
    // TODO inject random into CreatorUtilities to check the generated positions
    RoomCreator roomCreator;
    MapReader mapReader;
    final int width = 300;
    final int heigth = 100;

    @Before
    public void setUp() {
        roomCreator = new RoomCreator();
        mapReader = Mockito.mock(MapReader.class);
        Position skaPos = new Position(2, 1);
        List<Position> civPos = new ArrayList<>();
        List<Position> mPos = new ArrayList<>();
        List<Position> rPos = new ArrayList<>();
        civPos.add(new Position(3, 5)); civPos.add(new Position(9, 8));
        mPos.add(new Position(2, 5)); mPos.add(new Position(10, 8));
        rPos.add(new Position(25, 25)); rPos.add(new Position(30, 8));
        Mockito.when(mapReader.getCivilians()).thenReturn(civPos);
        Mockito.when(mapReader.getMeleeEnem()).thenReturn(mPos);
        Mockito.when(mapReader.getRangedEnem()).thenReturn(rPos);
        Mockito.when(mapReader.getSkanePos()).thenReturn(skaPos);
        Mockito.when(mapReader.getHeight()).thenReturn(heigth);
        Mockito.when(mapReader.getLength()).thenReturn(width);
    }

    @Test
    public void create() {
        Room room = roomCreator.createRoom(mapReader);
        int total_walls = width*2 + (heigth+2)*2;
        assertEquals(total_walls, room.getWalls().size());
        assertEquals(6, room.getEnemies().size());
        assertEquals(new Position(2, 1), room.getSkane().getPos());
    }
}
