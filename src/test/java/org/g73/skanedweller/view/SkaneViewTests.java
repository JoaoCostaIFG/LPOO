package org.g73.skanedweller.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.AbstractTextGraphics;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.g73.skanedweller.view.element_views.SkaneView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SkaneViewTests {
    private static final char skaChar = 'S';
    private static final char skaBodyChar = 'o';
    private static final char skaBuryChar = 'X';
    private static final char skaBodyBuryChar = 'x';

    private static final int skaBodyX = 4;
    private static final int skaBodyY = 4;
    private static final int skaX = 3;
    private static final int skaY = 3;
    private static final int maxOxy = 100;

    private TextGraphics gra;
    private Skane ska;
    private SkaneBody skaBody;

    @BeforeEach
    public void setUp() {
        this.gra = Mockito.mock(TextGraphics.class);
        this.ska = Mockito.mock(Skane.class);
        this.skaBody = Mockito.mock(SkaneBody.class);

        Mockito.when(skaBody.getX())
                .thenReturn(skaBodyX);
        Mockito.when(skaBody.getY())
                .thenReturn(skaBodyY);
        Mockito.when(skaBody.getPos())
                .thenReturn(new Position(skaBodyX, skaBodyY));

        Mockito.when(ska.getX())
                .thenReturn(skaX);
        Mockito.when(ska.getY())
                .thenReturn(skaY);
        Mockito.when(ska.getPos())
                .thenReturn(new Position(skaX, skaY));

        Mockito.when(ska.getMaxOxygenLevel())
                .thenReturn(maxOxy);
        Mockito.when(ska.getOxygenLevel())
                .thenReturn(maxOxy);

        List<SkaneBody> skaBodyList = new ArrayList<>();
        skaBodyList.add(skaBody);
        Mockito.when(ska.getBody())
                .thenReturn(skaBodyList);
    }

    @Test
    public void testDrawUnburySkane() {
        Mockito.when(ska.isBury())
                .thenReturn(false);

        new SkaneView().draw(gra, ska);

        Mockito.verify(gra, never())
                .setCharacter(skaBodyX, skaBodyY, skaBodyBuryChar);
        Mockito.verify(gra, never())
                .setCharacter(skaX, skaY, skaBuryChar);

        Mockito.verify(gra, times(1))
                .setCharacter(skaBodyX, skaBodyY, skaBodyChar);
        Mockito.verify(gra, times(1))
                .setCharacter(skaX, skaY, skaChar);

        Mockito.verify(gra, never())
                .setBackgroundColor(Colors.bgDark);
    }

    @Test
    public void testDrawBurySkane() {
        Mockito.when(ska.isBury())
                .thenReturn(true);

        new SkaneView().draw(gra, ska);

        Mockito.verify(gra, times(1))
                .setCharacter(skaBodyX, skaBodyY, skaBodyBuryChar);
        Mockito.verify(gra, times(1))
                .setCharacter(skaX, skaY, skaBuryChar);

        Mockito.verify(gra, never())
                .setCharacter(skaBodyX, skaBodyY, skaBodyChar);
        Mockito.verify(gra, never())
                .setCharacter(skaX, skaY, skaChar);

        Mockito.verify(gra, never())
                .setBackgroundColor(Colors.bgDark);
    }

    @Test
    public void testDrawBurySkaneBodyFOV() {
        Mockito.when(ska.isBury())
                .thenReturn(true);

        when(skaBody.getX())
                .thenReturn(8);
        when(skaBody.getY())
                .thenReturn(3);
        Mockito.when(skaBody.getPos())
                .thenReturn(new Position(8, 3));

        new SkaneView().draw(gra, ska);
        Mockito.verify(gra, never())
                .setBackgroundColor(Colors.bgDark);

        Mockito.reset(gra);
        when(skaBody.getX())
                .thenReturn(9);
        when(skaBody.getY())
                .thenReturn(3);
        Mockito.when(skaBody.getPos())
                .thenReturn(new Position(9, 3));

        new SkaneView().draw(gra, ska);
        Mockito.verify(gra).
                setBackgroundColor(Colors.bgDark);
    }

    private class TextGraphicsSpy extends AbstractTextGraphics {
        private int width, height;
        private TextCharacter plane[][];

        public TextGraphicsSpy(int width, int height) {
            this.width = width;
            this.height = height;
            this.plane = new TextCharacter[height][width];
            for (int i = 0; i < width; ++i) {
                for (int j = 0; j < height; ++j)
                    plane[i][j] = null;
            }
        }

        @Override
        public TerminalSize getSize() {
            return new TerminalSize(width, height);
        }

        @Override
        public TextGraphics setCharacter(int column, int row, TextCharacter character) {
            plane[column][row] = character;
            return null;
        }

        @Override
        public TextCharacter getCharacter(int column, int row) {
            return plane[column][row];
        }
    }

    @Test
    public void testOxyIndicatorDraw() {
        // making it so green needs to be called at least once
        Mockito.when(ska.getOxygenLevel())
                .thenReturn(maxOxy / 2);

        new SkaneView().draw(gra, ska);
        Mockito.verify(gra, atLeastOnce()).setForegroundColor(Colors.green);

        /* using spy to check the chars */
        TextGraphicsSpy graSpy = new TextGraphicsSpy(200, 200);
        List<SkaneBody> bodies = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            SkaneBody b = Mockito.mock(SkaneBody.class);
            Mockito.when(b.getX())
                    .thenReturn(3 + i);
            Mockito.when(b.getY())
                    .thenReturn(3);
            Mockito.when(b.getPos())
                    .thenReturn(new Position(3 + i, 3));

            bodies.add(b);
        }
        Mockito.when(ska.getBody())
                .thenReturn(bodies);
        Mockito.when(ska.getSize())
                .thenReturn(bodies.size());

        new SkaneView().draw(graSpy, ska);

        TextCharacter bodyOrange = new TextCharacter('o', Colors.orange, Colors.bg, SGR.BOLD);
        for (int i = 0; i < 6; ++i) {
            SkaneBody b = bodies.get(i);
            assertEquals(graSpy.getCharacter(b.getX(), b.getY()), bodyOrange);
        }
        TextCharacter bodyGreen = new TextCharacter('o', Colors.green, Colors.bg, SGR.BOLD);
        for (int i = 6; i < 10; ++i) {
            SkaneBody b = bodies.get(i);
            assertEquals(graSpy.getCharacter(b.getX(), b.getY()), bodyGreen);
        }

        assertEquals(graSpy.getCharacter(skaX, skaY), new TextCharacter('S', Colors.green, Colors.bg, SGR.BOLD));
    }

    @AfterEach
    public void endChecks() {
        Mockito.verify(gra, atLeastOnce())
                .setBackgroundColor(Colors.bg);

        Mockito.verify(gra, times(1))
                .enableModifiers(SGR.BOLD);
        Mockito.verify(gra, times(1))
                .disableModifiers(SGR.BOLD);
    }
}
