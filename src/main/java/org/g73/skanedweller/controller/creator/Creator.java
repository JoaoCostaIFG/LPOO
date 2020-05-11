package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.Element;

public interface Creator {
    public Element create(Position pos);
}
