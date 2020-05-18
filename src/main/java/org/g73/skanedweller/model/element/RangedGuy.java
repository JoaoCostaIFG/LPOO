package org.g73.skanedweller.model.element;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.element_behaviours.*;

import java.util.ArrayList;
import java.util.List;

public class RangedGuy extends Element {
    List<Laser> lasers;

    public RangedGuy(Position pos, int hp, int atk, int range) {
        super(pos,
                new AgressiveBehaviour(atk, range),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new MortalBehaviour(hp),
                new MovableBehaviour());

        this.lasers = new ArrayList<>();
    }

    public RangedGuy(int x, int y, int hp, int atk, int range) {
        this(new Position(x, y), hp, atk, range);
    }

    public Laser shoot(Position target, int dur, int thickness, AttackStrategy atkStrat) {
        Laser l = new Laser(target, dur, getAtk(), thickness);
        l.setAtkStrat(atkStrat);
        lasers.add(l);

        return l;
    }

    public List<Laser> getLasers() {
        return lasers;
    }
}
