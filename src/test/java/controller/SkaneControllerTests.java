package controller;

import view.EVENT;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import model.Position;
import model.Room;
import model.element.skane.Skane;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SkaneControllerTests {
    SkaneController skaCtr;
    Skane ska;
    Room room;

    @Before
    public void setUp() {
        ska = new Skane(1, 1, 1, 1, 200, 1);
        skaCtr = new SkaneController(ska, 3);
        room = Mockito.mock(Room.class);
    }

    public void setUpMock() {
        ska = Mockito.mock(Skane.class);
        skaCtr = new SkaneController(ska, 3);
    }

    @Test
    public void bury() {
        assertEquals(false, ska.isBury());
        skaCtr.setEvent(EVENT.Bury); skaCtr.update(this.room);
        assertEquals(true, ska.isBury());
        skaCtr.setEvent(EVENT.Bury); skaCtr.update(this.room);
        assertEquals(false, ska.isBury());

        ska.setOxygenLevel(150);
        assertEquals(false, ska.isBury());
        skaCtr.setEvent(EVENT.Bury); skaCtr.update(this.room);
        assertEquals(false, ska.isBury());
    }

    @Test
    public void inhale() {
        ska.bury(true);
        int n = 100;
        for (int i = 0; i < n; ++i)
            skaCtr.update(room);
        assertEquals(200 - n, ska.getOxygenLevel());

        ska.bury(false);
        skaCtr.update(room);
        skaCtr.update(room);
        assertEquals(200 - n + 2 * 2, ska.getOxygenLevel());
        for (int i = 0; i < n; ++i)
            skaCtr.update(room);
        assertEquals(200, ska.getOxygenLevel());
    }

    @Test
    public void damage() {
        setUpMock();
        skaCtr.takeDamage(300);
        verify(ska).takeDamage(300);
        verify(ska, times(300)).shrink();

        reset(ska);
        skaCtr.takeDamage(-10);
        verify(ska, never()).takeDamage(any(Integer.class));
        verify(ska, never()).shrink();
    }

    @Test
    public void eat() {
        setUpMock();
        when(ska.getHp()).thenReturn(2);
        skaCtr.nom(300);
        verify(ska).setHp(300 + 2);
        verify(ska, times(300)).grow();

        reset(ska);
        skaCtr.nom(-10);
        verify(ska, never()).setHp(any(Integer.class));
        verify(ska, never()).grow();
    }

    @Test
    public void update() {
        setUpMock();
        Mockito.when(ska.moveDown()).thenReturn(new Position(1, 1));
        skaCtr.setEvent(EVENT.MoveDown);
        skaCtr.update(room);
        Mockito.verify(ska).setPos(eq(new Position(1, 1)));

    }
}
