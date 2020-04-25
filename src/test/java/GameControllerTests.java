import controller.GameController;
import controller.SkaneController;
import gui.EVENT;
import gui.Gui;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import room.Room;
import room.element.skane.Skane;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class GameControllerTests {
    private GameController game;
    private Gui gui;
    private Room room;
    private SkaneController ska_ctr;

    @Before
    public void setUp() throws IOException {
        room = Mockito.mock(Room.class);
        gui = Mockito.mock(Gui.class);
        ska_ctr = Mockito.mock(SkaneController.class);

        Mockito.when(room.getSkane()).thenReturn(new Skane(1, 1, 1, 1, 1, 1));

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
//        Mockito.verify(ska_ctr, timeout(50).atLeastOnce()).inhale();
        Mockito.verify(gui, timeout(100)).close();

//        Mockito.when(gui.getEvent()).thenReturn(EVENT.RestartGame);
//          Mockito.verify(game).restart(); TODO Verify restart
    }

    @Test
    public void playerInputs() throws IOException {
        Mockito.when(gui.getEvent()).thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.MoveDown)
                .thenReturn(EVENT.MoveDown)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.MoveLeft)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.QuitGame);
        game.start();
//        Mockito.verify(ska_ctr, atLeastOnce()).inhale();
        // Mockito.verify(ska_ctr); // TODO Move Movement down to skane controller

        Mockito.reset(gui);
        Mockito.reset(ska_ctr);
        Mockito.when(gui.getEvent())
                .thenReturn(EVENT.MoveLeft)
                .thenReturn(EVENT.MoveLeft)
                .thenReturn(EVENT.Bury)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.NullEvent)
                .thenReturn(EVENT.Bury)
                .thenReturn(EVENT.QuitGame);
        game.start();
//        Mockito.verify(ska_ctr, atLeastOnce()).inhale();
//        Mockito.verify(ska_ctr, times(2)).toggleBury();
    }
}
