package org.g73.skanedweller.view;

import com.googlecode.lanterna.TerminalSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TerminalResizeHandlerTests {
    private TerminalResizeHandler handler;
    private TerminalSize size;

    @BeforeEach
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
