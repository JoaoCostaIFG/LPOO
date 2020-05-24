package org.g73.skanedweller.controller;

import com.googlecode.lanterna.TerminalSize;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.view.EVENT;
import org.g73.skanedweller.view.Gui;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameControllerTests {
    private GameController game;
    private Gui gui;
    private Room room;
    private SkaneController ska_ctr;

    @Before
    public void setUp() {
        room = Mockito.mock(Room.class);
        gui = Mockito.mock(Gui.class);
        ska_ctr = Mockito.mock(SkaneController.class);

        Mockito.when(room.getSkane()).thenReturn(new Skane(1, 1, 1, 1, 1, 1));
        Mockito.when(gui.getTermSize()).thenReturn(new TerminalSize(10, 10));

        this.game = new GameController(room, gui, ska_ctr);
        assertEquals(room, game.getRoom());
    }

    @Test
    public void testExitInputs() throws IOException {
        Mockito.when(gui.getEvent())
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.QuitGame);
        game.start();

        // Passes when closed is called within 50ms
        Mockito.verify(ska_ctr, timeout(50).atLeastOnce()).update(eq(this.room));
        Mockito.verify(gui, timeout(100)).close();
    }

    @Test
    public void testRestartGame() throws IOException {
        Mockito.when(gui.getEvent())
                .thenReturn(EVENT.RestartGame)
                .thenReturn(EVENT.QuitGame);
        game.start();

        InOrder orderGuiCalls = Mockito.inOrder(gui);

        orderGuiCalls.verify(gui).startInputHandler();
        orderGuiCalls.verify(gui).draw();
        orderGuiCalls.verify(gui).getEvent();
        orderGuiCalls.verify(gui).releaseKeys();
        orderGuiCalls.verify(gui).stopInputHandler();

        orderGuiCalls.verify(gui).setRoom(any(Room.class));

        orderGuiCalls.verify(gui).startInputHandler();
        orderGuiCalls.verify(gui).draw();
        orderGuiCalls.verify(gui).getEvent();
        orderGuiCalls.verify(gui).releaseKeys();
        orderGuiCalls.verify(gui).close();
    }

    @Test
    public void testPlayerInputs() throws IOException {
        Mockito.when(gui.getEvent())
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.MoveDown)
                .thenReturn(EVENT.MoveDown)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.Bury)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.MoveLeft)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.QuitGame);
        game.start();
        Mockito.verify(ska_ctr, atLeast(9)).update(this.room);
        Mockito.verify(ska_ctr, times(2)).
                setEvent(EVENT.MoveDown);
        Mockito.verify(ska_ctr, times(1)).
                setEvent(EVENT.Bury);
        Mockito.verify(ska_ctr, never()).setEvent(EVENT.QuitGame);
    }

    @Test
    public void testSpawners() throws IOException {
        List<Spawner> spawners = game.getSpawners();
        int numSpawners = spawners.size();
        assertNotEquals(numSpawners, 0); // 3 types of enemies, 1 spawner for each
        verify(room, times(numSpawners)).addObserver(any(Spawner.class));
        for (Spawner s : spawners)
            verify(room).addObserver(s);

        Spawner spawnerMock = mock(Spawner.class);
        spawners.clear();
        spawners.add(spawnerMock);
        game.setSpawners(spawners);

        Mockito.when(gui.getEvent())
                .thenReturn(EVENT.QuitGame);
        game.start();

        for (Spawner s : spawners)
            verify(s).update(room);
    }


    @Test
    public void testTimeBetweenFrames() throws IOException {
        Mockito.when(gui.getEvent())
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.QuitGame);

        long startTime = System.currentTimeMillis();
        game.start();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        assertTrue(elapsedTime >= 60 && elapsedTime <= 70);
    }
}
