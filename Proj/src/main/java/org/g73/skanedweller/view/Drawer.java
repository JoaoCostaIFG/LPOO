package org.g73.skanedweller.view;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.view.element_views.*;

import java.io.IOException;

public class Drawer implements RoomDrawer {
    private final int skaFov = 5;

    private TextGraphics gra;
    private ElementDrawer<Civilian> civieView;
    private ElementDrawer<MeleeGuy> meleeGuyView;
    private ElementDrawer<RangedGuy> rangedGuyView;
    private ElementDrawer<Room> roomView;
    private ElementDrawer<Skane> skaneView;
    private ElementDrawer<Wall> wallView;

    public Drawer(TextGraphics gra, String colorResName) throws IOException {
        this.gra = gra;

        // load colors to pass to views
        Colors colors = new Colors(colorResName);

        this.civieView = new CivieView(colors);
        this.meleeGuyView = new MeleeGuyView(colors);
        this.rangedGuyView = new RangedGuyView(colors);
        this.roomView = new RoomView(colors, skaFov);
        this.skaneView = new SkaneView(colors, skaFov);
        this.wallView = new WallView(colors);
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
