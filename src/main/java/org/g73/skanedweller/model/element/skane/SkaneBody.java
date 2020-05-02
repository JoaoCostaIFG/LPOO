package org.g73.skanedweller.model.element.skane;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.Collider;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.AgressiveBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.CollidableBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.ImmortalBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.ImovableBehaviour;

public class SkaneBody extends Element {
    private Collider collider;

    public SkaneBody(Position pos) {
        super(pos,
                new AgressiveBehaviour(),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new ImmortalBehaviour(),
                new ImovableBehaviour());
    }

    public SkaneBody(int x, int y) {
        this(new Position(x, y));
    }
}
