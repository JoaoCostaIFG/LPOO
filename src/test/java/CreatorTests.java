import creator.CreatorUtilities;
import creator.RoomCreator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import room.Position;
import room.Room;
import room.element.skane.SkaneBody;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class CreatorTests {
    // TODO inject random into CreatorUtilities to check the generated positions
    private Random rdm;
    private CreatorUtilities creatorUtls;

    @Before
    public void setUpMock() {
        this.rdm = Mockito.mock(Random.class);
        this.creatorUtls = new CreatorUtilities(rdm);
    }

    @Test
    public void getValidRandomPos() {
        int startx = 1, endx = 10;
        int starty = 11, endy = 20;

        Mockito.when(rdm.nextInt(endx - 1))
                .thenReturn(1)
                .thenReturn(2);

        Mockito.when(rdm.nextInt(endy - 1))
                .thenReturn(1)
                .thenReturn(2);

        Position rdmPos1 = creatorUtls.getRandomPos(startx, endx, starty, endy);
        Position rdmPos2 = creatorUtls.getRandomPos(startx, endx, starty, endy);

        assertEquals(rdmPos1, new Position(2, 12));
        assertEquals(rdmPos2, new Position(3, 13));

        verify(rdm, times(2)).nextInt(endx - 1);
        verify(rdm, times(2)).nextInt(endy - 1);
    }

    @Test
    public void getInvalidRandomPos() {
        int squareSide = 20;

        Mockito.when(rdm.nextInt(squareSide - 2))
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(3)
                .thenReturn(3);

        Position rdmPos1 = creatorUtls.getRdmPosRoom(squareSide, squareSide);
        assertEquals(rdmPos1, new Position(2, 2));
        verify(rdm, times(2)).nextInt(squareSide - 2);

        // add new random position to filled positions list
        assertEquals(creatorUtls.getFilledPos().size(), 0);
        creatorUtls.regPos(rdmPos1);
        assertEquals(creatorUtls.getFilledPos().size(), 1);
        creatorUtls.regPos(rdmPos1); // repeated pos shouldn't be added
        assertEquals(creatorUtls.getFilledPos().size(), 1);

        Position rdmPos2 = creatorUtls.getRdmPosRoom(squareSide, squareSide);
        assertNotEquals(rdmPos2, rdmPos1);
        assertEquals(rdmPos2, new Position(4, 4));

        // add new random position to filled positions list
        creatorUtls.regPos(rdmPos2);
        assertEquals(creatorUtls.getFilledPos().size(), 2);
        creatorUtls.regPos(rdmPos2); // repeated pos shouldn't be added
        assertEquals(creatorUtls.getFilledPos().size(), 2);

        verify(rdm, times(6)).nextInt(squareSide - 2);
    }

    @Test
    public void createSkane() {
        int width = 300;
        int heigth = 100;

        RoomCreator roomCreator = new RoomCreator(this.creatorUtls);

        Mockito.when(rdm.nextInt(width - 2))
                .thenReturn(1)
                .thenReturn(3)
                .thenReturn(5)
                .thenReturn(7)
                .thenReturn(9)
                .thenReturn(11);
        Mockito.when(rdm.nextInt(heigth - 2))
                .thenReturn(0)
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(6)
                .thenReturn(8)
                .thenReturn(10);

        Room room = roomCreator.createRoom(width, heigth);
        assertNotEquals(room.getWalls().size(), 0);
        assertNotEquals(room.getEnemies().size(), 0);
        assertEquals(room.getSkane().getPos(), new Position(2, 1));
        for (SkaneBody sb : room.getSkane().getBody())
            assertEquals(sb.getPos(), new Position(2, 1));

        // mock has no repeated positions
        verify(rdm, times(3)).nextInt(width - 2);
        verify(rdm, times(3)).nextInt(heigth - 2);
        Mockito.verifyNoMoreInteractions(rdm);

        // nothing generated on top of anything else (except skane)
        /* TODO can't find something similar to Mockito's atMost() for JUnit so I need
            to think of a nice solution.
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < heigth; ++j) {
                if (!room.isSkanePos(new Position(i, j))) {
                    assertEquals(room.getSamePos(new Position(i, j)).size(), atMost(1));
                }
            }
        }
         */
    }
}
