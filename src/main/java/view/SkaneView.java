package view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.element.skane.Skane;
import model.element.skane.SkaneBody;

public class SkaneView extends Drawer<Skane> {
    private static final char skaChar = 'S';
    private static final char skaBodyChar = 'o';
    private static final char skaBuryChar = 'X';
    private static final char skaBodyBuryChar = 'x';

    public void draw(TextGraphics gra, Skane ska) {
        gra.enableModifiers(SGR.BOLD);
        TextColor oldBg = gra.getBackgroundColor();
        TextColor oldFg = gra.getForegroundColor();
        gra.setBackgroundColor(bg);

        double oxyPerc = (double) (ska.getMaxOxygenLevel() - ska.getOxygenLevel()) / ska.getMaxOxygenLevel();
        long numSpotsToFill = Math.round((ska.getSize() + 1) * oxyPerc);
        long numSpots = 0;

        gra.setForegroundColor(orange);
        for (SkaneBody b : ska.getBody()) {
            if (ska.isBury() && b.getPos().dist(ska.getPos()) > skaFov)
                gra.setBackgroundColor(bgDark);
            else
                gra.setBackgroundColor(bg);

            if (numSpots++ == numSpotsToFill)
                gra.setForegroundColor(green);
            gra.setCharacter(b.getX(), b.getY(), ska.isBury() ? skaBodyBuryChar : skaBodyChar);
        }

        if (numSpots == numSpotsToFill)
            gra.setForegroundColor(green);
        gra.setCharacter(ska.getX(), ska.getY(), ska.isBury() ? skaBuryChar : skaChar);

        gra.setBackgroundColor(oldBg);
        gra.setForegroundColor(oldFg);
        gra.disableModifiers(SGR.BOLD);
    }
}
