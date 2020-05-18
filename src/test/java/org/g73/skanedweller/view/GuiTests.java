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
import org.junit.experimental.theories.Theories;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

    private void testKey(KeyEventPair ke) throws IOException {
        Mockito.when(screen.readInput())
                .thenReturn(ke.ks)
                .thenReturn(null);

        boolean found = false;
        while (!found) {
            if (gui.getEvent() == ke.ev)
                found = true;
        }
        assertEquals(gui.getEvent(), ke.ev);

        gui.releaseKeys();
        assertEquals(gui.getEvent(), EVENT.NullEvent);
    }

    @Test
    public void processEvents() throws IOException, InterruptedException {
        //     List<KeyEventPair> strokes = new ArrayList<>();
        //     strokes.add(new KeyEventPair('a', EVENT.MoveLeft));
        //     strokes.add(new KeyEventPair('A', EVENT.MoveLeft));
        //     strokes.add(new KeyEventPair('d', EVENT.MoveRight));
        //     strokes.add(new KeyEventPair('D', EVENT.MoveRight));
        //     strokes.add(new KeyEventPair('w', EVENT.MoveUp));
        //     strokes.add(new KeyEventPair('W', EVENT.MoveUp));
        //     strokes.add(new KeyEventPair('s', EVENT.MoveDown));
        //     strokes.add(new KeyEventPair('S', EVENT.MoveDown));
        //     strokes.add(new KeyEventPair('r', EVENT.RestartGame));
        //     strokes.add(new KeyEventPair('R', EVENT.RestartGame));
        //     strokes.add(new KeyEventPair('q', EVENT.QuitGame));
        //     strokes.add(new KeyEventPair('Q', EVENT.QuitGame));
        //     strokes.add(new KeyEventPair(' ', EVENT.Bury));
        //     strokes.add(new KeyEventPair(KeyType.ArrowLeft, EVENT.MoveLeft));
        //     strokes.add(new KeyEventPair(KeyType.ArrowRight, EVENT.MoveRight));
        //     strokes.add(new KeyEventPair(KeyType.ArrowUp, EVENT.MoveUp));
        //     strokes.add(new KeyEventPair(KeyType.ArrowDown, EVENT.MoveDown));
        //     strokes.add(new KeyEventPair(KeyType.Escape, EVENT.QuitGame));
        //     strokes.add(new KeyEventPair(KeyType.EOF, EVENT.QuitGame));

        //     gui.startInputHandler();
        //     assertEquals(EVENT.NullEvent, gui.getEvent());

        //     for (KeyEventPair ke : strokes)
        //         testKey(ke);

        //     gui.releaseKeys();
        //     assertEquals(gui.getEvent(), EVENT.NullEvent);
    }

    private static class TestRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @Test
    public void testInputHandlerStart() {
        TestRunnable testRunnable = new TestRunnable();
        Thread inputHandler = new Thread(testRunnable);
        gui.startInputHandler(inputHandler);

        assertTrue(inputHandler.isAlive());
        assertTrue(inputHandler.isDaemon());
        assertFalse(inputHandler.isInterrupted());

        gui.stopInputHandler();
        assertTrue(inputHandler.isInterrupted());

        synchronized (testRunnable) {
            testRunnable.notifyAll(); // stop runnable
        }
    }

    @Test
    public void testStartCloseScreen() throws IOException {
        Gui gui = new Gui(room, screen, null);
        Mockito.verify(screen).startScreen();
        Mockito.verify(screen).setCursorPosition(null);
        Mockito.verify(screen).doResizeIfNecessary();

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
}
