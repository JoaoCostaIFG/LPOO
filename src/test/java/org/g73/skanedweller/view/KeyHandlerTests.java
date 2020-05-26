package org.g73.skanedweller.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KeyHandlerTests {
    private KeyHandler keyHandler;

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

    @Before
    public void setUp() {
        keyHandler = new KeyHandler();
    }
    
    @Test
    public void testProcessEvents() {
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

        assertEquals(EVENT.NullEvent, keyHandler.processKey(null));

        for (KeyEventPair ke : strokes)
            assertEquals(ke.ev, keyHandler.processKey(ke.ks));
    }

}
