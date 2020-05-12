package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.element.Element;

public interface AttackStrategy {
    boolean attack(Element me, Element target);
}
