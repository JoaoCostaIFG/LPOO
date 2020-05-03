package org.g73.skanedweller.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.g73.skanedweller.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GuiTests {
    private Gui gui;
    private Room room;
    private RoomDrawer drawer;
    private Screen screen;
    private TerminalResizeHandler resizeHandler;

    @Before
    public void setUp() {
        room = Mockito.mock(Room.class);
        drawer = Mockito.mock(RoomDrawer.class);
        screen = Mockito.mock(Screen.class);
        resizeHandler = Mockito.mock(TerminalResizeHandler.class);
        gui = new Gui(room, screen, resizeHandler, drawer);
    }

    @Test
    public void drawResized() throws IOException {
        Mockito.when(resizeHandler.hasResized()).thenReturn(true);
        TerminalSize size = new TerminalSize(2, 2);
        Mockito.when(resizeHandler.getLastKnownSize()).thenReturn(size);

        gui.draw();
        Mockito.verify(room).setSize(2, 2);
        Mockito.verify(screen).doResizeIfNecessary();

        InOrder order = Mockito.inOrder(screen, drawer);
        order.verify(screen).clear();
        order.verify(drawer).draw(this.room);
        order.verify(screen).refresh();
    }

    @Test
    public void draw() throws IOException {
        Mockito.when(resizeHandler.hasResized()).thenReturn(false);
        gui.draw();

        InOrder order = Mockito.inOrder(screen, drawer);
        order.verify(screen).clear();
        order.verify(drawer).draw(this.room);
        order.verify(screen).refresh();
    }

    @Test
    public void processEvents() throws IOException, InterruptedException {
        KeyStroke a = new KeyStroke('a', false, false);
        KeyStroke space = new KeyStroke(' ', false, false);
        KeyStroke quit = new KeyStroke(KeyType.EOF);

        assertEquals(EVENT.NullEvent, gui.getEvent());
        Mockito.when(screen.readInput()).thenReturn(a);
        gui.startInputHandler();
        TimeUnit.MILLISECONDS.sleep(10);
        assertEquals(EVENT.MoveLeft, gui.getEvent());
        Mockito.when(screen.readInput()).thenReturn(space);
        TimeUnit.MILLISECONDS.sleep(10);
        assertEquals(EVENT.Bury, gui.getEvent());
        Mockito.when(screen.readInput()).thenReturn(quit);
        TimeUnit.MILLISECONDS.sleep(10);
        assertEquals(EVENT.QuitGame, gui.getEvent());
    }
}
