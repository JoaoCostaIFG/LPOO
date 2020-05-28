package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Laser;
import org.g73.skanedweller.model.element.RangedGuy;
import org.g73.skanedweller.view.Colors;

public class RangedGuyView implements ElementDrawer<RangedGuy> {
    private static final TextCharacter rangedChar = new TextCharacter('R', Colors.yellow, Colors.bg);

    private LaserView laserView;

    public RangedGuyView(LaserView laserView) {
        this.laserView = laserView;
    }

    public RangedGuyView() {
        this(new LaserView());
    }

    public void draw(TextGraphics gra, RangedGuy ranged) {
        gra.setCharacter(ranged.getX(), ranged.getY(), rangedChar);

        for (Laser l : ranged.getLasers())
            laserView.draw(gra, l);
    }
}
