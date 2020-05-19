package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.MeleeGuy;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class MeleeGuyView {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor red = fromString("#844F4E");
    private static final TextCharacter meleeChar = new TextCharacter('M', red, bg);

    public void draw(TextGraphics gra, MeleeGuy melee) {
        gra.setCharacter(melee.getX(), melee.getY(), meleeChar);
    }
}
