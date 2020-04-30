package view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.element.Civilian;

public class CivieView extends Drawer<Civilian> {
    private static final TextCharacter civieChar = new TextCharacter('C', blue, bg);

    @Override
    public void draw(TextGraphics gra, Civilian civie) {
        gra.setCharacter(civie.getX(), civie.getY(), civieChar);
    }
}
