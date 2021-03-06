package org.g73.skanedweller.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.view.element_views.RoomView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class RoomViewTests {
    private static final int skaFov = 5;

    private static final int width = 300;
    private static final int height = 100;
    private static final int skaX = 3;
    private static final int skaY = 3;

    private Colors colors = new Colors("colors");
    private TextCharacter bgChar;
    private TextCharacter bgDarkChar;
    private TextGraphics gra;
    private Room room;

    public RoomViewTests() throws IOException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        this.gra = Mockito.mock(TextGraphics.class);
        this.room = Mockito.mock(Room.class);

        Mockito.when(room.getWidth())
                .thenReturn(width);
        Mockito.when(room.getHeight())
                .thenReturn(height);

        Mockito.when(room.getSkanePos())
                .thenReturn(new Position(skaX, skaY));

        // colors
        this.bgChar = new TextCharacter(' ', colors.getColor("bg"),
                colors.getColor("bg"));
        this.bgDarkChar = new TextCharacter(' ', colors.getColor("bgDark"),
                colors.getColor("bgDark"));
    }

    @Test
    public void roomUnburySkaneDraw() {
        Mockito.when(room.isSkaneBury())
                .thenReturn(false);

        new RoomView(colors, skaFov).draw(gra, room);

        Mockito.verify(gra, times(1))
                .fillRectangle(new TerminalPosition(0, 0),
                        new TerminalSize(width, height),
                        bgChar);
        Mockito.verify(gra, times(0))
                .setCharacter(anyInt(), anyInt(), any(TextCharacter.class));
    }

    @Test
    public void roomBurySkaneDraw() {
        Mockito.when(room.isSkaneBury())
                .thenReturn(true);

        new RoomView(colors, skaFov).draw(gra, room);

        Mockito.verify(gra, times(1))
                .fillRectangle(new TerminalPosition(0, 0),
                        new TerminalSize(width, height),
                        bgDarkChar);


        for (int i = 0; i < room.getWidth(); ++i) {
            for (int j = 0; j < room.getHeight(); ++j) {
                if (Math.pow(skaX - i, 2) + Math.pow(skaY - j, 2) < skaFov * skaFov)
                    Mockito.verify(gra, times(1))
                            .setCharacter(i, j, bgChar);
                else
                    Mockito.verify(gra, never())
                            .setCharacter(i, j, bgChar);
            }
        }
    }
}
