package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RayCastTests {
    private RayCast rc;
    private Room room;

    @BeforeEach
    public void setUp() {
        this.rc = new RayCast();
        this.room = Mockito.mock(Room.class);
        Mockito.when(room.getWidth())
                .thenReturn(300);
        Mockito.when(room.getHeight())
                .thenReturn(300);
    }

    private Element setUpPos(Position p) {
        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getPos())
                .thenReturn(p);
        Mockito.when(room.getSamePos(p))
                .thenReturn(new ArrayList<Element>(Collections.singletonList(e)));

        return e;
    }

    @Test
    public void testRayCastUnobstructedOct03() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(232, 57);

        Element e1 = setUpPos(p1);
        Element e2 = setUpPos(p2);

        Mockito.when(room.getSamePos(p2))
                .thenReturn(new ArrayList<Element>(Arrays.asList(e2, e2))); // 2 at destiny

        // Unobstructed view
        List<Element> elemList = rc.elemRay(room, p1, p2);
        assertEquals(elemList.size(), 2);
        for (Element e : elemList)
            assertEquals(e, e2);
        // reverse
        List<Element> elemListRev = rc.elemRay(room, p2, p1);
        assertEquals(elemListRev.size(), 1);
        for (Element e : elemListRev)
            assertEquals(e, e1);
    }

    @Test
    public void testRayCastUnobstructedOct12() {
        Position p1 = new Position(200, 5);
        Position p2 = new Position(1, 280);

        Element e1 = setUpPos(p1);
        Element e2 = setUpPos(p2);

        Mockito.when(room.getSamePos(p1))
                .thenReturn(new ArrayList<Element>(Arrays.asList(e1, e1))); // 2 at source

        // Unobstructed view
        List<Element> elemList = rc.elemRay(room, p1, p2);
        assertEquals(elemList.size(), 1);
        for (Element e : elemList)
            assertEquals(e, e2);
        // reverse
        List<Element> elemListRev = rc.elemRay(room, p2, p1);
        assertEquals(elemListRev.size(), 2);
        for (Element e : elemListRev)
            assertEquals(e, e1);
    }

    @Test
    public void testRayCastObstructed03() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(232, 57);
        Position p3 = new Position(233, 57);

        Element e1 = setUpPos(p1);
        Element e2 = setUpPos(p2);
        Element e3 = setUpPos(p3);

        // Obstructed view
        List<Element> elemList = rc.elemRay(room, p1, p3);
        assertEquals(elemList.size(), 1);
        for (Element e : elemList)
            assertEquals(e, e2);
        // reverse
        List<Element> elemListRev = rc.elemRay(room, p3, p1);
        assertEquals(elemListRev.size(), 1);
        for (Element e : elemListRev)
            assertEquals(e, e2);
    }

    @Test
    public void testRayCastObstructedOct23() {
        Position p1 = new Position(200, 5);
        Position p2 = new Position(2, 279);
        Position p3 = new Position(1, 280);

        Element e1 = setUpPos(p1);
        Element e2 = setUpPos(p2);
        Element e3 = setUpPos(p3);

        // Unobstructed view
        List<Element> elemList = rc.elemRay(room, p1, p3);
        assertEquals(elemList.size(), 1);
        for (Element e : elemList)
            assertEquals(e, e2);
        // reverse
        List<Element> elemListRev = rc.elemRay(room, p3, p1);
        assertEquals(elemListRev.size(), 1);
        for (Element e : elemListRev)
            assertEquals(e, e2);
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
        Position p1 = new Position(400, 400); // this pos is out of Room size
        Position p2 = new Position(1, 1);

        // nothing until end of run
        List<Element> elemList = rc.elemRay(room, p1, p2);
        assertEquals(elemList, new ArrayList<>());
        assertEquals(elemList.size(), 0);
    }
}
