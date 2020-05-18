package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreatorUtilsTests {
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

}
