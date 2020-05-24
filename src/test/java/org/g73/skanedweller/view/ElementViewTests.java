package org.g73.skanedweller.view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.view.element_views.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.lanterna.TextColor.Factory.fromString;
import static org.mockito.Mockito.*;

public class ElementViewTests {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor blue = fromString("#0E91E7");
    private static final TextColor brightRed = fromString("#C00000");
    private static final TextColor purple = fromString("#8558AD");
    private static final TextColor red = fromString("#844F4E");
    private static final TextColor yellow = fromString("#988D3D");

    private static final TextCharacter civieChar = new TextCharacter('C', blue, bg);
    private static final TextCharacter laserChar = new TextCharacter('#', brightRed, bg);
    private static final TextCharacter meleeChar = new TextCharacter('M', red, bg);
    private static final TextCharacter rangedChar = new TextCharacter('R', yellow, bg);
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
    public void rangedGuyDraw() {
        Laser laser = Mockito.mock(Laser.class);
        RangedGuy rg = Mockito.mock(RangedGuy.class);
        List<Laser> lasers = new ArrayList<>();
        lasers.add(laser);
        when(rg.getLasers())
                .thenReturn(lasers);

        setUpElementPos(rg);

        LaserView lv = Mockito.mock(LaserView.class);
        new RangedGuyView(lv).draw(gra, rg);
        Mockito.verify(gra).setCharacter(60, 65, rangedChar);

        Mockito.verify(rg).getLasers();
        Mockito.verify(lv).draw(gra, laser);

        checkPosCalledOnce(rg);
    }

    @Test
    public void laserReadyDraw() {
        Laser laser = Mockito.mock(Laser.class);
        when(laser.getReadiness())
                .thenReturn(true);

        setUpElementPos(laser);

        new LaserView().draw(gra, laser);
        Mockito.verify(laser).getReadiness();
        Mockito.verify(gra).setCharacter(60, 65, laserChar);

        checkPosCalledOnce(laser);
    }

    @Test
    public void laserNotReadyDraw() {
        Laser laser = Mockito.mock(Laser.class);
        when(laser.getReadiness())
                .thenReturn(false);

        setUpElementPos(laser);

        new LaserView().draw(gra, laser);
        Mockito.verify(laser).getReadiness();
        Mockito.verify(gra, never()).setCharacter(60, 65, laserChar);

        Mockito.verify(laser, never()).getPos();
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
