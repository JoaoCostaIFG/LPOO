package gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import room.Room;
import room.colliders.RectangleCollider;
import room.element.Civilian;
import room.element.Entity;
import room.element.MeleeGuy;
import room.element.Wall;
import room.element.skane.Skane;
import room.element.skane.SkaneBody;

import java.util.List;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class CollisionsDrawer implements GraphicsDrawer {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor blue = fromString("#0E91E7");
    private static final TextColor green = fromString("#76A15D");
    private static final TextColor purple = fromString("#8558AD");
    private static final TextColor red = fromString("#844F4E");

    private static final TextCharacter bgChar = new TextCharacter(' ', bg, bg);
    private static final TextCharacter colChar = new TextCharacter('X', blue, bg);

    private TextGraphics gra;

    public CollisionsDrawer(TextGraphics gra) {
        this.gra = gra;
    }

    private void drawRectangleCollider(RectangleCollider col) {
        for (int i = 0; i < col.getHeight(); ++i) {
            int x = col.getX() + i;
            for (int j = 0; j < col.getWidth(); ++j)
                gra.setCharacter(x, col.getY() + j, colChar);
        }
    }

    @Override
    public void drawSkane(Skane ska) {
        drawRectangleCollider((RectangleCollider) ska.getCollider());
        for (SkaneBody c : ska.getBody())
            drawRectangleCollider(c.getCollider());
    }

    @Override
    public void drawWall(Wall wall) {
        drawRectangleCollider(wall.getCollider());
    }

    @Override
    public void drawCivie(Civilian civie) {
        drawRectangleCollider((RectangleCollider) civie.getCollider());
    }

    @Override
    public void drawMelee(MeleeGuy melee) {
        drawRectangleCollider((RectangleCollider) melee.getCollider());
    }

    @Override
    public void drawImage(List<String> image, int x, int y) {

    }

    @Override
    public void drawRoom(Room room) {
        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(room.getWidth(), room.getHeight()), bgChar);

        for (Wall wall : room.getWalls())
            drawWall(wall);

        for (Entity e : room.getEnemies()) {
            if (e instanceof Civilian) drawCivie((Civilian) e);
            else if (e instanceof MeleeGuy) drawMelee((MeleeGuy) e);
        }

        drawSkane(room.getSkane());
    }
}
