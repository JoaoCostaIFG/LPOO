package org.g73.skanedweller.controller.creator.spawners_creator;

import org.g73.skanedweller.controller.Spawner;
import org.g73.skanedweller.controller.creator.Creator;

public abstract class SpawnerCreator extends Creator<Spawner> {
    private Integer spawnCD;
    private Integer maxEntities;

    public SpawnerCreator(Integer spawnCD, Integer maxEntities) {
        this.spawnCD = spawnCD;
        this.maxEntities = maxEntities;
    }

    public Integer getSpawnCD() {
        return spawnCD;
    }

    public Integer getMaxEntities() {
        return maxEntities;
    }
}
