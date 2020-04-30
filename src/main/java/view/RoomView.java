package view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import model.Position;
import model.Room;

public class RoomView extends Drawer<Room> {
    private static final TextCharacter bgChar = new TextCharacter(' ', bg, bg);
    private static final TextCharacter bgDarkChar = new TextCharacter(' ', bgDark, bgDark);

    private void drawSkaneFov(TextGraphics gra, Position skaPos) {
        for (int i = skaPos.getX() - skaFov; i < skaPos.getX() + skaFov; ++i) {
            for (int j = skaPos.getY() - skaFov; j < skaPos.getY() + skaFov; ++j) {
                if (Math.pow(skaPos.getX() - i, 2) + Math.pow(skaPos.getY() - j, 2) < skaFov * skaFov)
                    gra.setCharacter(i, j, bgChar);
            }
        }
    }

    @Override
    public void draw(TextGraphics gra, Room room) {
        boolean isSkaBury = room.isSkaneBury();
        Position skaPos = room.getSkanePos();

        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(room.getWidth(), room.getHeight()),
                isSkaBury ? bgDarkChar : bgChar);

        if (isSkaBury) drawSkaneFov(gra, skaPos);
    }
}
