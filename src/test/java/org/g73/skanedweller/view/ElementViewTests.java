package org.g73.skanedweller.view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.g73.skanedweller.model.element.Wall;
import org.g73.skanedweller.view.element_views.CivieView;
import org.g73.skanedweller.view.element_views.MeleeGuyView;
import org.g73.skanedweller.view.element_views.WallView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.googlecode.lanterna.TextColor.Factory.fromString;
import static org.mockito.Mockito.times;

public class ElementViewTests {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor blue = fromString("#0E91E7");
    private static final TextColor purple = fromString("#8558AD");
    private static final TextColor red = fromString("#844F4E");

    private static final TextCharacter civieChar = new TextCharacter('C', blue, bg);
    private static final TextCharacter meleeChar = new TextCharacter('M', red, bg);
    private static final TextCharacter wallChar = new TextCharacter('#', purple, bg);

    private TextGraphics gra;

    @Before
    public void setUp() {
        this.gra = Mockito.mock(TextGraphics.class);
    }

    private void setUpElementPos(Element e) {
        Mockito.when(e.getX()).thenReturn(60);
        Mockito.when(e.getY()).thenReturn(65);
    }

    public void checkPosCalledOnce(Element e) {
        Mockito.verify(e, times(1))
                .getX();
        Mockito.verify(e, times(1))
                .getY();
    }

    @Test
    public void civieDraw() {
        Civilian civie = Mockito.mock(Civilian.class);
        setUpElementPos(civie);

        new CivieView().draw(gra, civie);
        Mockito.verify(gra, times(1))
                .setCharacter(60, 65, civieChar);

        checkPosCalledOnce(civie);
    }

    @Test
    public void meleeGuyDraw() {
        MeleeGuy melee = Mockito.mock(MeleeGuy.class);
        setUpElementPos(melee);

        new MeleeGuyView().draw(gra, melee);
        Mockito.verify(gra, times(1))
                .setCharacter(60, 65, meleeChar);

        checkPosCalledOnce(melee);
    }

    @Test
    public void wallDraw() {
        Wall wall = Mockito.mock(Wall.class);
        setUpElementPos(wall);

        new WallView().draw(gra, wall);
        Mockito.verify(gra, times(1))
                .setCharacter(60, 65, wallChar);

        checkPosCalledOnce(wall);
    }
}
