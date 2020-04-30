package view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.element.Wall;

public class WallView extends Drawer<Wall> {
    private static final TextCharacter wallChar = new TextCharacter('#', purple, bg);

    @Override
    public void draw(TextGraphics gra, Wall wall) {
        gra.setCharacter(wall.getX(), wall.getY(), wallChar);
    }
}
