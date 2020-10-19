package org.g73.skanedweller.controller.attack_strategy;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.AttackStrategy;

public class MeleeAtkStrat implements AttackStrategy {
    private int ticksBetweenAttacks;

    public MeleeAtkStrat(int ticksBetweenAttacks) {
        this.ticksBetweenAttacks = ticksBetweenAttacks;
    }

    @Override
    public boolean attack(Room room, Element me, Element target) {
        me.setAtkCounter(ticksBetweenAttacks);
        if (me.getPos().dist(target.getPos()) <= me.getRange() || me.collidesWith(target)) {
            target.takeDamage(me.getAtk());
            return true;
        }
        return false;
    }
}
