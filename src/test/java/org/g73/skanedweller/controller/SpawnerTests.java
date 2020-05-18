package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.creator.Creator;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

public class SpawnerTests {
    //@Test
    //public void spawn() {
    //    Creator cr = Mockito.mock(Creator.class);
    //    Element e = Mockito.mock(Element.class);
    //    Mockito.when(cr.create(any(Position.class))).thenReturn(e);
    //    Position pos = new Position(20, 21);
    //    int cnt = 3, delay = 10;

    //    Spawner spawner = new Spawner(cnt, delay, cr, pos);
    //    Room room = Mockito.mock(Room.class);
    //    InOrder order = Mockito.inOrder(room);

    //    for (int i=0; i<cnt+1; ++i) {
    //        for (int j=0; j<delay; ++j) {
    //            spawner.update(room);
    //            order.verify(room, never()).addElement(any(Element.class));
    //        }
    //        spawner.update(room);
    //        order.verify(room, atLeastOnce()).addElement(any(Element.class));
    //    }

    //    // Max Enemies spawned
    //    for (int j=0; j<delay * 2; ++j) {
    //        spawner.update(room);
    //        order.verify(room, never()).addElement(any(Element.class));
    //    }
    //}
}
