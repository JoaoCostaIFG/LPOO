package org.g73.skanedweller.controller.creator.spawners_creator;

import org.g73.skanedweller.controller.Spawner;
import org.g73.skanedweller.controller.creator.elements_creator.MeleeCreator;
import org.g73.skanedweller.model.Position;

public class MeleeSpawnerCreator extends SpawnerCreator {
    public MeleeSpawnerCreator() {
        super(30*10, 10);
    }

    @Override
    public Spawner create(Position pos) {
        return new Spawner(this.getMaxEntities(), this.getSpawnCD(),
                new MeleeCreator(), pos);
    }
}
