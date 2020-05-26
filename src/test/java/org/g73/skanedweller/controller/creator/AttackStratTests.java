package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.controller.attack_strategy.LaserAtkStrat;
import org.g73.skanedweller.controller.attack_strategy.MeleeAtkStrat;
import org.g73.skanedweller.controller.attack_strategy.RangedGuyAtkStrat;
import org.g73.skanedweller.controller.attack_strategy.SkaneAttackStrategy;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.Laser;
import org.g73.skanedweller.model.element.RangedGuy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

public class AttackStratTests {
    private Room room;
    private Element e;
    private LaserAtkStrat laser;
    private MeleeAtkStrat melee;
    private RangedGuyAtkStrat ranged;
    private SkaneAttackStrategy skane;
    
    @Before
    public void setUp() {
        room = Mockito.mock(Room.class);;
        e = Mockito.mock(Element.class);
        laser = new LaserAtkStrat();
        skane = new SkaneAttackStrategy();
        melee = new MeleeAtkStrat(30);
        ranged = new RangedGuyAtkStrat(40, 10);
    }
    
    @Test
    public void testLaserAtk() {
        Laser l = Mockito.mock(Laser.class);
        Position origin = new Position(0, 0);
        Position dist5Units = new Position(5, 0);
        Position dist10Units = new Position(0, 10);
        Mockito.when(l.getReadiness()).thenReturn(false);
        assertFalse(laser.attack(room, l, e));

        Mockito.when(l.getReadiness()).thenReturn(true);
        Mockito.when(l.collidesWith(e)).thenReturn(false);
        Mockito.when(l.getPos()).thenReturn(origin);
        Mockito.when(l.getRange()).thenReturn(7);
        Mockito.when(l.getAtk()).thenReturn(7);
        Mockito.when(e.getPos()).thenReturn(dist10Units);
        assertFalse(laser.attack(room, l, e));

        Mockito.when(e.getPos()).thenReturn(dist10Units);
        Mockito.when(l.collidesWith(e)).thenReturn(true);
        assertTrue(laser.attack(room, l, e));

        Mockito.when(e.getPos()).thenReturn(dist5Units);
        assertTrue(laser.attack(room, l, e));
        Mockito.verify(l, times(2)).makeUnready();
        Mockito.verify(e, times(2)).takeDamage(7);
    }
    
    @Test
    public void testSkaneAtkStrat() {
        Element m = Mockito.mock(Element.class);
        assertFalse(skane.attack(room, m, e));
    }
    
    @Test
    public void testMeleeAtkStrat() {
        Element me = Mockito.mock(Element.class);
        Position origin = new Position(0, 0);
        Position dist5Units = new Position(5, 0);
        Position dist10Units = new Position(0, 10);
        Mockito.when(me.getPos()).thenReturn(origin);
        Mockito.when(me.getRange()).thenReturn(7);
        Mockito.when(me.getAtk()).thenReturn(7);
        Mockito.when(e.getPos()).thenReturn(dist10Units);
        assertFalse(melee.attack(room, me, e));

        Mockito.when(e.getPos()).thenReturn(dist10Units);
        Mockito.when(me.collidesWith(e)).thenReturn(true);
        assertTrue(melee.attack(room, me, e));

        Mockito.when(e.getPos()).thenReturn(dist5Units);
        assertTrue(melee.attack(room, me, e));
        Mockito.verify(me, times(3)).setAtkCounter(30);
    }
    
    @Test
    public void testRangedAtkStrat() {
        RangedGuy rangedGuy = Mockito.mock(RangedGuy.class);
        List<Laser> lasers = new ArrayList<>();
        
        Mockito.when(rangedGuy.getLasers()).thenReturn(lasers);
        Mockito.when(rangedGuy.collidesWith(e)).thenReturn(true);
        Mockito.when(rangedGuy.getPos()).thenReturn(new Position(0, 0));
        Mockito.when(rangedGuy.getRange()).thenReturn(3);
        Mockito.when(e.getPos()).thenReturn(new Position(100, 100));
        assertTrue(ranged.attack(room, rangedGuy, e));

        Mockito.when(rangedGuy.collidesWith(e)).thenReturn(false);
        Mockito.when(rangedGuy.getPos()).thenReturn(new Position(0, 0));
        Mockito.when(rangedGuy.getRange()).thenReturn(3);
        Mockito.when(e.getPos()).thenReturn(new Position(1, 0));
        assertTrue(ranged.attack(room, rangedGuy, e));
        
        List<Position> poses = new ArrayList<>(); poses.add(new Position(0, 0));
        Mockito.when(room.posRay(rangedGuy.getPos(), e.getPos())).thenReturn(poses);
        assertFalse(ranged.attack(room, rangedGuy, e));
        
        Position origin = new Position(0, 0); Position dist1Unit = new Position(1, 0);
        Laser l1 = Mockito.mock(Laser.class); Laser l2 = Mockito.mock(Laser.class);
        Mockito.when(rangedGuy.shoot(eq(origin), eq(10), eq(0), any(LaserAtkStrat.class)))
                .thenReturn(l1);
        Mockito.when(rangedGuy.shoot(eq(dist1Unit), eq(10), eq(0), any(LaserAtkStrat.class)))
                .thenReturn(l2);
        poses.add(new Position(1, 0));
        Mockito.when(room.posRay(rangedGuy.getPos(), e.getPos())).thenReturn(poses);
        assertTrue(ranged.attack(room, rangedGuy, e));
        Mockito.verify(l2).makeReady();
//        Mockito.verify(l2).attack(room, e);
    }
    
    @Test
    public void testRangedAtkStratLasers() {
        RangedGuy rangedGuy = Mockito.mock(RangedGuy.class);
        List<Laser> lasers = new ArrayList<>();
        Laser l1 = Mockito.mock(Laser.class); Mockito.when(l1.getDuration()).thenReturn(0);
        Laser l2 = Mockito.mock(Laser.class); Mockito.when(l2.getDuration()).thenReturn(-5);
        Laser l3 = Mockito.mock(Laser.class); Mockito.when(l3.getDuration()).thenReturn(5);
        lasers.add(l1); lasers.add(l2); lasers.add(l3);
        Mockito.when(rangedGuy.getLasers()).thenReturn(lasers);
        
        Mockito.when(rangedGuy.collidesWith(e)).thenReturn(false);
        Mockito.when(rangedGuy.getPos()).thenReturn(new Position(0, 0));
        Mockito.when(rangedGuy.getRange()).thenReturn(3);
        Mockito.when(e.getPos()).thenReturn(new Position(100, 100));
        assertFalse(ranged.attack(room, rangedGuy, e));
        
        assertEquals(1, lasers.size());
        assertEquals(l3, lasers.get(0));
        Mockito.verify(l3).tickLaser();
        Mockito.verify(l3).attack(room, e);
    }
}
