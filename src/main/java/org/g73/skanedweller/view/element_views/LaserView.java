package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Laser;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class LaserView {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor brightRed = fromString("#C00000");
    private static final TextCharacter laserChar = new TextCharacter('#', brightRed, bg);

    public void draw(TextGraphics gra, Laser laser) {
        if (laser.getReadiness()) // only draw ready lasers
            gra.setCharacter(laser.getX(), laser.getY(), laserChar);
    }
}
