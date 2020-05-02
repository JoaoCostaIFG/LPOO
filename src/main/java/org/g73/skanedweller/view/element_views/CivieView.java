package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.Civilian;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class CivieView {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor blue = fromString("#0E91E7");
    private static final TextCharacter civieChar = new TextCharacter('C', blue, bg);

    public void draw(TextGraphics gra, Civilian civie) {
        gra.setCharacter(civie.getX(), civie.getY(), civieChar);
    }
}
