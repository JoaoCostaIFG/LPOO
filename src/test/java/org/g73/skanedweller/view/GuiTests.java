package org.g73.skanedweller.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.g73.skanedweller.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class GuiTests {
    private Gui gui;
    private Room room;
    private RoomDrawer drawer;
    private Screen screen;
    private TerminalResizeHandler resizeHandler;

    @Before
    public void setUp() throws IOException {
        room = Mockito.mock(Room.class);
        drawer = Mockito.mock(RoomDrawer.class);
        screen = Mockito.mock(Screen.class);
        resizeHandler = Mockito.mock(TerminalResizeHandler.class);
        gui = new Gui(room, screen, resizeHandler, drawer);
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

    private static class KeyEventPair {
        public KeyStroke ks;
        public EVENT ev;

        public KeyEventPair(KeyStroke ks, EVENT ev) {
            this.ks = ks;
            this.ev = ev;
        }

        public KeyEventPair(char c, EVENT ev) {
            this(new KeyStroke(c, false, false), ev);
        }

        public KeyEventPair(KeyType c, EVENT ev) {
            this(new KeyStroke(c), ev);
        }
    }

    private void testKey(InputHandler inputHandler, KeyEventPair ke) {
        Mockito.when(inputHandler.getLastKey())
                .thenReturn(ke.ks)
                .thenReturn(null);

        assertEquals(gui.getEvent(), ke.ev);

        gui.releaseKeys();
        assertEquals(gui.getEvent(), EVENT.NullEvent);
    }

    @Test
    public void processEvents() {
        List<KeyEventPair> strokes = new ArrayList<>();
        strokes.add(new KeyEventPair('a', EVENT.MoveLeft));
        strokes.add(new KeyEventPair('A', EVENT.MoveLeft));
        strokes.add(new KeyEventPair('d', EVENT.MoveRight));
        strokes.add(new KeyEventPair('D', EVENT.MoveRight));
        strokes.add(new KeyEventPair('w', EVENT.MoveUp));
        strokes.add(new KeyEventPair('W', EVENT.MoveUp));
        strokes.add(new KeyEventPair('s', EVENT.MoveDown));
        strokes.add(new KeyEventPair('S', EVENT.MoveDown));
        strokes.add(new KeyEventPair('r', EVENT.RestartGame));
        strokes.add(new KeyEventPair('R', EVENT.RestartGame));
        strokes.add(new KeyEventPair('q', EVENT.QuitGame));
        strokes.add(new KeyEventPair('Q', EVENT.QuitGame));
        strokes.add(new KeyEventPair(' ', EVENT.Bury));
        strokes.add(new KeyEventPair(KeyType.ArrowLeft, EVENT.MoveLeft));
        strokes.add(new KeyEventPair(KeyType.ArrowRight, EVENT.MoveRight));
        strokes.add(new KeyEventPair(KeyType.ArrowUp, EVENT.MoveUp));
        strokes.add(new KeyEventPair(KeyType.ArrowDown, EVENT.MoveDown));
        strokes.add(new KeyEventPair(KeyType.Escape, EVENT.QuitGame));
        strokes.add(new KeyEventPair(KeyType.EOF, EVENT.QuitGame));

        InputHandler inputHandler = Mockito.mock(InputHandler.class);
        gui.startInputHandler(inputHandler);

        Mockito.when(inputHandler.getLastKey())
                .thenReturn(null);
        assertEquals(EVENT.NullEvent, gui.getEvent());

        for (KeyEventPair ke : strokes)
            testKey(inputHandler, ke);

        gui.releaseKeys();
        assertEquals(gui.getEvent(), EVENT.NullEvent);
    }

    @Test
    public void testInputHandlerStart() {
        InputHandler inputHandler = Mockito.mock(InputHandler.class);

        gui.startInputHandler(inputHandler);
        Mockito.verify(inputHandler, Mockito.times(1)).setDaemon(true);
        Mockito.verify(inputHandler, Mockito.times(1)).start();

        gui.stopInputHandler();
        Mockito.verify(inputHandler, Mockito.times(1)).stop();
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
        TerminalSize init_size = new TerminalSize(80, 60);
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(init_size).createTerminal();
        TerminalResizeHandler resizeHandler = Mockito.mock(TerminalResizeHandler.class);
        terminal.addResizeListener(resizeHandler);

        Screen screen = new TerminalScreen(terminal);
        Gui gui = new Gui(room, screen, resizeHandler);

        Mockito.when(resizeHandler.getLastKnownSize()).thenReturn(new TerminalSize(100, 100));
        assertEquals(gui.getTermSize(), new TerminalSize(100, 100));
    }

    @Test
    public void testDefaultInputHandlerStart() {
        assertNull(gui.getInputHandler());
        gui.startInputHandler();
        assertNotNull(gui.getInputHandler());
        gui.stopInputHandler();
    }
}
