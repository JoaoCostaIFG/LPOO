package org.g73.skanedweller.view;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.sun.tools.javac.util.List;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.view.element_views.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class DrawerTests {
    private TextGraphics gra;
    private CivieView civieView;
    private MeleeGuyView meleeGuyView;
    private RangedGuyView rangedGuyView;
    private RoomView roomView;
    private SkaneView skaneView;
    private WallView wallView;

    private Drawer drawer;

    @Before
    public void setUp() {
        gra = Mockito.mock(TextGraphics.class);
        civieView = Mockito.mock(CivieView.class);
        rangedGuyView = Mockito.mock(RangedGuyView.class);
        meleeGuyView = Mockito.mock(MeleeGuyView.class);
        roomView = Mockito.mock(RoomView.class);
        skaneView = Mockito.mock(SkaneView.class);
        wallView = Mockito.mock(WallView.class);

        drawer = new Drawer(gra, civieView, meleeGuyView, rangedGuyView, roomView, skaneView, wallView);
    }

    private void mockElements(Room room, Skane ska, List<Wall> walls, List<Element> enemies) {
        Mockito.when(room.getSkane()).thenReturn(ska);
        Mockito.when(room.getWalls()).thenReturn(walls);
        Mockito.when(room.getEnemies()).thenReturn(enemies);
    }

    @Test
    public void drawSurface() {
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.isSkaneBury()).thenReturn(false); // Draw all elements

        Position withinSkane = new Position(2, 3);
        Position outsideSkane = new Position(200, 300);
        Skane s = new Skane(withinSkane, 1, 1, 1, 1);
        Wall w1 = new Wall(withinSkane), w2 = new Wall(outsideSkane);
        Civilian c1 = new Civilian(withinSkane, 1), c2 = new Civilian(outsideSkane, 1);
        MeleeGuy m1 = new MeleeGuy(withinSkane, 1, 1, 1);
        mockElements(room, s, List.of(w1, w2), List.of(c1, c2, m1));

        this.drawer.draw(room);
        Mockito.verify(this.skaneView).draw(this.gra, s);
        Mockito.verify(this.wallView).draw(this.gra, w1);
        Mockito.verify(this.wallView).draw(this.gra, w2);
        Mockito.verify(this.civieView).draw(this.gra, c1);
        Mockito.verify(this.civieView).draw(this.gra, c2);
        Mockito.verify(this.meleeGuyView).draw(this.gra, m1);
    }

    @Test
    public void drawBurried() {
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.isSkaneBury()).thenReturn(true); // Draw elements next to skane
        Mockito.when(room.getSkanePos()).thenReturn(new Position(0, 0));

        Position withinSkane = new Position(2, 3), withinSkane2 = new Position(4, 1);
        Position outsideSkane = new Position(200, 300), outsideSkane2 = new Position(40, 0);
        Position outsideSkane3 = new Position(0, 15);
        Skane s = new Skane(withinSkane, 1, 1, 1, 1);
        Wall w1 = new Wall(withinSkane), w2 = new Wall(outsideSkane), w3 = new Wall(outsideSkane2);
        Civilian c1 = new Civilian(withinSkane, 1), c2 = new Civilian(outsideSkane3, 1);
        MeleeGuy m1 = new MeleeGuy(withinSkane2, 1, 1, 1);
        mockElements(room, s, List.of(w1, w2, w3), List.of(c1, c2, m1));

        this.drawer.draw(room);
        // Within skane reach
        Mockito.verify(this.skaneView).draw(this.gra, s);
        Mockito.verify(this.wallView).draw(this.gra, w1);
        Mockito.verify(this.civieView).draw(this.gra, c1);
        Mockito.verify(this.meleeGuyView).draw(this.gra, m1);
        // Outside
        Mockito.verify(this.wallView, Mockito.never()).draw(this.gra, w2);
        Mockito.verify(this.wallView, Mockito.never()).draw(this.gra, w3);
        Mockito.verify(this.civieView, Mockito.never()).draw(this.gra, c2);
    }
}
