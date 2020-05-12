package org.g73.skanedweller.model.element.skane;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.Collider;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.*;

public class SkaneBody extends Element {
    public SkaneBody(Position pos) {
        super(pos,
                new PassiveBehaviour(),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new ImmortalBehaviour(),
                new ImovableBehaviour());
    }

    public SkaneBody(int x, int y) {
        this(new Position(x, y));
    }
}
