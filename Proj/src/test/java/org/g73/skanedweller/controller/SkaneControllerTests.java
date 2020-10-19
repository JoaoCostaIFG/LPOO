package org.g73.skanedweller.controller;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.view.EVENT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class SkaneControllerTests {
    private final int scentDur = 3;
    private final int oxy = 200;
    SkaneController skaCtr;
    Skane ska;
    Room room;

    @BeforeEach
    public void setUp() {
        ska = Mockito.mock(Skane.class);
        skaCtr = new SkaneController(ska, scentDur);
        room = Mockito.mock(Room.class);

        resetSka();
    }

    private void resetSka() {
        Mockito.reset(ska);

        Mockito.when(ska.isBury())
                .thenReturn(false);
        Mockito.when(ska.getOxygenLevel())
                .thenReturn(oxy);
        Mockito.when(ska.getMaxOxygenLevel())
                .thenReturn(oxy);
    }

    private void burySka() {
        Mockito.when(ska.isBury())
                .thenReturn(true);
    }

    @Test
    public void testSkaCtrlBury() {
        // bury ska
        skaCtr.toggleBury();
        Mockito.verify(ska).isBury();
        Mockito.verify(ska).getOxygenLevel();
        Mockito.verify(ska).getMaxOxygenLevel();
        Mockito.verify(ska).bury(true);

        // unbury ska
        resetSka();
        burySka();
        skaCtr.toggleBury();
        Mockito.verify(ska).isBury();
        Mockito.verify(ska, never()).getOxygenLevel();
        Mockito.verify(ska, never()).getMaxOxygenLevel();
        Mockito.verify(ska).bury(false);

        // can't bury ska cause not full oxy
        resetSka();
        Mockito.when(ska.getOxygenLevel())
                .thenReturn(oxy - 1);
        skaCtr.toggleBury();
        Mockito.verify(ska).isBury();
        Mockito.verify(ska).getOxygenLevel();
        Mockito.verify(ska).getMaxOxygenLevel();
        Mockito.verify(ska, never()).bury(true);
    }

    @Test
    public void testSkaCtrlInhale() {
        burySka();
        int numInhales = 100;
        for (int i = 1; i <= numInhales; ++i) {
            skaCtr.inhale();
            Mockito.verify(ska).setOxygenLevel(oxy - i);
            Mockito.when(ska.getOxygenLevel())
                    .thenReturn(oxy - i);
        }
        Mockito.verify(ska, times(numInhales)).getOxygenLevel();
        Mockito.verify(ska, times(numInhales)).isBury();
        Mockito.verify(ska, never()).bury(false);

        // recover oxygen levels
        Mockito.when(ska.isBury())
                .thenReturn(false);
        for (int i = 1; i < numInhales / 2; ++i) { // recovey rate is 2 units per inhale
            skaCtr.inhale();
            // recovering oxy involves a second pass through (half) the values
            Mockito.verify(ska, times(2)).setOxygenLevel(oxy - numInhales + 2 * i);
            Mockito.when(ska.getOxygenLevel())
                    .thenReturn(oxy - numInhales + 2 * i);
        }

        skaCtr.inhale();
        Mockito.verify(ska).setOxygenLevel(oxy);
    }

    @Test
    public void testGoOutOfOxy() {
        burySka();

        int numInhales = 200;
        for (int i = 1; i < numInhales; ++i) {
            skaCtr.inhale();
            Mockito.verify(ska).setOxygenLevel(oxy - i);
            Mockito.when(ska.getOxygenLevel())
                    .thenReturn(oxy - i);
        }

        // get to 0 oxy
        Mockito.verify(ska, never()).bury(false);
        skaCtr.inhale();
        Mockito.verify(ska).setOxygenLevel(0);
        Mockito.when(ska.getOxygenLevel())
                .thenReturn(0);

        // inhale while burried at 0 oxy
        Mockito.verify(ska, never()).bury(false);
        skaCtr.inhale();
        Mockito.verify(ska).setOxygenLevel(0);
        Mockito.verify(ska).bury(false);
    }

    @Test
    public void testUnburriedMaxOxyInhale() {
        skaCtr.inhale();
        verify(ska, never()).setOxygenLevel(anyInt());
    }

    @Test
    public void update() {
        Mockito.when(ska.getPos())
                .thenReturn(new Position(1, 1));
        Position p;

        // left
        p = new Position(0, 1);
        Mockito.when(ska.moveLeft())
                .thenReturn(p);
        skaCtr.handleEvent(EVENT.MoveLeft, room);
        verify(ska).moveLeft();
        verify(ska).dropScent(scentDur);
        verify(ska).tickScentTrail();
        verify(ska).setPos(p);
        verify(room).getCollidingElemsInPos(ska, p);

        // right
        p = new Position(2, 1);
        Mockito.when(ska.moveRight())
                .thenReturn(p);
        skaCtr.handleEvent(EVENT.MoveRight, room);
        verify(ska).moveRight();
        verify(ska, times(2)).dropScent(scentDur);
        verify(ska, times(2)).tickScentTrail();
        verify(ska).setPos(p);
        verify(room).getCollidingElemsInPos(ska, p);

        // up
        p = new Position(1, 0);
        Mockito.when(ska.moveUp())
                .thenReturn(p);
        skaCtr.handleEvent(EVENT.MoveUp, room);
        verify(ska).moveUp();
        verify(ska, times(3)).dropScent(scentDur);
        verify(ska, times(3)).tickScentTrail();
        verify(ska).setPos(p);
        verify(room).getCollidingElemsInPos(ska, p);

        // down
        p = new Position(1, 2);
        Mockito.when(ska.moveDown())
                .thenReturn(p);
        skaCtr.handleEvent(EVENT.MoveDown, room);
        verify(ska).moveDown();
        verify(ska, times(4)).dropScent(scentDur);
        verify(ska, times(4)).tickScentTrail();
        verify(ska).setPos(p);
        verify(room).getCollidingElemsInPos(ska, p);
    }

    @Test
    public void testDontCareEventUpdate() {
        // skaCtrl doesn't handle the quit game event
        when(ska.getPos())
                .thenReturn(new Position(5, 5));
        skaCtr.handleEvent(EVENT.QuitGame, room);

        verify(ska, never()).setPos(any());
    }

    @Test
    public void testSkaCtrlScent() {
        skaCtr.tickScentTrail();
        verify(ska).tickScentTrail();
        verify(ska).dropScent(scentDur);
    }

    @Test
    public void testSkaCtrlUpdateFunc() {
        skaCtr.setEvent(EVENT.Bury);
        skaCtr.update(room);

        Mockito.verify(ska, times(2)).isBury();
        Mockito.verify(ska, times(2)).getOxygenLevel();
        Mockito.verify(ska, times(2)).getMaxOxygenLevel();

        Mockito.verify(ska).bury(true);
    }
}
