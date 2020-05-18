package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

public class MovementStratTests {
    private Civilian civie;
    private Skane ska;
    private final int moveTicks = 5;

    @Before
    public void setUp() {
        this.ska = new Skane(20, 1, 1, 1, 1, 1);
        this.civie = new Civilian(10, 10, 10);
        civie.setMovCounter(0);

        ska.setPos(ska.moveRight());
        ska.setPos(ska.moveRight());
    }

    @After
    public void wereTicksSet() {
        assertEquals(civie.getMovCounter(), moveTicks);
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

        this.civie.setMoveStrat(new ScaredMoveStrat(moveTicks));
        List<Position> posList = civie.genMoves(room);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, times(0)).elemRay(any(), any());
    }

    @Test
    public void scaredStratTest() {
        Room room = Mockito.mock(Room.class);
        makeSkaneVisible(room, civie);

        this.civie.setMoveStrat(new ScaredMoveStrat(moveTicks));
        List<Position> posList = civie.genMoves(room);
        assertEquals(posList.size(), 2);

        assertEquals(posList.get(0), civie.moveDown());
        assertEquals(posList.get(1), civie.moveLeft());

        Mockito.verify(room, times(1)).elemRay(any(), any());
        Mockito.verify(room, atLeastOnce()).getSkanePos();
    }

    @Test
    public void scaredStratObstructedTest() {
        Room room = Mockito.mock(Room.class);

        makeSkaneObstructed(room, civie);

        this.civie.setMoveStrat(new ScaredMoveStrat(moveTicks));
        List<Position> posList = civie.genMoves(room);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, times(1)).elemRay(any(), any());
        Mockito.verify(room, atLeastOnce()).getSkanePos();
    }

    @Test
    public void meleeStratBuryTest() {
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.isSkaneBury())
                .thenReturn(true);

        this.civie.setMoveStrat(new MeleeMoveStrat(moveTicks));
        List<Position> posList = civie.genMoves(room);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, times(0)).elemRay(any(), any());
    }

    @Test
    public void meleeStratTest() {
        Room room = Mockito.mock(Room.class);
        makeSkaneVisible(room, civie);

        this.civie.setMoveStrat(new MeleeMoveStrat(moveTicks));
        List<Position> posList = civie.genMoves(room);
        assertEquals(posList.size(), 4); // can see 2 skane parts

        assertEquals(posList.get(0), civie.moveRight());
        assertEquals(posList.get(1), civie.moveUp());
        assertEquals(posList.get(2), civie.moveRight());
        assertEquals(posList.get(3), civie.moveUp());

        Mockito.verify(room, times(2)).elemRay(any(), any());
    }

    @Test
    public void meleeStratObstructedTest() {
        Room room = Mockito.mock(Room.class);
        makeSkaneObstructed(room, civie);

        ska.dropScent(1);
        Mockito.when(room.elemRay(any(), any())).thenReturn(new ArrayList<>());

        this.civie.setMoveStrat(new MeleeMoveStrat(moveTicks));
        List<Position> posList = civie.genMoves(room);
        assertEquals(posList.size(), 2); // can see scent

        assertEquals(posList.get(0), civie.moveRight());
        assertEquals(posList.get(1), civie.moveUp());

        // 1 head, 1 body, 1 scent
        Mockito.verify(room, times(3)).elemRay(any(), any());
    }
}
