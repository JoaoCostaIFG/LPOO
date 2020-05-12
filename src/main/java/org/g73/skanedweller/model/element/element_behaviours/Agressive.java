package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.element.Element;

public interface Agressive {
    int getAtk();

    void setAtk(int atk);

    int getRange();

    void setRange(int range);

    void setAtkStrat(AttackStrategy attackStrat);

    boolean attack(Element me, Element target);

    int getAtkCounter();

    void setAtkCounter(int numTicks);

    void tickAtkCounter();
}
