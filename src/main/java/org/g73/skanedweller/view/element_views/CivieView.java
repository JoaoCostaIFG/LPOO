package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.view.Colors;

public class CivieView implements ElementDrawer<Civilian> {
    private static final TextCharacter civieChar = new TextCharacter('C', Colors.blue, Colors.bg);

    public void draw(TextGraphics gra, Civilian civie) {
        gra.setCharacter(civie.getX(), civie.getY(), civieChar);
    }
}
