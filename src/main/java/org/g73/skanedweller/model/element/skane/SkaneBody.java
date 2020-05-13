package org.g73.skanedweller.model.element.skane;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.element_behaviours.CollidableBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.ImmortalBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.ImovableBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.PassiveBehaviour;

public class SkaneBody extends Element {
    private Skane ska;

    public SkaneBody(Position pos, Skane ska) {
        super(pos,
                new PassiveBehaviour(),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new ImmortalBehaviour(),
                new ImovableBehaviour());

        this.ska = ska;
    }

    public SkaneBody(int x, int y, Skane ska) {
        this(new Position(x, y), ska);
    }

    @Override
    public void takeDamage(int dmg) {
        ska.takeDamage(dmg);
    }
}
