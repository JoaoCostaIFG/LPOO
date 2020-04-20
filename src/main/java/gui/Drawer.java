package gui;

import room.Room;
import room.element.Civilian;
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
    private static final TextColor blue = fromString("#0E91E7");
    private static final TextColor green = fromString("#76A15D");
    private static final TextColor purple = fromString("#8558AD");
    private static final TextColor red = fromString("#844F4E");

    private static final TextCharacter bgChar = new TextCharacter(' ', bg, bg);
    private static final TextCharacter civieChar = new TextCharacter('C', blue, bg);
    private static final TextCharacter meleeChar = new TextCharacter('M', red, bg);
    private static final TextCharacter skaChar = new TextCharacter('S', green, bg, SGR.BOLD);
    private static final TextCharacter skaBuryChar = new TextCharacter('X', green, bg, SGR.BOLD);
    private static final TextCharacter skaBodyChar = new TextCharacter('o', green, bg);
    private static final TextCharacter skaBodyBuryChar = new TextCharacter('x', green, bg);
    private static final TextCharacter wallChar = new TextCharacter('#', purple, bg);

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
        for (SkaneBody b : ska.getBody())
            gra.setCharacter(b.getX(), b.getY(), ska.isBury() ? skaBodyBuryChar : skaBodyChar);
        gra.setCharacter(ska.getX(), ska.getY(), ska.isBury() ? skaBuryChar : skaChar);
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

        for (Wall wall : room.getWalls())
            drawWall(wall);

        for (Civilian civie : room.getCivies())
            drawCivie(civie);

        for (MeleeGuy melee : room.getMeleeGuys())
            drawMelee(melee);

        drawSkane(room.getSkane());
    }
}
