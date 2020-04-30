package view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.element.MeleeGuy;

public class MeleeGuyView extends Drawer<MeleeGuy> {
    private static final TextCharacter meleeChar = new TextCharacter('M', red, bg);

    @Override
    public void draw(TextGraphics gra, MeleeGuy melee) {
        gra.setCharacter(melee.getX(), melee.getY(), meleeChar);
    }
}
