import controller.EnemyController;
import controller.MovableController;
import controller.SkaneController;
import org.junit.Test;
import room.Position;
import room.element.Civilian;
import room.element.Wall;
import room.element.skane.Skane;

import static org.junit.Assert.assertFalse;

public class CollisionControllerTests {
    private MovableController controller;
    // This is probably temporary, to be moved to each controller

    @Test
    public void skaneCollisionHandler() {
        // Wall wall = Mockito.mock(Wall.class);
        // Doesn't work because Mockito doesn't allow
        // override of final static methods
        // Using normal objects instead.
        Wall wall = new Wall(new Position(1, 1));
        Skane skane = new Skane(1, 1, 1, 1, 1, 1);
        controller = new SkaneController(skane, 10);

        assertFalse(controller.handleCollision(skane, wall));
    }

    @Test
    public void enemyControllerHandler() {
        controller = new EnemyController();
        Wall wall = new Wall(new Position(1, 1));
        Civilian civ = new Civilian(1, 1, 1);

        assertFalse(controller.handleCollision(civ, wall));
    }
}
