package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Laser;
import org.g73.skanedweller.model.element.RangedGuy;
import org.g73.skanedweller.view.Colors;

public class RangedGuyView implements ElementDrawer<RangedGuy> {
    private LaserView laserView;
    private TextCharacter rangedChar;

    public RangedGuyView(Colors colors, LaserView laserView) {
        this.laserView = laserView;
        this.rangedChar = new TextCharacter('R', colors.getColor("yellow"), colors.getColor("bg"));
    }

    public RangedGuyView(Colors colors) {
        this(colors, new LaserView(colors));
    }

    public void draw(TextGraphics gra, RangedGuy ranged) {
        gra.setCharacter(ranged.getX(), ranged.getY(), rangedChar);

        for (Laser l : ranged.getLasers())
            laserView.draw(gra, l);
    }
}
