import org.junit.Test;
import model.Position;
import model.colliders.CompositeCollider;
import model.colliders.RectangleCollider;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ColliderTests {
    @Test
    public void rectangleCollider() {
        RectangleCollider r1 = new RectangleCollider(10, 10, 10, 10);
        RectangleCollider r2 = new RectangleCollider(11, 11, 1, 1);
        assertTrue(r1.collidesWith(r1));
        assertTrue(r1.collidesWith(r2));
        assertTrue(r2.collidesWith(r1));

        r1.setHeight(1);
        r1.setWidth(1);
        assertFalse(r1.collidesWith(r2));
        assertFalse(r2.collidesWith(r1));

        r1.setPos(new Position(11, 10));
        r1.setHeight(2);
        assertTrue(r1.collidesWith(r2));
        assertTrue(r2.collidesWith(r1));

        r1.setPos(new Position(10, 11));
        r1.setWidth(2);
        r1.setHeight(1);
        assertTrue(r1.collidesWith(r2));
        assertTrue(r2.collidesWith(r1));
    }

    @Test
    public void composite() {
        CompositeCollider c1 = new CompositeCollider(1, 1);
        RectangleCollider r1 = new RectangleCollider(1, 1, 1, 1);
        RectangleCollider r2 = new RectangleCollider(2, 1, 1, 1);
        RectangleCollider r3 = new RectangleCollider(10, 2, 1, 1);
        RectangleCollider r4 = new RectangleCollider(11, 2, 1, 1);
        c1.addCollider(r1);
        c1.addCollider(r2);
        c1.addCollider(r3);
        c1.addCollider(r4);

        RectangleCollider rect = new RectangleCollider(9, 2, 5, 1);
        assertTrue(rect.collidesWith(c1));
        assertTrue(c1.collidesWith(rect));

        rect.setPos(new Position(3, 1));
        assertFalse(rect.collidesWith(c1));
        assertFalse(c1.collidesWith(rect));
    }
}
