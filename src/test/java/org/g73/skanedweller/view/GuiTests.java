package org.g73.skanedweller.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import org.g73.skanedweller.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

public class GuiTests {
    private Gui gui;
    private Room room;
    private RoomDrawer drawer;
    private Screen screen;
    private TerminalResizeHandler resizeHandler;
    private KeyHandler keyHandler;

    @Before
    public void setUp() throws IOException {
        room = Mockito.mock(Room.class);
        drawer = Mockito.mock(RoomDrawer.class);
        screen = Mockito.mock(Screen.class);
        resizeHandler = Mockito.mock(TerminalResizeHandler.class);
        keyHandler = Mockito.mock(KeyHandler.class);
        gui = new Gui(room, screen, resizeHandler, drawer, keyHandler);
    }

    @Test
    public void testSetRoom() {
        Room r = new Room(2000, 2000);
        assertNotEquals(gui.getRoom(), r);
        gui.setRoom(r);
        assertEquals(gui.getRoom(), r);
    }

    @Test
    public void testDrawResized() throws IOException {
        Mockito.reset(screen);

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
    public void testDraw() throws IOException {
        Mockito.when(resizeHandler.hasResized()).thenReturn(false);
        Mockito.when(resizeHandler.getLastKnownSize()).thenReturn(new TerminalSize(2, 2));
        Mockito.when(room.getHeight()).thenReturn(2);
        Mockito.when(room.getWidth()).thenReturn(2);
        gui.draw();
        Mockito.verify(room, Mockito.never()).setSize(any(Integer.class), any(Integer.class));

        InOrder order = Mockito.inOrder(screen, drawer);
        order.verify(screen).clear();
        order.verify(drawer).draw(this.room);
        order.verify(screen).refresh();
    }

    @Test
    public void testInputHandlerStart() {
        InputHandler inputHandler = Mockito.mock(InputHandler.class);

        gui.startInputHandler(inputHandler);
        Mockito.verify(inputHandler, times(1)).setDaemon(true);
        Mockito.verify(inputHandler, times(1)).start();

        gui.stopInputHandler();
        Mockito.verify(inputHandler, times(1)).stop();
    }

    @Test
    public void testStartCloseScreen() throws IOException {
        Mockito.reset(screen);
        Gui gui = new Gui(room, screen, null);
        Mockito.verify(screen).doResizeIfNecessary();
        Mockito.verify(screen).setCursorPosition(null);
        Mockito.verify(screen).startScreen();

        gui.close();
        Mockito.verify(screen).close();
    }

     @Test
     public void testResizeHandler() throws IOException {
         Screen screen = Mockito.mock(Screen.class);
         Gui gui = new Gui(room, screen, resizeHandler);

         Mockito.when(resizeHandler.getLastKnownSize())
                 .thenReturn(new TerminalSize(100, 100));
         assertEquals(gui.getTermSize(), new TerminalSize(100, 100));
     }

    @Test
    public void testDefaultInputHandlerStart() {
        assertNull(gui.getInputHandler());
        gui.startInputHandler();
        assertNotNull(gui.getInputHandler());
        gui.stopInputHandler();
    }

    @Test
    public void testGetEvents() {
        InputHandler inputHandler = Mockito.mock(InputHandler.class);
        KeyStroke ks = Mockito.mock(KeyStroke.class);

        Mockito.when(inputHandler.getLastKey()).thenReturn(ks).thenReturn(null);
        Mockito.when(keyHandler.processKey(ks)).thenReturn(EVENT.MoveDown);
        Mockito.when(keyHandler.processKey(null)).thenReturn(EVENT.NullEvent);
        gui.startInputHandler(inputHandler);
        assertEquals(EVENT.MoveDown, gui.getEvent());
        Mockito.verify(inputHandler, times(1)).getLastKey();
        Mockito.verify(keyHandler, times(1)).processKey(eq(ks));


        gui.releaseKeys();
        assertEquals(gui.getEvent(), EVENT.NullEvent);
        Mockito.verify(inputHandler, times(2)).getLastKey();
        Mockito.verify(keyHandler, times(1)).processKey(eq(null));
    }
}
