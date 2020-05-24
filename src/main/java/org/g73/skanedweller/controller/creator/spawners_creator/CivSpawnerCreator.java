package org.g73.skanedweller.controller.creator.spawners_creator;

import org.g73.skanedweller.controller.Spawner;
import org.g73.skanedweller.controller.creator.elements_creator.CivieCreator;
import org.g73.skanedweller.model.Position;

public class CivSpawnerCreator extends SpawnerCreator {
    public CivSpawnerCreator() {
        super(10 * 1, 30);
    }

    @Override
    public Spawner create(Position pos) {
        return new Spawner(this.getMaxEntities(), this.getSpawnCD(),
                new CivieCreator(), pos);
    }
}
