package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.Spawner;
import org.g73.skanedweller.controller.creator.spawners_creator.CivSpawnerCreator;
import org.g73.skanedweller.controller.creator.spawners_creator.MeleeSpawnerCreator;
import org.g73.skanedweller.controller.creator.spawners_creator.RangedSpawnerCreator;
import org.g73.skanedweller.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpawnerCreatorTests {
    private MeleeSpawnerCreator mlSpCreator;
    private RangedSpawnerCreator rgSpCreator;
    private CivSpawnerCreator civSpCreator;
    
    @BeforeEach
    public void setUp() {
        this.mlSpCreator = new MeleeSpawnerCreator();
        this.rgSpCreator = new RangedSpawnerCreator();
        this.civSpCreator = new CivSpawnerCreator();
    }
    
    @Test
    public void meleeSpawner() {
        Spawner mSp = mlSpCreator.create(new Position(1, 10));
        assertEquals(30 * 10, (int) mSp.getDelay());
        assertEquals(10, (int) mSp.getMaxCount());
        assertEquals(new Position(1, 10), mSp.getSpawningPosition());
    }
    
    @Test
    public void rangedSpawner() {
        Spawner mSp = rgSpCreator.create(new Position(1, 10));
        assertEquals(30 * 5, (int) mSp.getDelay());
        assertEquals(5, (int) mSp.getMaxCount());
    }
    
    @Test
    public void civSpawner() {
        Spawner mSp = civSpCreator.create(new Position(1, 10));
        assertEquals(10 * 1, (int) mSp.getDelay());
        assertEquals(30, (int) mSp.getMaxCount());
    }
}
