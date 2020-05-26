package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.controller.movement_strategy.RangedMoveStrat;
import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Skane;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MovementStratTests {
    private Element el;
    private Skane ska;
    private final int moveTicks = 5;

    @Before
    public void setUp() {
        this.ska = new Skane(20, 1, 1, 1, 1, 1);
        this.el = Mockito.mock(Element.class);
        el.setMovCounter(0);

        ska.setPos(ska.moveRight());
        ska.setPos(ska.moveRight());
    }

    @After
    public void wereTicksSet() {
        assertEquals(el.getMovCounter(), moveTicks);
    }

    private void roomMockSetSkaneInfo (Room room) {
        Mockito.when(room.getSkane())
                .thenReturn(ska);
        Mockito.when(room.getSkanePos())
                .thenReturn(ska.getPos());
    }

    private void makeSkaneVisible(Room room, Element e) {
        // making it so skane is visible
        Mockito.when(room.isSkaneBury())
                .thenReturn(false);
        Mockito.when(room.isSkanePos(any()))
                .thenReturn(true);

        List<Element> skaList = new ArrayList<>();
        skaList.add(ska);
        List<Element> skaBodyList = new ArrayList<>();
        skaBodyList.add(ska.getBody().get(0));
        Mockito.when(room.elemRay(e.getPos(), ska.getPos()))
                .thenReturn(skaList);
        Mockito.when(room.elemRay(e.getPos(), ska.getBody().get(0).getPos()))
                .thenReturn(skaBodyList);

        roomMockSetSkaneInfo(room);
    }

    private void makeSkaneObstructed(Room room, Element e) {
        // making it so raycast hits something other than skane
        Mockito.when(room.isSkaneBury())
                .thenReturn(false);

        List<Element> rayList = new ArrayList<>();
        rayList.add(new Civilian(1, 1, 1));
        Mockito.when(room.elemRay(e.getPos(), ska.getPos()))
                .thenReturn(rayList);
        Mockito.when(room.elemRay(e.getPos(), ska.getBody().get(0).getPos()))
                .thenReturn(rayList);
        Mockito.when(room.isSkanePos(any()))
                .thenReturn(false);

        roomMockSetSkaneInfo(room);
    }

    @Test
    public void scaredStratBuryTest() {
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.isSkaneBury())
                .thenReturn(true);

        this.el.setMoveStrat(new ScaredMoveStrat(moveTicks));
        List<Position> posList = el.genMoves(room);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, times(0)).elemRay(any(), any());
    }

    @Test
    public void scaredStratTest() {
        Room room = Mockito.mock(Room.class);
        makeSkaneVisible(room, el);

        this.el.setMoveStrat(new ScaredMoveStrat(moveTicks));
        List<Position> posList = el.genMoves(room);
        assertEquals(posList.size(), 2);

        assertEquals(posList.get(0), el.moveDown());
        assertEquals(posList.get(1), el.moveLeft());

        Mockito.verify(room, times(1)).elemRay(any(), any());
        Mockito.verify(room, atLeastOnce()).getSkanePos();
    }

    @Test
    public void scaredStratObstructedTest() {
        Room room = Mockito.mock(Room.class);

        makeSkaneObstructed(room, el);

        this.el.setMoveStrat(new ScaredMoveStrat(moveTicks));
        List<Position> posList = el.genMoves(room);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, times(1)).elemRay(any(), any());
        Mockito.verify(room, atLeastOnce()).getSkanePos();
    }

    @Test
    public void meleeStratBuryTest() {
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.isSkaneBury())
                .thenReturn(true);

        this.el.setMoveStrat(new MeleeMoveStrat(moveTicks));
        List<Position> posList = el.genMoves(room);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, times(0)).elemRay(any(), any());
    }

    @Test
    public void meleeStratTest() {
        Room room = Mockito.mock(Room.class);
        makeSkaneVisible(room, el);

        this.el.setMoveStrat(new MeleeMoveStrat(moveTicks));
        List<Position> posList = el.genMoves(room);
        assertEquals(posList.size(), 4); // can see 2 skane parts

        assertEquals(posList.get(0), el.moveRight());
        assertEquals(posList.get(1), el.moveUp());
        assertEquals(posList.get(2), el.moveRight());
        assertEquals(posList.get(3), el.moveUp());

        Mockito.verify(room, times(2)).elemRay(any(), any());
    }

    @Test
    public void meleeStratObstructedTest() {
        Room room = Mockito.mock(Room.class);
        makeSkaneObstructed(room, el);

        ska.dropScent(1);
        Mockito.when(room.elemRay(any(), any())).thenReturn(new ArrayList<>());

        this.el.setMoveStrat(new MeleeMoveStrat(moveTicks));
        List<Position> posList = el.genMoves(room);
        assertEquals(posList.size(), 2); // can see scent

        assertEquals(posList.get(0), el.moveRight());
        assertEquals(posList.get(1), el.moveUp());

        // 1 head, 1 body, 1 scent
        Mockito.verify(room, times(3)).elemRay(any(), any());
    }
    
//    @Test
//    public void testRangedStratBurried() {
//        Room room = Mockito.mock(Room.class);
//        Element e = Mockito.mock(Element.class);
//
//        RangedMoveStrat moveStrat = new RangedMoveStrat(60);
//        Mockito.when(room.isSkaneBury()).thenReturn(true);
//        assertEquals(0, moveStrat.genMoves(room, e).size());
//        Mockito.verify(e, times(1)).setMovCounter(eq(60));
//        Mockito.verify(room).isSkaneBury();
//        verifyNoMoreInteractions(room);
//    }
//    
//    @Test
//    public void testRangedStratSurface() {
//        Room room = Mockito.mock(Room.class);
//        Element e = Mockito.mock(Element.class);
//
//        RangedMoveStrat moveStrat = new RangedMoveStrat(60);
//        Mockito.when(room.isSkaneBury()).thenReturn(true);
//        assertEquals(0, moveStrat.genMoves(room, e).size());
//        Mockito.verify(e, times(1)).setMovCounter(eq(60));
//    }
}
