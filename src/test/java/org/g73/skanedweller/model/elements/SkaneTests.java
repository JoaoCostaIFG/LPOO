package org.g73.skanedweller.model.elements;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.element.skane.Scent;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SkaneTests {
    private final int initx = 50;
    private final int inity = 50;
    private final int scentDur = 2;
    private final int initHp = 5;
    private Skane ska;
    List<SkaneBody> body;

    @BeforeEach
    public void setUp() {
        ska = new Skane(initx, inity, 100, initHp, 5, 2);
        body = ska.getBody();
    }

    @Test
    public void testInitSkane() {
        assertEquals(ska.getPos().getX(), initx);
        assertEquals(ska.getPos().getY(), inity);
    }

    @Test
    public void testInitBody() {
        assertEquals(body.size(), 2);

        Position pos = new Position(initx, inity);
        for (SkaneBody b : body) // all body starts on top of skane
            assertEquals(b.getPos(), pos);
    }

    @Test
    public void testGetTailPos() {
        assertEquals(ska.getTailPos(), new Position(initx, inity));
        ska.shrink();
        assertEquals(ska.getTailPos(), new Position(initx, inity));
        ska.shrink();
        assertNull(ska.getTailPos());
    }

    @Test
    public void testMoveRight() {
        ska.setPos(ska.moveRight());
        assertEquals(ska.getPos(), new Position(initx + 1, inity));
        assertEquals(body.get(1).getPos(), new Position(initx, inity));
        assertEquals(body.get(0).getPos(), new Position(initx, inity));

        ska.setPos(ska.moveRight(10));
        assertEquals(ska.getPos(), new Position(initx + 11, inity));
        assertEquals(body.get(1).getPos(), new Position(initx + 1, inity));
        assertEquals(body.get(0).getPos(), new Position(initx, inity));

        ska.setPos(ska.moveRight(-20));
        assertEquals(ska.getPos(), new Position(initx - 9, inity));
        assertEquals(body.get(1).getPos(), new Position(initx + 11, inity));
        assertEquals(body.get(0).getPos(), new Position(initx + 1, inity));
    }

    @Test
    public void testMoveUp() {
        ska.setPos(ska.moveUp());
        assertEquals(ska.getPos(), new Position(initx, inity - 1));
        assertEquals(body.get(1).getPos(), new Position(initx, inity));
        assertEquals(body.get(0).getPos(), new Position(initx, inity));

        ska.setPos(ska.moveUp(10));
        assertEquals(ska.getPos(), new Position(initx, inity - 11));
        assertEquals(body.get(1).getPos(), new Position(initx, inity - 1));
        assertEquals(body.get(0).getPos(), new Position(initx, inity));

        ska.setPos(ska.moveUp(-20));
        assertEquals(ska.getPos(), new Position(initx, inity + 9));
        assertEquals(body.get(1).getPos(), new Position(initx, inity - 11));
        assertEquals(body.get(0).getPos(), new Position(initx, inity - 1));
    }

    @Test
    public void testMoveLeft() {
        ska.setPos(ska.moveLeft());
        assertEquals(ska.getPos(), new Position(initx - 1, inity));
        assertEquals(body.get(1).getPos(), new Position(initx, inity));
        assertEquals(body.get(0).getPos(), new Position(initx, inity));

        ska.setPos(ska.moveLeft(10));
        assertEquals(ska.getPos(), new Position(initx - 11, inity));
        assertEquals(body.get(1).getPos(), new Position(initx - 1, inity));
        assertEquals(body.get(0).getPos(), new Position(initx, inity));

        ska.setPos(ska.moveLeft(-20));
        assertEquals(ska.getPos(), new Position(initx + 9, inity));
        assertEquals(body.get(1).getPos(), new Position(initx - 11, inity));
        assertEquals(body.get(0).getPos(), new Position(initx - 1, inity));
    }

    @Test
    public void testMoveDown() {
        ska.setPos(ska.moveDown());
        assertEquals(ska.getPos(), new Position(initx, inity + 1));
        assertEquals(body.get(1).getPos(), new Position(initx, inity));
        assertEquals(body.get(0).getPos(), new Position(initx, inity));

        ska.setPos(ska.moveDown(10));
        assertEquals(ska.getPos(), new Position(initx, inity + 11));
        assertEquals(body.get(1).getPos(), new Position(initx, inity + 1));
        assertEquals(body.get(0).getPos(), new Position(initx, inity));

        ska.setPos(ska.moveDown(-20));
        assertEquals(ska.getPos(), new Position(initx, inity - 9));
        assertEquals(body.get(1).getPos(), new Position(initx, inity + 11));
        assertEquals(body.get(0).getPos(), new Position(initx, inity + 1));
    }

    @Test
    public void testMoveWithoutBody() {
        ska.shrink();
        ska.shrink();
        assertTrue(ska.getBody().isEmpty());

        ska.setPos(ska.moveRight());
        assertEquals(ska.getPos(), new Position(initx + 1, inity));
    }

    @Test
    public void testGrowAndShrink() {
        ska.grow();
        assertEquals(ska.getSize(), 3);
        assertEquals(ska.getBody().get(0).getPos(), ska.getPos());

        ska.shrink();
        assertEquals(ska.getSize(), 2);
    }

    @Test
    public void testTakeDamage() {
        assertEquals(ska.getHp(), initHp);
        assertEquals(ska.getBody().size(), 2);

        ska.takeDamage(1);
        assertEquals(ska.getHp(), initHp - 1);
        assertEquals(ska.getBody().size(), 1);

        ska.takeDamage(3);
        assertEquals(ska.getHp(), initHp - 4);
        assertTrue(ska.getBody().isEmpty());

        ska.takeDamage(initHp);
        assertEquals(ska.getHp(), 0);
        assertTrue(ska.getBody().isEmpty());
    }

    /* SKANE BODY TESTS */
    @Test
    public void testSkaneBodyTakeDmg() {
        List<SkaneBody> bodies = ska.getBody();
        assertFalse(bodies.isEmpty());

        assertEquals(ska.getHp(), initHp);
        bodies.get(0).takeDamage(initHp);

        assertEquals(ska.getHp(), 0);
        assertTrue(ska.getBody().isEmpty());
    }

    /* SCENT TESTS */
    @Test
    public void testScentTrail() {
        assertTrue(ska.getScentTrail().isEmpty());

        ska.setPos(ska.moveRight());
        ska.setPos(ska.moveRight());

        // drop scent on tail
        ska.dropScent(scentDur);
        assertEquals(ska.getScentTrail().size(), 1);
        for (Scent scent : ska.getScentTrail())
            assertEquals(scent.getPos(), new Position(initx, inity));

        // getting rid of dropped scent and skane body
        for (int i = 0; i < scentDur; ++i) {
            assertEquals(ska.getScentTrail().size(), 1);
            ska.tickScentTrail();
        }
        assertTrue(ska.getScentTrail().isEmpty());
        ska.shrink();
        ska.shrink();
        assertEquals(ska.getSize(), 0);

        // drop scent on head
        ska.dropScent(scentDur);
        assertEquals(ska.getScentTrail().size(), 1);
        for (Scent scent : ska.getScentTrail())
            assertEquals(scent.getPos(), new Position(initx + 2, inity)); // we moved 2 units right
    }
}
