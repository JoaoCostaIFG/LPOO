package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.g73.skanedweller.view.Colors;

public class MeleeGuyView implements ElementDrawer<MeleeGuy> {
    private static final TextCharacter meleeChar = new TextCharacter('M', Colors.red, Colors.bg);

    public void draw(TextGraphics gra, MeleeGuy melee) {
        gra.setCharacter(melee.getX(), melee.getY(), meleeChar);
    }
}
