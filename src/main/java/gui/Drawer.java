package gui;

import room.Room;
import room.element.Civilian;
import room.element.Entity;
import room.element.MeleeGuy;
import room.element.skane.Skane;
import room.element.skane.SkaneBody;
import room.element.Wall;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.List;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class Drawer implements GraphicsDrawer {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor bgDark = fromString("#212833");
    private static final TextColor blue = fromString("#0E91E7");
    private static final TextColor green = fromString("#76A15D");
    private static final TextColor orange = fromString("#D68445");
    private static final TextColor purple = fromString("#8558AD");
    private static final TextColor red = fromString("#844F4E");

    private static final TextCharacter bgChar = new TextCharacter(' ', bg, bg);
    private static final TextCharacter civieChar = new TextCharacter('C', blue, bg);
    private static final TextCharacter meleeChar = new TextCharacter('M', red, bg);
    private static final TextCharacter wallChar = new TextCharacter('#', purple, bg);

    private static final char skaChar = 'S';
    private static final char skaBuryChar = 'X';
    private static final char skaBodyChar = 'o';
    private static final char skaBodyBuryChar = 'x';

    private TextGraphics gra;

    Drawer(TextGraphics gra) {
        this.gra = gra;
    }

    public void drawImage(List<String> image, int x, int y) {
        int line_y = y;
        for (String l : image)
            gra.putCSIStyledString(x, line_y++, l);
    }

    @Override
    public void drawSkane(Skane ska) {
        gra.enableModifiers(SGR.BOLD);
        gra.setBackgroundColor(bg);

        double oxyPerc = (double) (ska.getMaxOxygenLevel() - ska.getOxygenLevel()) / ska.getMaxOxygenLevel();
        long numSpotsToFill = Math.round((ska.getSize() + 1) * oxyPerc);
        long numSpots = 0;

        gra.setForegroundColor(orange);
        for (SkaneBody b : ska.getBody()) {
            if (numSpots++ == numSpotsToFill)
                gra.setForegroundColor(green);

            gra.setCharacter(b.getX(), b.getY(), ska.isBury() ? skaBodyBuryChar : skaBodyChar);
        }
        if (numSpots == numSpotsToFill)
            gra.setForegroundColor(green);
        gra.setCharacter(ska.getX(), ska.getY(), ska.isBury() ? skaBuryChar : skaChar);


        gra.disableModifiers(SGR.BOLD);
    }

    @Override
    public void drawWall(Wall wall) {
        gra.setCharacter(wall.getX(), wall.getY(), wallChar);
    }

    @Override
    public void drawCivie(Civilian civie) {
        gra.setCharacter(civie.getX(), civie.getY(), civieChar);
    }

    @Override
    public void drawMelee(MeleeGuy melee) {
        gra.setCharacter(melee.getX(), melee.getY(), meleeChar);
    }

    @Override
    public void drawRoom(Room room) {
        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(room.getWidth(), room.getHeight()), bgChar);

        /*
        for (Scent s : room.getSkane().getScentTrail())
            gra.setCharacter(s.getX(), s.getY(), civieChar);
         */

        for (Wall wall : room.getWalls())
            drawWall(wall);

        for (Entity e : room.getEnemies()) {
            if (e instanceof Civilian) drawCivie((Civilian) e);
            else if (e instanceof MeleeGuy) drawMelee((MeleeGuy) e);
        }

        drawSkane(room.getSkane());
    }
}
