package org.g73.skanedweller.model;

import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeTry;
import org.g73.skanedweller.model.element.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RayCastTests {
    private final int maxRoomLen = 80;

    private RayCast rc;
    private Room room;

    @BeforeTry
    @BeforeEach
    public void setUp() {
        this.rc = new RayCast();
        this.room = Mockito.mock(Room.class);
        Mockito.when(room.getWidth())
                .thenReturn(maxRoomLen);
        Mockito.when(room.getHeight())
                .thenReturn(maxRoomLen);
    }

    private Element setUpPos(Position p) {
        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getPos())
                .thenReturn(p);
        Mockito.when(room.getSamePos(p))
                .thenReturn(new ArrayList<>(Collections.singletonList(e)));

        return e;
    }

    @Provide
    Arbitrary<Position> insideRoom() {
        Arbitrary<Integer> x = Arbitraries.integers().between(0, maxRoomLen);
        Arbitrary<Integer> y = Arbitraries.integers().between(0, maxRoomLen);

        return Combinators.combine(x, y).as(Position::new);
    }

    @Property
    public void testRayCastUnobstructed(@ForAll("insideRoom") Position p1,
                                        @ForAll("insideRoom") Position p2) {
        Element e1 = setUpPos(p1);
        Element e2 = setUpPos(p2);

        Mockito.when(room.getSamePos(p2))
                .thenReturn(new ArrayList<>(Arrays.asList(e2, e2))); // 2 at destiny

        // Unobstructed view
        List<Element> elemList = rc.elemRay(room, p1, p2);
        if (p1.equals(p2)) {
            assertEquals(elemList.size(), 0);
        } else {
            assertEquals(elemList.size(), 2);
            for (Element e : elemList)
                assertEquals(e, e2);
        }
        // reverse
        List<Element> elemListRev = rc.elemRay(room, p2, p1);
        if (p1.equals(p2)) {
            assertEquals(elemList.size(), 0);
        } else {
            assertEquals(elemListRev.size(), 1);
            for (Element e : elemListRev)
                assertEquals(e, e1);
        }
    }


    @Property
    public void testRayCastObstructed(@ForAll("insideRoom") Position p1,
                                      @ForAll("insideRoom") Position p2) {
        Element e1 = setUpPos(p1);
        Element e2 = setUpPos(p2);

        int x = (p2.getX() + p1.getX()) / 2;
        int y = (p2.getY() + p1.getY()) / 2;
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(x - 1, y - 1));
        posList.add(new Position(x, y - 1));
        posList.add(new Position(x + 1, y - 1));
        posList.add(new Position(x - 1, y));
        posList.add(new Position(x, y));
        posList.add(new Position(x + 1, y));
        posList.add(new Position(x - 1, y + 1));
        posList.add(new Position(x, y + 1));
        posList.add(new Position(x + 1, y + 1));
        for (Position middlePos : posList)
            setUpPos(middlePos);

        Position hitMiddlePos = null;
        List<Element> elemList;

        // Unobstructed view
        elemList = rc.elemRay(room, p1, p2);
        if (p1.equals(p2)) {
            assertEquals(elemList.size(), 0);
        } else {
            assertEquals(elemList.size(), 1);

            hitMiddlePos = elemList.get(0).getPos();
            assertTrue(posList.contains(hitMiddlePos));

            assertTrue(!p1.equals(hitMiddlePos) || !p2.equals(hitMiddlePos));
        }
        // reverse
        elemList = rc.elemRay(room, p2, p1);
        if (p1.equals(p2)) {
            assertEquals(elemList.size(), 0);
            assertNull(hitMiddlePos);
        } else {
            assertEquals(elemList.size(), 1);

            hitMiddlePos = elemList.get(0).getPos();
            assertTrue(posList.contains(hitMiddlePos));

            assertTrue(!p1.equals(hitMiddlePos) || !p2.equals(hitMiddlePos));
        }
    }

    @Test
    public void testRayCastErrorTermX0() {
        Position p1 = new Position(0, 0);
        Position p2 = new Position(9, 3);

        Element e1 = setUpPos(p1);
        Element e2 = setUpPos(p2);

        // Unobstructed view
        List<Element> elemList = rc.elemRay(room, p1, p2);
        assertEquals(elemList.size(), 1);
        for (Element e : elemList)
            assertEquals(e, e2);
        // reverse
        List<Element> elemListRev = rc.elemRay(room, p2, p1);
        assertEquals(elemListRev.size(), 1);
        for (Element e : elemListRev)
            assertEquals(e, e1);
    }

    @Test
    public void testRayCastErrorTermY0() {
        Position p1 = new Position(0, 0);
        Position p2 = new Position(3, 9);

        Element e1 = setUpPos(p1);
        Element e2 = setUpPos(p2);

        // Unobstructed view
        List<Element> elemList = rc.elemRay(room, p1, p2);
        assertEquals(elemList.size(), 1);
        for (Element e : elemList)
            assertEquals(e, e2);
        // reverse
        List<Element> elemListRev = rc.elemRay(room, p2, p1);
        assertEquals(elemListRev.size(), 1);
        for (Element e : elemListRev)
            assertEquals(e, e1);
    }

    @Test
    public void testRayCastHitNothing() {
        Position p1 = new Position(1, 1);
        Position p2 = new Position(1, 2);
        Element e1 = setUpPos(p1);

        // no elements until end of map
        List<Element> elemList = rc.elemRay(room, p1, p2);
        assertEquals(elemList, new ArrayList<>());
        assertEquals(elemList.size(), 0);
    }

    @Test
    public void testRayCastHitNoPos() {
        Position p1 = new Position(100, 100); // this pos is out of Room size
        Position p2 = new Position(1, 1);

        // nothing until end of run
        List<Element> elemList = rc.elemRay(room, p1, p2);
        assertEquals(elemList, new ArrayList<>());
        assertEquals(elemList.size(), 0);
    }
}
