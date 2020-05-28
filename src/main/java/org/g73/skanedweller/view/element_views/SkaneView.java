package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.g73.skanedweller.view.Colors;

public class SkaneView implements ElementDrawer<Skane> {
    private static final char skaChar = 'S';
    private static final char skaBodyChar = 'o';
    private static final char skaBuryChar = 'X';
    private static final char skaBodyBuryChar = 'x';

    private static final int skaFov = 5;

    public void draw(TextGraphics gra, Skane ska) {
        gra.enableModifiers(SGR.BOLD);
        TextColor oldBg = gra.getBackgroundColor();
        TextColor oldFg = gra.getForegroundColor();
        gra.setBackgroundColor(Colors.bg);

        double oxyPerc = (double) (ska.getMaxOxygenLevel() - ska.getOxygenLevel()) / ska.getMaxOxygenLevel();
        long numSpotsToFill = Math.round((ska.getSize() + 1) * oxyPerc); // orange spots to draw (empty oxygen)
        long numSpots = 0;

        gra.setForegroundColor(Colors.orange);
        for (SkaneBody b : ska.getBody()) {
            if (ska.isBury() && b.getPos().dist(ska.getPos()) > skaFov)
                gra.setBackgroundColor(Colors.bgDark);
            else
                gra.setBackgroundColor(Colors.bg);

            if (numSpots++ == numSpotsToFill)
                gra.setForegroundColor(Colors.green);
            gra.setCharacter(b.getX(), b.getY(), ska.isBury() ? skaBodyBuryChar : skaBodyChar);
        }

        if (numSpots == numSpotsToFill)
            gra.setForegroundColor(Colors.green);
        gra.setCharacter(ska.getX(), ska.getY(), ska.isBury() ? skaBuryChar : skaChar);

        gra.setBackgroundColor(oldBg);
        gra.setForegroundColor(oldFg);
        gra.disableModifiers(SGR.BOLD);
    }
}
