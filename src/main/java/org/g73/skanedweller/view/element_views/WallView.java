package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Wall;
import org.g73.skanedweller.view.Colors;

public class WallView implements ElementDrawer<Wall> {
    private static final TextCharacter wallChar = new TextCharacter('#', Colors.purple, Colors.bg);

    public void draw(TextGraphics gra, Wall wall) {
        gra.setCharacter(wall.getX(), wall.getY(), wallChar);
    }
}
