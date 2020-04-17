import org.junit.Before;
import org.junit.Test;
import room.Position;
import room.element.Skane;
import room.element.SkaneBody;

import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class SkaneTests {
    private final int initx = 50;
    private final int inity = 50;
    private Skane ska;
    Vector<SkaneBody> body;

    @Before
    public void setUp() {
        ska = new Skane(initx, inity, 100, 5, 5, 2);
        body = ska.getBody();
    }

    @Test
    public void initSkane() {
        assertEquals(ska.getPos().getX(), initx);
        assertEquals(ska.getPos().getY(), inity);
    }

    @Test
    public void initBody() {
        assertEquals(body.size(), 2);

        Position pos = new Position(initx, inity);
        for (SkaneBody b : body) // all body starts on top of skane
            assertEquals(b.getPos(), pos);
    }

    @Test
    public void moveRight() {
        Position curr_pos = ska.getPos();
        int x = curr_pos.getX();
        int y = curr_pos.getY();

        ska.setPos(ska.moveRight());
        assertEquals(ska.getPos(), new Position(x + 1, y));
        assertEquals(body.get(1).getPos(), new Position(x, y));
        assertEquals(body.get(0).getPos(), new Position(x, y));

        ska.setPos(ska.moveRight(10));
        assertEquals(ska.getPos(), new Position(x + 11, y));
        assertEquals(body.get(1).getPos(), new Position(x + 1, y));
        assertEquals(body.get(0).getPos(), new Position(x, y));

        ska.setPos(ska.moveRight(-20));
        assertEquals(ska.getPos(), new Position(x - 9, y));
        assertEquals(body.get(1).getPos(), new Position(x + 11, y));
        assertEquals(body.get(0).getPos(), new Position(x + 1, y));
    }

    @Test
    public void moveUp() {
        Position curr_pos = ska.getPos();
        int x = curr_pos.getX();
        int y = curr_pos.getY();

        ska.setPos(ska.moveUp());
        assertEquals(ska.getPos(), new Position(x, y - 1));
        assertEquals(body.get(1).getPos(), new Position(x, y));
        assertEquals(body.get(0).getPos(), new Position(x, y));

        ska.setPos(ska.moveUp(10));
        assertEquals(ska.getPos(), new Position(x, y - 11));
        assertEquals(body.get(1).getPos(), new Position(x, y - 1));
        assertEquals(body.get(0).getPos(), new Position(x, y));

        ska.setPos(ska.moveUp(-20));
        assertEquals(ska.getPos(), new Position(x, y + 9));
        assertEquals(body.get(1).getPos(), new Position(x, y - 11));
        assertEquals(body.get(0).getPos(), new Position(x, y - 1));
    }

    @Test
    public void moveLeft() {
        Position curr_pos = ska.getPos();
        int x = curr_pos.getX();
        int y = curr_pos.getY();

        ska.setPos(ska.moveLeft());
        assertEquals(ska.getPos(), new Position(x - 1, y));
        assertEquals(body.get(1).getPos(), new Position(x, y));
        assertEquals(body.get(0).getPos(), new Position(x, y));

        ska.setPos(ska.moveLeft(10));
        assertEquals(ska.getPos(), new Position(x - 11, y));
        assertEquals(body.get(1).getPos(), new Position(x - 1, y));
        assertEquals(body.get(0).getPos(), new Position(x, y));

        ska.setPos(ska.moveLeft(-20));
        assertEquals(ska.getPos(), new Position(x + 9, y));
        assertEquals(body.get(1).getPos(), new Position(x - 11, y));
        assertEquals(body.get(0).getPos(), new Position(x - 1, y));
    }

    @Test
    public void moveDown() {
        Position curr_pos = ska.getPos();
        int x = curr_pos.getX();
        int y = curr_pos.getY();

        ska.setPos(ska.moveDown());
        assertEquals(ska.getPos(), new Position(x, y + 1));
        assertEquals(body.get(1).getPos(), new Position(x, y));
        assertEquals(body.get(0).getPos(), new Position(x, y));

        ska.setPos(ska.moveDown(10));
        assertEquals(ska.getPos(), new Position(x, y + 11));
        assertEquals(body.get(1).getPos(), new Position(x, y + 1));
        assertEquals(body.get(0).getPos(), new Position(x, y));

        ska.setPos(ska.moveDown(-20));
        assertEquals(ska.getPos(), new Position(x, y - 9));
        assertEquals(body.get(1).getPos(), new Position(x, y + 11));
        assertEquals(body.get(0).getPos(), new Position(x, y + 1));
    }
}
