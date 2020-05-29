package org.g73.skanedweller.view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.view.element_views.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ElementViewTests {
    private TextCharacter civieChar;
    private TextCharacter laserChar;
    private TextCharacter meleeChar;
    private TextCharacter rangedChar;
    private TextCharacter wallChar;

    private Colors colors = new Colors("colors");
    private TextGraphics gra;

    public ElementViewTests() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        this.gra = Mockito.mock(TextGraphics.class);
        this.civieChar = new TextCharacter('C', colors.getColor("blue"), colors.getColor("bg"));
        this.laserChar = new TextCharacter('#', colors.getColor("brightRed"), colors.getColor("bg"));
        this.meleeChar = new TextCharacter('M', colors.getColor("red"), colors.getColor("bg"));
        this.rangedChar = new TextCharacter('R', colors.getColor("yellow"), colors.getColor("bg"));
        this.wallChar = new TextCharacter('#', colors.getColor("purple"), colors.getColor("bg"));
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

        new CivieView(colors).draw(gra, civie);
        Mockito.verify(gra, times(1))
                .setCharacter(60, 65, civieChar);

        checkPosCalledOnce(civie);
    }

    @Test
    public void meleeGuyDraw() {
        MeleeGuy melee = Mockito.mock(MeleeGuy.class);
        setUpElementPos(melee);

        new MeleeGuyView(colors).draw(gra, melee);
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
        new RangedGuyView(colors, lv).draw(gra, rg);
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

        new LaserView(colors).draw(gra, laser);
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

        new LaserView(colors).draw(gra, laser);
        Mockito.verify(laser).getReadiness();
        Mockito.verify(gra, never()).setCharacter(60, 65, laserChar);

        Mockito.verify(laser, never()).getPos();
    }

    @Test
    public void wallDraw() {
        Wall wall = Mockito.mock(Wall.class);
        setUpElementPos(wall);

        new WallView(colors).draw(gra, wall);
        Mockito.verify(gra, times(1))
                .setCharacter(60, 65, wallChar);

        checkPosCalledOnce(wall);
    }
}
