package view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class Drawer {
    protected static final TextColor bg = fromString("#313742");
    protected static final TextColor bgDark = fromString("#212833");
    protected static final TextColor blue = fromString("#0E91E7");
    protected static final TextColor green = fromString("#76A15D");
    protected static final TextColor orange = fromString("#D68445");
    protected static final TextColor purple = fromString("#8558AD");
    protected static final TextColor red = fromString("#844F4E");

    protected static final int skaFov = 5;

    private TextGraphics gra;

    public Drawer(TextGraphics gra) {
        this.gra = gra;
    }

    /*
    private boolean isOutsideSkaFov(Element e, Position skaPos, boolean isSkaBury) {
        return (isSkaBury && e.getPos().dist(skaPos) > skaFov);
    }

    public void draw(Room room) {
        boolean isSkaBury = room.isSkaneBury();
        Position skaPos = room.getSkanePos();

        for (Wall wall : room.getWalls()) {
            if (!isOutsideSkaFov(wall, skaPos, isSkaBury))
                new WallView(gra).draw(wall);
        }

        for (Element e : room.getEnemies()) {
            if (isOutsideSkaFov(e, skaPos, isSkaBury))
                continue;

            if (e instanceof Civilian) new CivieView(gra).draw((Civilian) e);
            else if (e instanceof MeleeGuy) new MeleeGuyView(gra).draw((MeleeGuy) e);
        }

        new SkaneView(gra).draw(gra, room.getSkane());
    }
     */
}
