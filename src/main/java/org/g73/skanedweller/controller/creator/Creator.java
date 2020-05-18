package org.g73.skanedweller.controller.creator;


import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Element;

public abstract class Creator<T extends Element> {
    public abstract T create(Position pos);
}
