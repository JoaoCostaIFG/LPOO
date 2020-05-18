package org.g73.skanedweller.controller.attack_strategy;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.Laser;
import org.g73.skanedweller.model.element.element_behaviours.AttackStrategy;

public class LaserAttackStrat implements AttackStrategy<Laser> {
    @Override
    public boolean attack(Room room, Laser me, Element target) {
        if (!me.getReadiness())
            return false;
        if (me.getPos().dist(target.getPos()) > me.getRange() && !me.collidesWith(target))
            return false;

        me.makeUnready(); // Makes it disappear after attacking anything
        target.takeDamage(me.getAtk());
        return true;
    }
}
