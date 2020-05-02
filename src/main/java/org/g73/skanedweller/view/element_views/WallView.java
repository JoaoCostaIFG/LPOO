package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Wall;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class WallView {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor purple = fromString("#8558AD");
    private static final TextCharacter wallChar = new TextCharacter('#', purple, bg);

    public void draw(TextGraphics gra, Wall wall) {
        gra.setCharacter(wall.getX(), wall.getY(), wallChar);
    }
}
