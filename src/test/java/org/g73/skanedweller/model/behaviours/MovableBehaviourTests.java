package org.g73.skanedweller.model.behaviours;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.MovableBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.MoveStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MovableBehaviourTests {
    private Room room;
    private MoveStrategy strat;
    private MovableBehaviour behaviour;

    @Before
    public void setUp() {
        this.room = Mockito.mock(Room.class);
        this.strat = Mockito.mock(MoveStrategy.class);
        this.behaviour = new MovableBehaviour();
    }

    @Test
    public void testImovInit() {
        assertEquals(behaviour.getMovCounter(), 0);
    }

    @Test
    public void testMovCounter() {
        int movCounter = 2;
        behaviour.setMovCounter(movCounter);
        assertEquals(behaviour.getMovCounter(),  movCounter);

        for (int i = 1; i <= movCounter; ++i ){
            behaviour.tickMovCounter();
            assertEquals(behaviour.getMovCounter(), movCounter - i);
        }
    }

    @Test
    public void testMovStrat() {
        List<Position> pos = new ArrayList<>();
        pos.add(new Position(1, 1));
        Element e = Mockito.mock(Element.class);

        behaviour.setMoveStrat(strat);

        Mockito.when(strat.genMoves(room, e))
                .thenReturn(pos);
        assertEquals(behaviour.genMoves(room, e), pos);

        Mockito.when(strat.genMoves(room, e))
                .thenReturn(new ArrayList<>());
        assertEquals(behaviour.genMoves(room, e), new ArrayList<>());
    }
}
