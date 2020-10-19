package org.g73.skanedweller.controller.creator.elements_creator;

import org.g73.skanedweller.controller.creator.Creator;
import org.g73.skanedweller.model.element.Element;

public abstract class ElementCreator extends Creator<Element> {
    private Integer hp, atk, range;

    public ElementCreator(Integer hp, Integer atk, Integer range) {
        this.hp = hp;
        this.atk = atk;
        this.range = range;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getAtk() {
        return atk;
    }

    public Integer getRange() {
        return range;
    }
}
