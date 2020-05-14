package org.g73.skanedweller.controller;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.view.EVENT;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SkaneControllerTests {
    SkaneController skaCtr;
    Skane ska;
    Room room;

    private final int scentDur = 3;

    @Before
    public void setUp() {
        ska = new Skane(1, 1, 1, 1, 200, 1);
        skaCtr = new SkaneController(ska, scentDur);
        room = Mockito.mock(Room.class);
    }

    public void setUpMock() {
        ska = Mockito.mock(Skane.class);
        skaCtr = new SkaneController(ska, scentDur);
    }

    @Test
    public void bury() {
        assertEquals(false, ska.isBury());
        skaCtr.setEvent(EVENT.Bury);
        skaCtr.update(this.room);
        assertEquals(true, ska.isBury());
        skaCtr.setEvent(EVENT.Bury);
        skaCtr.update(this.room);
        assertEquals(false, ska.isBury());

        ska.setOxygenLevel(150);
        assertEquals(false, ska.isBury());
        skaCtr.setEvent(EVENT.Bury);
        skaCtr.update(this.room);
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
    public void goOutOfOxy() {
        ska.bury(true);
        int n = 200;
        for (int i = 0; i < n; ++i)
            skaCtr.update(room);
        assertEquals(0, ska.getOxygenLevel());

        assertEquals(true, ska.isBury());
        skaCtr.update(room);
        assertEquals(false, ska.isBury());
    }

    @Test
    public void unburyMaxOxyInhale() {
        setUpMock();
        when(ska.getOxygenLevel()).thenReturn(200);
        when(ska.getMaxOxygenLevel()).thenReturn(200);
        when(ska.isBury()).thenReturn(false);

        skaCtr.inhale();
        verify(ska, never()).setOxygenLevel(anyInt());
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

        reset(ska);
        skaCtr.takeDamage(0);
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

        reset(ska);
        skaCtr.nom(0);
        verify(ska, never()).setHp(any(Integer.class));
        verify(ska, never()).grow();
    }

    @Test
    public void update() {
        setUpMock();

        Mockito.when(ska.moveLeft()).thenReturn(new Position(1, 1));
        skaCtr.setEvent(EVENT.MoveLeft);
        skaCtr.update(room);
        verify(ska, times(1)).moveLeft();
        verify(ska, times(1)).dropScent(scentDur);
        verify(ska, times(1)).tickScentTrail();
        verify(ska).setPos(eq(new Position(1, 1)));

        reset(ska);
        Mockito.when(ska.moveRight()).thenReturn(new Position(1, 1));
        skaCtr.setEvent(EVENT.MoveRight);
        skaCtr.update(room);
        verify(ska, times(1)).moveRight();
        verify(ska, times(1)).dropScent(scentDur);
        verify(ska, times(1)).tickScentTrail();
        verify(ska).setPos(eq(new Position(1, 1)));

        reset(ska);
        Mockito.when(ska.moveUp()).thenReturn(new Position(1, 1));
        skaCtr.setEvent(EVENT.MoveUp);
        skaCtr.update(room);
        verify(ska, times(1)).moveUp();
        verify(ska, times(1)).dropScent(scentDur);
        verify(ska, times(1)).tickScentTrail();
        verify(ska).setPos(eq(new Position(1, 1)));

        reset(ska);
        Mockito.when(ska.moveDown()).thenReturn(new Position(1, 1));
        skaCtr.setEvent(EVENT.MoveDown);
        skaCtr.update(room);
        verify(ska, times(1)).moveDown();
        verify(ska, times(1)).dropScent(scentDur);
        verify(ska, times(1)).tickScentTrail();
        verify(ska).setPos(eq(new Position(1, 1)));
    }

    @Test
    public void dontCareEventUpdate() {
        setUpMock();

        when(ska.getPos()).thenReturn(new Position(5, 5));
        skaCtr.setEvent(EVENT.QuitGame);
        skaCtr.update(room);

        verify(ska, never()).setPos(any());
    }

    @Test
    public void Scent() {
        setUpMock();
        skaCtr.tickScentTrail();
        verify(ska, times(1)).tickScentTrail();
        verify(ska, times(1)).dropScent(scentDur);
    }
}
