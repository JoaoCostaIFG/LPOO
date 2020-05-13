package org.g73.skanedweller.controller.attack_strategy;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.Laser;
import org.g73.skanedweller.model.element.RangedGuy;
import org.g73.skanedweller.model.element.element_behaviours.AttackStrategy;

import java.util.List;

public class RangedGuyAtkStrat implements AttackStrategy<RangedGuy> {
    private int ticksBetweenAttacks;
    private int laserDur;

    public RangedGuyAtkStrat(int ticksBetweenAttacks, int laserDur) {
        this.ticksBetweenAttacks = ticksBetweenAttacks;
        this.laserDur = laserDur;
    }

    private void tickLasers(Room room, RangedGuy me, Element target) {
        me.getLasers().removeIf(l -> (l.getDuration() <= 0));
        for (Laser l : me.getLasers()) {
            l.tickLaser();
            l.attack(room, target);
        }
    }

    @Override
    public boolean attack(Room room, RangedGuy me, Element target) {
        me.setAtkCounter(ticksBetweenAttacks);
        tickLasers(room, me, target);

        if (me.getPos().dist(target.getPos()) > me.getRange() && !me.collidesWith(target))
            return false;

        LaserAttackStrat laserAttackStrat = new LaserAttackStrat();
        List<Position> posToShoot = room.getRayLine(me.getPos(), target.getPos());
        for (Position p : posToShoot) {
            if (!p.equals(me.getPos())) {
                Laser l = me.shoot(p, laserDur, 0, laserAttackStrat);
                l.makeReady();
            }
        }

        for (Laser l : me.getLasers())
            l.attack(room, target);

        return true;
    }

}
