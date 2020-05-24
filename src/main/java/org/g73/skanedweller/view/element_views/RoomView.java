package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class RoomView {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor bgDark = fromString("#212833");
    private static final TextCharacter bgChar = new TextCharacter(' ', bg, bg);
    private static final TextCharacter bgDarkChar = new TextCharacter(' ', bgDark, bgDark);

    private static final int skaFov = 5;

    private void drawSkaneFov(TextGraphics gra, Position skaPos) {
        for (int i = Math.max(skaPos.getX() - skaFov, 0); i < skaPos.getX() + skaFov && i >= 0; ++i) {
            for (int j = Math.max(skaPos.getY() - skaFov, 0); j < skaPos.getY() + skaFov && j >= 0; ++j) {
                if (Math.pow(skaPos.getX() - i, 2) + Math.pow(skaPos.getY() - j, 2) < skaFov * skaFov)
                    gra.setCharacter(i, j, bgChar);
            }
        }
    }

    public void draw(TextGraphics gra, Room room) {
        boolean isSkaBury = room.isSkaneBury();
        Position skaPos = room.getSkanePos();

        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(room.getWidth(), room.getHeight()),
                isSkaBury ? bgDarkChar : bgChar);

        if (isSkaBury) drawSkaneFov(gra, skaPos);
    }
}
