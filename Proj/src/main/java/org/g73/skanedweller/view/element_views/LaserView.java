package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Laser;
import org.g73.skanedweller.view.Colors;

public class LaserView implements ElementDrawer<Laser> {
    private TextCharacter laserChar;

    public LaserView(Colors colors) {
        this.laserChar = new TextCharacter('#', colors.getColor("ruby"), colors.getColor("bg"));
    }

    public void draw(TextGraphics gra, Laser laser) {
        if (laser.getReadiness()) // only draw ready lasers
            gra.setCharacter(laser.getX(), laser.getY(), laserChar);
    }
}
