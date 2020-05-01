package view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.Position;
import model.element.skane.Skane;
import model.element.skane.SkaneBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static com.googlecode.lanterna.TextColor.Factory.fromString;
import static org.mockito.Mockito.*;

public class SkaneViewTests {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor bgDark = fromString("#212833");
    private static final TextColor green = fromString("#76A15D");
    private static final TextColor orange = fromString("#D68445");

    private static final char skaChar = 'S';
    private static final char skaBodyChar = 'o';
    private static final char skaBuryChar = 'X';
    private static final char skaBodyBuryChar = 'x';

    private static final int skaFov = 5;

    private static final int skaBodyX = 4;
    private static final int skaBodyY = 4;
    private static final int skaX = 3;
    private static final int skaY = 3;
    private static final int maxOxy = 100;

    private TextGraphics gra;
    private Skane ska;
    private SkaneBody skaBody;

    // TODO check green and orange colors

    @Before
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
    public void drawUnburySkane() {
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
                .setBackgroundColor(bgDark);
    }

    @Test
    public void drawBurySkane() {
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
                .setBackgroundColor(bgDark);
    }

    @After
    public void endChecks() {
        Mockito.verify(gra, atLeastOnce())
                .setBackgroundColor(bg);

        Mockito.verify(gra, times(1))
                .enableModifiers(SGR.BOLD);
        Mockito.verify(gra, times(1))
                .disableModifiers(SGR.BOLD);
    }
}
