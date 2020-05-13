package org.g73.skanedweller.model.element;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.element_behaviours.AgressiveBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.ImmortalBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.ImovableBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.NotCollidableBehaviour;

public class Laser extends Element {
    private int duration;
    private boolean ready;

    public Laser(Position pos, int dur, int dmg, int range) {
        super(pos, new AgressiveBehaviour(dmg, range),
                new NotCollidableBehaviour(),
                new ImmortalBehaviour(),
                new ImovableBehaviour());
        this.duration = dur;
        this.ready = false;
    }

    public Laser(int x, int y, int dur, int dmg, int range) {
        this(new Position(x, y), dur, dmg, range);
    }

    public void makeReady() {
        this.ready = true;
    }
     public void makeUnready() {
        this.ready = false;
     }

    public boolean getReadiness() {
        return this.ready;
    }

    public void tickLaser() {
        --duration;
    }

    public int getDuration() {
        return this.duration;
    }
}
