package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.movement_strategy.MeleeMoveStrat;
import org.g73.skanedweller.controller.movement_strategy.RangedMoveStrat;
import org.g73.skanedweller.controller.movement_strategy.ScaredMoveStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.skane.Scent;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

public class MovementStratTests {
    private final int moveTicks = 5;
    private final Position skaPos = new Position(5, 5);
    private final Position skaBodyPos = new Position(5, 6);
    private final Position ePos = new Position(4, 4);
    private final Position ePosDown = new Position(4, 5);
    private final Position ePosLeft = new Position(3, 4);
    private final Position ePosRight = new Position(5, 4);
    private final Position ePosUp = new Position(4, 3);

    private Element e;
    private Skane ska;
    private SkaneBody skaBody;
    private Room room;

    @Before
    public void setUp() {
        this.e = Mockito.mock(Element.class);
        Mockito.when(e.getMovCounter())
                .thenReturn(0);
        Mockito.when(e.getPos())
                .thenReturn(ePos);
        Mockito.when(e.moveDown())
                .thenReturn(ePosDown);
        Mockito.when(e.moveLeft())
                .thenReturn(ePosLeft);
        Mockito.when(e.moveRight())
                .thenReturn(ePosRight);
        Mockito.when(e.moveUp())
                .thenReturn(ePosUp);

        this.ska = Mockito.mock(Skane.class);
        Mockito.when(ska.getPos())
                .thenReturn(skaPos);
        Mockito.when(ska.isBury())
                .thenReturn(false);
        Mockito.when(ska.isBury())
                .thenReturn(false);
        this.skaBody = Mockito.mock(SkaneBody.class);
        Mockito.when(skaBody.getPos())
                .thenReturn(skaBodyPos);
        Mockito.when(ska.getBody())
                .thenReturn(new ArrayList<>(Collections.singletonList(skaBody)));

        this.room = Mockito.mock(Room.class);
        roomMockSetSkaneInfo();
    }

    @After
    public void testSetMovCoutner() {
        Mockito.verify(e).setMovCounter(moveTicks);
    }

    private void roomMockSetSkaneInfo() {
        Mockito.when(room.getSkane())
                .thenReturn(ska);
        Position p = ska.getPos();
        Mockito.when(room.getSkanePos())
                .thenReturn(p);
        boolean b = ska.isBury();
        Mockito.when(room.isSkaneBury())
                .thenReturn(b);
    }

    private void makeSkaneVisible() {
        // making it so skane is visible
        Mockito.when(room.isSkaneBury())
                .thenReturn(false);
        Mockito.when(room.isSkanePos(skaPos))
                .thenReturn(true);
        Mockito.when(room.isSkanePos(skaBodyPos))
                .thenReturn(true);

        Mockito.when(room.elemRay(ePos, skaPos))
                .thenReturn(new ArrayList<>(Collections.singletonList(ska)));
        Mockito.when(room.elemRay(ePos, skaBodyPos))
                .thenReturn(new ArrayList<>(Collections.singletonList(skaBody)));

        roomMockSetSkaneInfo();
    }

    private void makeSkaneObstructed() {
        // making it so raycast hits something other than skane
        Mockito.when(room.isSkaneBury())
                .thenReturn(false);
        Mockito.when(room.isSkanePos(skaPos))
                .thenReturn(false);
        Mockito.when(room.isSkanePos(skaBodyPos))
                .thenReturn(false);

        Mockito.when(room.elemRay(ePos, skaPos))
                .thenReturn(new ArrayList<>(Collections.singletonList(ska)));
        Mockito.when(room.elemRay(ePos, skaBodyPos))
                .thenReturn(new ArrayList<>(Collections.singletonList(skaBody)));

        Mockito.when(room.isSkanePos(any()))
                .thenReturn(false);

        roomMockSetSkaneInfo();
    }

    @Test
    public void testScaredStratBury() {
        Mockito.when(room.isSkaneBury())
                .thenReturn(true);

        ScaredMoveStrat strat = new ScaredMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, never())
                .elemRay(any(), any());
    }

    @Test
    public void testScaredStrat() {
        makeSkaneVisible();

        ScaredMoveStrat strat = new ScaredMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 2);
        assertEquals(posList.get(0), ePosUp);
        assertEquals(posList.get(1), ePosLeft);

        Mockito.verify(room).elemRay(ePos, skaPos);
        Mockito.verify(room, atLeastOnce()).getSkanePos();
    }

    @Test
    public void testScaredStratEq() {
        makeSkaneVisible();

        // e is at same pos as ska
        Mockito.when(e.getPos())
                .thenReturn(skaPos);
        Mockito.when(e.moveDown())
                .thenReturn(new Position(5, 6));
        Mockito.when(e.moveLeft())
                .thenReturn(new Position(4, 5));
        Mockito.when(e.moveRight())
                .thenReturn(new Position(6, 5));
        Mockito.when(e.moveUp())
                .thenReturn(new Position(5, 4));
        Mockito.when(room.elemRay(skaPos, skaPos))
                .thenReturn(new ArrayList<>(Collections.singletonList(ska)));

        ScaredMoveStrat strat = new ScaredMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 4);
        assertEquals(posList.get(0), e.moveDown());
        assertEquals(posList.get(1), e.moveUp());
        assertEquals(posList.get(2), e.moveLeft());
        assertEquals(posList.get(3), e.moveRight());

        Mockito.verify(room).elemRay(skaPos, skaPos);
        Mockito.verify(room, atLeastOnce()).getSkanePos();
    }

    @Test
    public void testScaredStratObstructed() {
        makeSkaneObstructed();

        ScaredMoveStrat strat = new ScaredMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 0);

        Mockito.verify(room).elemRay(any(), any());
        Mockito.verify(room).getSkanePos();
    }

    @Test
    public void testMeleeStratBury() {
        Mockito.when(room.isSkaneBury())
                .thenReturn(true);

        MeleeMoveStrat strat = new MeleeMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, never()).elemRay(any(), any());
    }

    @Test
    public void testMeleeStrat() {
        makeSkaneVisible();

        MeleeMoveStrat strat = new MeleeMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 4); // can see 2 skane parts

        assertEquals(posList.get(0), ePosDown);
        assertEquals(posList.get(1), ePosRight);
        assertEquals(posList.get(2), ePosDown);
        assertEquals(posList.get(3), ePosRight);

        Mockito.verify(room).elemRay(ePos, skaPos);
        Mockito.verify(room).elemRay(ePos, skaBodyPos);
    }

    @Test
    public void testMeleeStratEq() {
        makeSkaneVisible();

        // e is at same pos as ska
        Mockito.when(e.getPos())
                .thenReturn(skaPos);
        Mockito.when(e.moveDown())
                .thenReturn(new Position(5, 6));
        Mockito.when(e.moveLeft())
                .thenReturn(new Position(4, 5));
        Mockito.when(e.moveRight())
                .thenReturn(new Position(6, 5));
        Mockito.when(e.moveUp())
                .thenReturn(new Position(5, 4));
        Mockito.when(room.elemRay(skaPos, skaPos))
                .thenReturn(new ArrayList<>(Collections.singletonList(ska)));

        MeleeMoveStrat strat = new MeleeMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 2);
        assertEquals(posList.get(0), e.moveUp());
        assertEquals(posList.get(1), e.moveLeft());

        Mockito.verify(room).elemRay(skaPos, skaPos);
        Mockito.verify(room).elemRay(skaPos, skaBodyPos);
    }

    @Test
    public void testMeleeStratObstructed() {
        makeSkaneObstructed();

        LinkedHashSet<Scent> scents = new LinkedHashSet<>();
        scents.add(new Scent(new Position(0, 20), 2));
        Mockito.when(ska.getScentTrail())
                .thenReturn(scents);
        Mockito.when(room.elemRay(any(), any()))
                .thenReturn(new ArrayList<>());

        MeleeMoveStrat strat = new MeleeMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 2); // can see scent

        assertEquals(posList.get(0), e.moveDown());
        assertEquals(posList.get(1), e.moveLeft());

        // 1 head, 1 body, 1 scent
        Mockito.verify(room).elemRay(ePos, skaPos);
        Mockito.verify(room).elemRay(ePos, skaBodyPos);
        Mockito.verify(room).elemRay(ePos, new Position(0, 20));
    }

    @Test
    public void testRangedStratBury() {
        Mockito.when(room.isSkaneBury())
                .thenReturn(true);

        RangedMoveStrat strat = new RangedMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 0);

        Mockito.verify(room, never()).elemRay(any(), any());
    }

    @Test
    public void testRangedStrat() {
        makeSkaneVisible();

        RangedMoveStrat strat = new RangedMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 4); // can see 2 skane parts

        assertEquals(posList.get(0), ePosDown);
        assertEquals(posList.get(1), ePosRight);
        assertEquals(posList.get(2), ePosDown);
        assertEquals(posList.get(3), ePosRight);

        Mockito.verify(room).elemRay(ePos, skaPos);
        Mockito.verify(room).elemRay(ePos, skaBodyPos);
    }

    @Test
    public void testRangedStratInRange() {
        makeSkaneVisible();

        Mockito.when(e.getRange())
                .thenReturn(2);

        RangedMoveStrat strat = new RangedMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 0); // can see 2 skane parts

        Mockito.verify(room).elemRay(ePos, skaPos);
        Mockito.verify(room, never()).elemRay(ePos, skaBodyPos);
    }

    @Test
    public void testRangedStratInRangeBody() {
        makeSkaneVisible();

        Mockito.when(e.getPos())
                .thenReturn(skaBodyPos);
        Mockito.when(e.moveDown())
                .thenReturn(new Position(5, 7));
        Mockito.when(e.moveLeft())
                .thenReturn(new Position(4, 6));
        Mockito.when(e.moveRight())
                .thenReturn(new Position(6, 6));
        Mockito.when(e.moveUp())
                .thenReturn(new Position(5, 5));

        Mockito.when(e.getRange())
                .thenReturn(1);

        RangedMoveStrat strat = new RangedMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 0); // can see 2 skane parts

        Mockito.verify(room).elemRay(skaBodyPos, skaPos);
        Mockito.verify(room).elemRay(skaBodyPos, skaBodyPos);
    }

    @Test
    public void testRangedStratInRangeEq() {
        makeSkaneVisible();

        Mockito.when(e.getPos())
                .thenReturn(skaBodyPos);
        Mockito.when(e.moveDown())
                .thenReturn(new Position(5, 7));
        Mockito.when(e.moveLeft())
                .thenReturn(new Position(4, 6));
        Mockito.when(e.moveRight())
                .thenReturn(new Position(6, 6));
        Mockito.when(e.moveUp())
                .thenReturn(new Position(5, 5));

        Mockito.when(e.getRange())
                .thenReturn(1);

        RangedMoveStrat strat = new RangedMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 0); // can see 2 skane parts

        Mockito.verify(room).elemRay(skaBodyPos, skaPos);
        Mockito.verify(room).elemRay(skaBodyPos, skaBodyPos);
    }

    @Test
    public void testRangedStratEq() {
        makeSkaneVisible();

        // e is at same pos as ska
        Mockito.when(e.getPos())
                .thenReturn(skaPos);
        Mockito.when(e.moveDown())
                .thenReturn(new Position(5, 6));
        Mockito.when(e.moveLeft())
                .thenReturn(new Position(4, 5));
        Mockito.when(e.moveRight())
                .thenReturn(new Position(6, 5));
        Mockito.when(e.moveUp())
                .thenReturn(new Position(5, 4));
        Mockito.when(room.elemRay(skaPos, skaPos))
                .thenReturn(new ArrayList<>(Collections.singletonList(ska)));

        RangedMoveStrat strat = new RangedMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 2);
        assertEquals(posList.get(0), e.moveUp());
        assertEquals(posList.get(1), e.moveLeft());

        Mockito.verify(room).elemRay(skaPos, skaPos);
        Mockito.verify(room).elemRay(skaPos, skaBodyPos);
    }

    @Test
    public void testRangedStratObstructed() {
        makeSkaneObstructed();

        LinkedHashSet<Scent> scents = new LinkedHashSet<>();
        scents.add(new Scent(new Position(0, 20), 2));
        Mockito.when(ska.getScentTrail())
                .thenReturn(scents);
        Mockito.when(room.elemRay(any(), any()))
                .thenReturn(new ArrayList<>());

        RangedMoveStrat strat = new RangedMoveStrat(moveTicks);
        List<Position> posList = strat.genMoves(room, e);
        assertEquals(posList.size(), 2); // can see scent

        assertEquals(posList.get(0), e.moveDown());
        assertEquals(posList.get(1), e.moveLeft());

        // 1 head, 1 body, 1 scent
        Mockito.verify(room).elemRay(ePos, skaPos);
        Mockito.verify(room).elemRay(ePos, skaBodyPos);
        Mockito.verify(room).elemRay(ePos, new Position(0, 20));
    }
}
