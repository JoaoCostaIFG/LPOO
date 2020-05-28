package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;

public interface AttackStrategy<T extends Element> {
    boolean attack(Room room, T me, Element target);
}
