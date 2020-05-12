package org.g73.skanedweller.model.element;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.element_behaviours.*;

public class Wall extends Element {
    public Wall(Position pos) {
        super(pos,
                new PassiveBehaviour(),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new ImmortalBehaviour(),
                new ImovableBehaviour());
    }

    public Wall(int x, int y) {
        this(new Position(x, y));
    }
}
