import controller.strategy.ScaredMoveStrat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import room.Position;
import room.Room;
import room.element.Civilian;
import room.element.Element;
import room.element.skane.Skane;

import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.doclint.Entity.times;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

public class MovementStratTests {
    private Civilian civie;
    private Skane ska;

    @Before
    public void setUp() {
        this.ska = new Skane(20, 1, 1, 1, 1, 2);
        this.civie = new Civilian(10, 10, 10);

        ska.setPos(ska.moveRight());
        ska.setPos(ska.moveRight());
    }

    @Test
    public void scaredStratBuryTest() {
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.isSkaneBury()).thenReturn(true);

        this.civie.setStrategy(new ScaredMoveStrat(2));
        List<Position> posList = civie.executeStrategy(room);
        assertEquals(posList.size(), 0);
        assertEquals(civie.getMovCounter(), 2);

        Mockito.verify(room, times(0)).raycast(any(), any());
    }

    @Test
    public void scaredStratTest() {
        Room room = Mockito.mock(Room.class);

        // making it so skane is visible
        Mockito.when(room.isSkaneBury())
                .thenReturn(false);
        Mockito.when(room.isSkanePos(any()))
                .thenReturn(true);
        List<Element> skaList = new ArrayList<>();
        skaList.add(ska);
        Mockito.when(room.raycast(any(), any()))
                .thenReturn(skaList);

        Mockito.when(room.getSkanePos())
                .thenReturn(ska.getPos());
        this.civie.setStrategy(new ScaredMoveStrat(2));
        List<Position> posList = civie.executeStrategy(room);
        assertEquals(posList.size(), 2);
        assertEquals(civie.getMovCounter(), 2);

        assertEquals(posList.get(0), civie.moveDown());
        assertEquals(posList.get(1), civie.moveLeft());

        Mockito.verify(room, times(1)).raycast(any(), any());
        Mockito.verify(room, atLeastOnce()).getSkanePos();
    }

    @Test
    public void scaredStratObstructedTest() {
        Room room = Mockito.mock(Room.class);

        // making it so raycast hits something other than skane
        Mockito.when(room.isSkaneBury())
                .thenReturn(false);
        List<Element> skaList = new ArrayList<>();
        skaList.add(new Civilian(1, 2, 2));
        Mockito.when(room.raycast(any(), any()))
                .thenReturn(skaList);
        Mockito.when(room.isSkanePos(any()))
                .thenReturn(false);

        this.civie.setStrategy(new ScaredMoveStrat(2));
        List<Position> posList = civie.executeStrategy(room);
        assertEquals(posList.size(), 0);
        assertEquals(civie.getMovCounter(), 2);

        Mockito.verify(room, times(1)).raycast(any(), any());
        Mockito.verify(room, atLeastOnce()).getSkanePos();
    }
}
