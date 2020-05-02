package view.element_views;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.Position;
import model.Room;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class RoomView {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor bgDark = fromString("#212833");
    private static final TextCharacter bgChar = new TextCharacter(' ', bg, bg);
    private static final TextCharacter bgDarkChar = new TextCharacter(' ', bgDark, bgDark);

    private static final int skaFov = 5;

    private void drawSkaneFov(TextGraphics gra, Position skaPos) {
        for (int i = skaPos.getX() - skaFov; i < skaPos.getX() + skaFov; ++i) {
            for (int j = skaPos.getY() - skaFov; j < skaPos.getY() + skaFov; ++j) {
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
