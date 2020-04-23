import Controller.MovableController;
import Controller.SkaneController;
import Controller.collisionStrategy.CollisionStrategy;
import Controller.collisionStrategy.NullCollision;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import room.Position;
import room.Room;
import room.element.Civilian;
import room.element.MeleeGuy;
import room.element.Wall;
import room.element.skane.Skane;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MovableControllerTests {
    private MovableController controller;

    @Before
    public void setUp() {
        controller = new SkaneController(null, 10);
    }

    @Test
    public void collisionHandler() {
        // Wall wall = Mockito.mock(Wall.class);
        // Doesn't work because Mockito doesn't allow
        // override of final static methods
        // Using normal objects instead.
        Wall wall = new Wall(new Position(1, 1));
        Skane skane = new Skane(1, 1, 1, 1, 1, 1, 1);

        assertEquals(false, controller.handleCollision(skane, wall));
    }
}
