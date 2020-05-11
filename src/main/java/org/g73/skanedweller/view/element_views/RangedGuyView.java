package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.RangedGuy;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class RangedGuyView {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor yellow = fromString("#988D3D");
    private static final TextCharacter rangedChar = new TextCharacter('R', yellow, bg);

    public void draw(TextGraphics gra, RangedGuy ranged) {
        gra.setCharacter(ranged.getX(), ranged.getY(), rangedChar);
    }
}
