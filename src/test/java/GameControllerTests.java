import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GameControllerTests {
    private GameController game;

    @Before
    public void setUp() throws IOException {
        this.game = new GameController();
    }

    @Test
    public void inputs() {
        assertEquals(1, 1);
    }
}
