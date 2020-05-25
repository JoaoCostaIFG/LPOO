package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.Wall;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RayCastTests {
//    @Test
//    public void unobstructedRayCastOct03() {
//        Position p1 = new Position(2, 3);
//        Position p2 = new Position(232, 57);
//
//        Wall w1 = new Wall(p1);
//        Wall w2 = new Wall(p2);
//
//        room.addElement(w1);
//        room.addElement(w2);
//        room.addElement(w2);
//
//        // Unobstructed view
//        room.setRayCasting(new RayCast());
//        List<Element> unobstructedElemList = room.elemRay(p1, p2);
//        assertEquals(unobstructedElemList.size(), 2);
//        for (Element e : unobstructedElemList)
//            assertEquals(e, w2);
//    }
//
//    @Test
//    public void unobstructedRayCastOct12() {
//        Position p1 = new Position(200, 5);
//        Position p2 = new Position(1, 280);
//
//        Wall w1 = new Wall(p1);
//        Wall w2 = new Wall(p2);
//
//        room.addElement(w1);
//        room.addElement(w1);
//        room.addElement(w2);
//
//        // Unobstructed view
//        room.setRayCasting(new RayCast());
//        List<Element> unobstructedElemList = room.elemRay(p1, p2);
//        assertEquals(unobstructedElemList.size(), 1);
//        for (Element e : unobstructedElemList)
//            assertEquals(e, w2);
//    }
//
//    @Test
//    public void obstructedRayCast03() {
//        Position p1 = new Position(2, 3);
//        Position p2 = new Position(232, 57);
//        Position p3 = new Position(233, 57);
//
//        Wall w1 = new Wall(p1);
//        Wall w2 = new Wall(p2);
//        Wall w3 = new Wall(p3);
//
//        room.addElement(w1);
//        room.addElement(w2);
//        room.addElement(w3);
//
//        // Obstructed view
//        room.setRayCasting(new RayCast());
//        List<Element> obstructedElemList = room.elemRay(p1, p3);
//        assertEquals(obstructedElemList.size(), 1);
//        for (Element e : obstructedElemList)
//            assertEquals(e, w2);
//    }
//
//    @Test
//    public void obstructedRayCastOct12() {
//        Position p1 = new Position(200, 5);
//        Position p2 = new Position(2, 279);
//        Position p3 = new Position(1, 280);
//
//        Wall w1 = new Wall(p1);
//        Wall w2 = new Wall(p2);
//        Wall w3 = new Wall(p3);
//
//        room.addElement(w1);
//        room.addElement(w2);
//        room.addElement(w3);
//
//        // Unobstructed view
//        room.setRayCasting(new RayCast());
//        List<Element> obstructedElemList = room.elemRay(p1, p3);
//        assertEquals(obstructedElemList.size(), 1);
//        for (Element e : obstructedElemList)
//            assertEquals(e, w2);
//    }
}
