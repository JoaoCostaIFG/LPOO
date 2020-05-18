package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.Wall;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RoomCreatorTests {
    // TODO inject random into CreatorUtilities to check the generated positions
    RoomCreator roomCreator;
    CreatorUtilities creatorUtils;
    final int width = 300;
    final int heigth = 100;

    @Before
    public void setUp() {
        creatorUtils = Mockito.mock(CreatorUtilities.class);
        roomCreator = new RoomCreator(creatorUtils);

        Mockito.when(creatorUtils.getRdmPosRoom(width, heigth))
                .thenReturn(new Position(2, 1))
                .thenReturn(new Position(4, 3))
                .thenReturn(new Position(6, 5))
                .thenReturn(new Position(8, 7))
                .thenReturn(new Position(10, 9))
                .thenReturn(new Position(11, 10));
    }

    @Test
    public void create() {
        Room room = roomCreator.createRoom(width, heigth);
        int total_walls = (int) (width*2 + (heigth-2)*2 + (1.0/3) * heigth);
        assertEquals(total_walls, room.getWalls().size());
        assertEquals(3, room.getEnemies().size());
        assertEquals(new Position(2, 1), room.getSkane().getPos());

        Mockito.verify(creatorUtils, times(3 + 1))
                .getRdmPosRoom(width, heigth);
        Mockito.verify(creatorUtils, times(3 + 1 + total_walls))
                .regPos(any(Position.class));


        for (SkaneBody sb : room.getSkane().getBody())
            assertEquals(new Position(2, 1), sb.getPos());

        // nothing generated on top of anything else (except skane)
        List<Element> elemList;
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < heigth; ++j) {
                if (!room.isSkanePos(new Position(i, j))) {
                    elemList = room.getSamePos(new Position(i, j));
                    if (elemList.size() > 1)
                        fail();
                }
            }
        }
    }
}
