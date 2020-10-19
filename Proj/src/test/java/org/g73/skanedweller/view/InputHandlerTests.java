package org.g73.skanedweller.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerTests {
    private InputHandler inputHandler;
    private Screen screen;

    @BeforeEach
    public void setUp() {
        screen = Mockito.mock(Screen.class);
        inputHandler = new InputHandler(screen);
    }

    @Test
    public void testStartThread() throws InterruptedException {
        inputHandler.start();
        Thread t = inputHandler.getThread();

        assertTrue(t.isAlive());
        assertFalse(t.isDaemon());

        inputHandler.stop();
        Thread.sleep(100);
        assertFalse(t.isAlive());
    }

    @Test
    public void testStartDaemonThread() throws InterruptedException {
        inputHandler.setDaemon(true);
        inputHandler.start();
        Thread t = inputHandler.getThread();

        assertTrue(t.isAlive());
        assertTrue(t.isDaemon());

        inputHandler.stop();
        Thread.sleep(100);
        assertFalse(t.isAlive());
    }

    @Test
    public void testNotStarted() {
        Thread t = inputHandler.getThread();
        assertNull(t);

        assertFalse(inputHandler.isAlive());
        assertNull(inputHandler.getLastKey());
    }

    @Test
    public void testStarterdIsAlive() throws InterruptedException {
        Thread t;

        inputHandler.start();
        assertTrue(inputHandler.isAlive());
        t = inputHandler.getThread();
        assertTrue(t.isAlive());

        inputHandler.stop();
        Thread.sleep(100);
        assertFalse(inputHandler.isAlive());
        t = inputHandler.getThread();
        assertNotNull(t);
        assertFalse(t.isAlive());
    }

    @Test
    public void testState() {
        assertFalse(inputHandler.isDaemon());
        inputHandler.setDaemon(true);
        assertTrue(inputHandler.isDaemon());
        inputHandler.setDaemon(false);
        assertFalse(inputHandler.isDaemon());
    }

    @Test
    public void testGetKey() throws IOException, InterruptedException {
        KeyStroke ks = new KeyStroke('s', false, false);
        Mockito.when(screen.readInput())
                .thenReturn(ks)
                .thenReturn(null);

        inputHandler.start();
        Thread.sleep(100);
        // Mockito.verify(screen, Mockito.timeout(100)).readInput();
        assertEquals(inputHandler.getLastKey(), ks);
        assertNull(inputHandler.getLastKey());

        Mockito.reset(screen);
        Mockito.when(screen.readInput())
                .thenReturn(ks)
                .thenReturn(null);
        Thread.sleep(100);
        // Mockito.verify(screen, Mockito.timeout(100)).readInput();
        assertNotNull(inputHandler.getLastKey());
    }

    @Test
    public void testScreenClosed() throws IOException, InterruptedException {
        Mockito.when(screen.readInput())
                .thenThrow(new IOException());
        inputHandler.start();
        Thread.sleep(200);
        assertEquals(inputHandler.isAlive(), false);
    }
}
