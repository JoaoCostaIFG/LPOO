package view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.Position;
import model.Room;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import view.element_views.RoomView;

import static com.googlecode.lanterna.TextColor.Factory.fromString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

public class RoomViewTests {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor bgDark = fromString("#212833");
    private static final TextCharacter bgChar = new TextCharacter(' ', bg, bg);
    private static final TextCharacter bgDarkChar = new TextCharacter(' ', bgDark, bgDark);

    private static final int skaFov = 5;

    private static final int width = 300;
    private static final int height = 100;
    private static final int skaX = 3;
    private static final int skaY = 3;

    private TextGraphics gra;
    private Room room;

    @Before
    public void setUp() {
        this.gra = Mockito.mock(TextGraphics.class);
        this.room = Mockito.mock(Room.class);

        Mockito.when(room.getWidth())
                .thenReturn(width);
        Mockito.when(room.getHeight())
                .thenReturn(height);

        Mockito.when(room.getSkanePos())
                .thenReturn(new Position(skaX, skaY));
    }

    @Test
    public void roomUnburySkaneDraw() {
        Mockito.when(room.isSkaneBury())
                .thenReturn(false);

        new RoomView().draw(gra, room);

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

        new RoomView().draw(gra, room);

        Mockito.verify(gra, times(1))
                .fillRectangle(new TerminalPosition(0, 0),
                        new TerminalSize(width, height),
                        bgDarkChar);


        for (int i = skaX - skaFov; i < skaX + skaFov; ++i) {
            for (int j = skaY - skaFov; j < skaY + skaFov; ++j) {
                if (Math.pow(skaX - i, 2) + Math.pow(skaY - j, 2) < skaFov * skaFov) {
                    Mockito.verify(gra, times(1))
                            .setCharacter(i, j, bgChar);
                }
            }
        }
    }
}
