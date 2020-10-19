package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;

public interface Agressive {
    int getAtk();

    void setAtk(int atk);

    int getRange();

    void setRange(int range);

    AttackStrategy getAtkStrat();

    void setAtkStrat(AttackStrategy attackStrat);

    boolean attack(Room room, Element me, Element target);

    int getAtkCounter();

    void setAtkCounter(int numTicks);

    void tickAtkCounter();
}
