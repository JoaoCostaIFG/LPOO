package org.g73.skanedweller.view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.g73.skanedweller.model.element.Wall;
import org.g73.skanedweller.view.element_views.*;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class Drawer implements RoomDrawer {
    private static final TextColor bg = fromString("#313742");
    private static final TextColor bgDark = fromString("#212833");
    private static final TextColor blue = fromString("#0E91E7");
    private static final TextColor green = fromString("#76A15D");
    private static final TextColor orange = fromString("#D68445");
    private static final TextColor purple = fromString("#8558AD");
    private static final TextColor red = fromString("#844F4E");

    private static final int skaFov = 5;

    private TextGraphics gra;
    private CivieView civieView;
    private MeleeGuyView meleeGuyView;
    private RoomView roomView;
    private SkaneView skaneView;
    private WallView wallView;

    public Drawer(TextGraphics gra) {
        this.gra = gra;
        civieView = new CivieView();
        meleeGuyView = new MeleeGuyView();
        roomView = new RoomView();
        skaneView = new SkaneView();
        wallView = new WallView();
    }

    private boolean isOutsideSkaFov(Element e, Position skaPos, boolean isSkaBury) {
        return (isSkaBury && e.getPos().dist(skaPos) > skaFov);
    }

    public void draw(Room room) {
        roomView.draw(gra, room);

        boolean isSkaBury = room.isSkaneBury();
        Position skaPos = room.getSkanePos();

        for (Wall wall : room.getWalls()) {
            if (!isOutsideSkaFov(wall, skaPos, isSkaBury))
                wallView.draw(gra, wall);
        }

        for (Element e : room.getEnemies()) {
            if (isOutsideSkaFov(e, skaPos, isSkaBury))
                continue;

            if (e instanceof Civilian) civieView.draw(gra, (Civilian) e);
            else if (e instanceof MeleeGuy) meleeGuyView.draw(gra, (MeleeGuy) e);
        }

        skaneView.draw(gra, room.getSkane());
    }
}
