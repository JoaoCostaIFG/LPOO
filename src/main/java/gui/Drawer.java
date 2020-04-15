package gui;

import arena.Map;
import arena.element.Skane;
import arena.element.Wall;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Drawer implements GraphicsDrawer {
    static final String skaChar = "S";
    static final String skaBuryChar = "X";
    static final String wallChar = "#";

    private TextGraphics gra;

    Drawer(TextGraphics gra) {
        this.gra = gra;
    }

    @Override
    public void drawSkane(Skane ska) {
        gra.setForegroundColor(TextColor.Factory.fromString("#76A15D"));
        gra.enableModifiers(SGR.BOLD);
        gra.putString(new TerminalPosition(ska.getX(), ska.getY()),
                ska.isBury() ? skaBuryChar : skaChar);
        gra.disableModifiers(SGR.BOLD);
    }

    @Override
    public void drawWall(Wall wall) {
        gra.setForegroundColor(TextColor.Factory.fromString("#8558AD"));
        gra.putString(new TerminalPosition(wall.getX(), wall.getY()), wallChar);
    }

    @Override
    public void drawMap(Map map) {
        gra.setBackgroundColor(TextColor.Factory.fromString("#313742"));
        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(map.getWidth(), map.getHeight()), ' ');

        drawSkane(map.getSkane());

        for (Wall wall : map.getWalls()) {
            drawWall(wall);
        }
    }
}
