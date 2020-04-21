import Controller.SkaneController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import room.element.skane.Skane;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SkaneControllerTests {
    SkaneController skaCtr;
    Skane ska;

    @Before
    public void setUp() {
        ska = new Skane(1, 1, 1, 1, 200, 1, 1);
        skaCtr = new SkaneController(ska, 200);
    }

    public void setUpMock() {
        ska = Mockito.mock(Skane.class);
        skaCtr = new SkaneController(ska, 200);
    }

    @Test
    public void bury() {
        assertEquals(false, ska.isBury());
        skaCtr.toggleBury();
        assertEquals(true, ska.isBury());
        skaCtr.toggleBury();
        assertEquals(false, ska.isBury());

        ska.setOxygenLevel(150);
        assertEquals(false, ska.isBury());
        skaCtr.toggleBury();
        assertEquals(false, ska.isBury());
    }

    @Test
    public void inhale() {
        ska.bury(true);
        int n = 100;
        for (int i = 0; i < n; ++i)
            skaCtr.inhale();
        assertEquals(200 - n, ska.getOxygenLevel());

        ska.bury(false);
        skaCtr.inhale();
        skaCtr.inhale();
        assertEquals(n + (200 / 50) * 2, ska.getOxygenLevel());
        for (int i = 0; i < n; ++i)
            skaCtr.inhale();
        assertEquals(200, ska.getOxygenLevel());
    }

    @Test
    public void damage() {
        setUpMock();
        skaCtr.takeDamage(300);
        verify(ska).takeDamage(300);
        verify(ska, times(300)).shrink();

        reset(ska);
        skaCtr.takeDamage(-10);
        verify(ska, never()).takeDamage(any(Integer.class));
        verify(ska, never()).shrink();
    }

    @Test
    public void eat() {
        setUpMock();
        when(ska.getHp()).thenReturn(2);
        skaCtr.nom(300);
        verify(ska).setHp(300 + 2);
        verify(ska, times(300)).grow();

        reset(ska);
        skaCtr.nom(-10);
        verify(ska, never()).setHp(any(Integer.class));
        verify(ska, never()).grow();
    }
}
