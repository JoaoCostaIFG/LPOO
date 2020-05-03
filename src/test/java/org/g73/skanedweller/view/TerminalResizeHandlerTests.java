package org.g73.skanedweller.view;

import com.googlecode.lanterna.TerminalSize;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TerminalResizeHandlerTests {
    private TerminalResizeHandler handler;
    private TerminalSize size;

    @Before
    public void setUp() {
        this.size = new TerminalSize(200, 200);
        this.handler = new TerminalResizeHandler(size);
    }

    @Test
    public void resize() {
        assertFalse(handler.hasResized());
        assertEquals(this.size, handler.getLastKnownSize());
        assertFalse(handler.hasResized());

        TerminalSize newSize = new TerminalSize(150, 150);
        handler.onResized(null, newSize);

        assertTrue(handler.hasResized());
        assertNotEquals(this.size, handler.getLastKnownSize());
        assertEquals(newSize, handler.getLastKnownSize());
        assertFalse(handler.hasResized());
    }

}
