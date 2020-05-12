package org.g73.skanedweller.controller.attack_strategy;

import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.AttackStrategy;

public class MeleeGuyAtkStrat implements AttackStrategy {
    private int ticksBetweenAttacks;

    public MeleeGuyAtkStrat(int ticksBetweenAttacks) {
        this.ticksBetweenAttacks = ticksBetweenAttacks;
    }

    @Override
    public boolean attack(Element me, Element target) {
        me.setAtkCounter(ticksBetweenAttacks);
        if (me.getPos().dist(target.getPos()) <= me.getRange() || me.collidesWith(target)) {
            target.takeDamage(me.getAtk());
            return true;
        }
        return false;
    }
}
