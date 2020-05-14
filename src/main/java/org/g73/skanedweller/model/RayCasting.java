package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.Element;

import java.util.List;

public interface RayCasting {
    List<Position> posRay(Room room, Position s, Position t);

    public List<Element> elemRay(Room room, Position s, Position t);
}
