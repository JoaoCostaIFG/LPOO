package org.g73.skanedweller.model.element.element_behaviours;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;

public class AgressiveBehaviour implements Agressive {
    private int atk;
    private int range;
    private int atkCounter = 0;
    private AttackStrategy attackStrat;

    public AgressiveBehaviour(int atk, int range) {
        this.atk = atk;
        this.range = range;
    }

    @Override
    public int getAtk() {
        return this.atk;
    }

    @Override
    public void setAtk(int atk) {
        this.atk = atk;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public void setAtkStrat(AttackStrategy attackStrat) {
        this.attackStrat = attackStrat;
    }
    
    @Override
    public AttackStrategy getAtkStrat() {
        return this.attackStrat;
    }

    @Override
    public boolean attack(Room room, Element me, Element target) {
        return attackStrat.attack(room, me, target);
    }

    @Override
    public int getAtkCounter() {
        return atkCounter;
    }

    @Override
    public void setAtkCounter(int numTicks) {
        atkCounter = numTicks;
    }

    @Override
    public void tickAtkCounter() {
        --atkCounter;
    }
}
