package gui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import room.Position;
import room.Room;
import room.element.*;
import room.element.skane.Skane;
import room.element.skane.SkaneBody;
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
    private static final TextCharacter bgDarkChar = new TextCharacter(' ', bgDark, bgDark);
    private static final TextCharacter civieChar = new TextCharacter('C', blue, bg);
    private static final TextCharacter meleeChar = new TextCharacter('M', red, bg);
    private static final TextCharacter wallChar = new TextCharacter('#', purple, bg);

    private static final char skaChar = 'S';
    private static final char skaBodyChar = 'o';
    private static final char skaBuryChar = 'X';
    private static final char skaBodyBuryChar = 'x';

    private static final int skaFov = 5;

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

    private void fillBg(Room room, Position skaPos, boolean isSkaBury) {
        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(room.getWidth(), room.getHeight()),
                isSkaBury ? bgDarkChar : bgChar);

        if (isSkaBury) { // if skane is buried, draw FOV around head
            for (int i = skaPos.getX() - skaFov; i < skaPos.getX() + skaFov; ++i)
                for (int j = skaPos.getY() - skaFov; j < skaPos.getY() + skaFov; ++j)
                    if (Math.pow(skaPos.getX() - i, 2) + Math.pow(skaPos.getY() - j, 2) < skaFov * skaFov)
                        gra.setCharacter(i, j, bgChar);
        }
    }

    private boolean isOutsideSkaFov(Element e, Position skaPos, boolean isSkaBury) {
        return (isSkaBury && e.getPos().dist(skaPos) > skaFov);
    }

    @Override
    public void drawRoom(Room room) {
        boolean isSkaBury = room.isSkaneBury();
        Position skaPos = room.getSkanePos();
        fillBg(room, skaPos, isSkaBury);

        for (Wall wall : room.getWalls()) {
            if (!isOutsideSkaFov(wall, skaPos, isSkaBury))
                drawWall(wall);
        }

        for (Element e : room.getEnemies()) {
            if (isOutsideSkaFov(e, skaPos, isSkaBury))
                continue;

            if (e instanceof Civilian) drawCivie((Civilian) e);
            else if (e instanceof MeleeGuy) drawMelee((MeleeGuy) e);
        }

        /*
        for (Scent s : room.getSkane().getScentTrail())
            gra.setCharacter(s.getX(), s.getY(), civieChar);
        */
        drawSkane(room.getSkane());
    }
}
