package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.creator.Creator;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;

public class Spawner {
    private Integer delay;
    private Integer currTick;
    private Integer currCount;
    private Integer maxCount;
    private Creator creator;
    private Position spawningPosition;

    public Spawner(Integer maxCount, Integer delay, Creator creator, Position pos) {
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

        if (currCount > maxCount)
            return;

        currTick = 0;
        ++currCount;
        // FIXME Use random gen here
        room.addElement(creator.create(spawningPosition));
    }
}
