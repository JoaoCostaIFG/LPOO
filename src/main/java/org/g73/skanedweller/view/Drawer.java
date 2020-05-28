package org.g73.skanedweller.view;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.view.element_views.*;

public class Drawer implements RoomDrawer {
    private static final int skaFov = 5;

    private TextGraphics gra;
    private CivieView civieView;
    private MeleeGuyView meleeGuyView;
    private RangedGuyView rangedGuyView;
    private RoomView roomView;
    private SkaneView skaneView;
    private WallView wallView;

    public Drawer(TextGraphics gra) {
        this(gra, new CivieView(),
                new MeleeGuyView(), new RangedGuyView(),
                new RoomView(), new SkaneView(),
                new WallView());
    }

    public Drawer(TextGraphics gra, CivieView civieView,
                  MeleeGuyView meleeGuyView, RangedGuyView rangedGuyView,
                  RoomView roomView, SkaneView skaneView, WallView wallView) {
        this.gra = gra;
        this.civieView = civieView;
        this.meleeGuyView = meleeGuyView;
        this.rangedGuyView = rangedGuyView;
        this.roomView = roomView;
        this.skaneView = skaneView;
        this.wallView = wallView;
    }

    private boolean IsElemInvis(Element e, Position skaPos, boolean isSkaBury) {
        return (isSkaBury && e.getPos().dist(skaPos) > skaFov);
    }

    public void draw(Room room) {
        roomView.draw(gra, room);

        boolean isSkaBury = room.isSkaneBury();
        Position skaPos = room.getSkanePos();

        for (Wall wall : room.getWalls()) {
            if (!IsElemInvis(wall, skaPos, isSkaBury))
                wallView.draw(gra, wall);
        }

        for (Element e : room.getEnemies()) {
            if (IsElemInvis(e, skaPos, isSkaBury))
                continue;

            if (e instanceof Civilian) civieView.draw(gra, (Civilian) e);
            else if (e instanceof MeleeGuy) meleeGuyView.draw(gra, (MeleeGuy) e);
            else if (e instanceof RangedGuy) rangedGuyView.draw(gra, (RangedGuy) e);
        }

        skaneView.draw(gra, room.getSkane());
    }
}
