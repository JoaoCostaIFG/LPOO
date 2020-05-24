package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.creator.elements_creator.ElementCreator;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.observe.Observer;

public class Spawner implements Observer<Room> {
    private Integer delay;
    private Integer currTick;
    private Integer currCount;
    private Integer maxCount;
    private ElementCreator creator;
    private Position spawningPosition;

    public Spawner(Integer maxCount, Integer delay, ElementCreator creator, Position pos) {
        this.maxCount = maxCount;
        this.delay = delay;
        this.creator = creator;
        this.spawningPosition = pos;
        this.currTick = 0;
        this.currCount = 0;
    }

    public void update(Room room) {
        if (currTick < delay) {
            ++currTick;
            return;
        }

        if (currCount + 1 > maxCount)
            return;

        currTick = 0;
        room.addElement(creator.create(spawningPosition));
    }

    @Override
    public void changed(Room observable) {
        currCount = 0;
        Object trackObj = creator.create(spawningPosition);
        for(Element e: observable.getElements()) {
            if (e.getClass().equals(trackObj.getClass())) {
                ++currCount;
            }
        }
    }

    public Position getSpawningPosition() {
        return spawningPosition;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public Integer getDelay() {
        return delay;
    }
}
