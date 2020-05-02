package org.g73.skanedweller.controller;

import com.googlecode.lanterna.TerminalSize;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.view.EVENT;
import org.g73.skanedweller.view.Gui;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

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
    }

    @Test
    public void ExitInputs() throws IOException {
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
    public void restartGame() throws IOException {
        Mockito.when(gui.getEvent())
                .thenReturn(EVENT.RestartGame)
                .thenReturn(EVENT.QuitGame);
        game.start();
        Mockito.verify(gui).setRoom(any(Room.class)); // TODO Verify restart
    }

    @Test
    public void playerInputs() throws IOException {
        Mockito.when(gui.getEvent()).thenReturn(EVENT.NullEvent)
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
}
