package org.g73.skanedweller.controller.creator.spawners_creator;

import org.g73.skanedweller.controller.Spawner;
import org.g73.skanedweller.controller.creator.elements_creator.RangedCreator;
import org.g73.skanedweller.model.Position;

public class RangedSpawnerCreator extends SpawnerCreator {
    public RangedSpawnerCreator() {
        super(30 * 5, 5);
    }

    @Override
    public Spawner create(Position pos) {
        return new Spawner(super.getMaxEntities(), super.getSpawnCD(),
                new RangedCreator(), pos);
    }
}
