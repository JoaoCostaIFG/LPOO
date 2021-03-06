package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;

public class PassiveBehaviour implements Agressive {
    @Override
    public int getAtk() {
        return 0;
    }

    @Override
    public void setAtk(int atk) {
    }

    @Override
    public int getRange() {
        return 0;
    }

    @Override
    public void setRange(int range) {

    }

    @Override
    public AttackStrategy getAtkStrat() {
        return null;
    }

    @Override
    public void setAtkStrat(AttackStrategy attackStrat) {
    }

    @Override
    public boolean attack(Room room, Element me, Element target) {
        return false;
    }

    @Override
    public int getAtkCounter() {
        return 1; // unable to attack
    }

    @Override
    public void setAtkCounter(int numTicks) {
    }

    @Override
    public void tickAtkCounter() {
    }
}
