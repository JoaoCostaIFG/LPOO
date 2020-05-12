package org.g73.skanedweller.model.element;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.element_behaviours.*;

public class Civilian extends Element {
    public Civilian(Position pos, int hp) {
        super(pos,
                new PassiveBehaviour(),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new MortalBehaviour(hp),
                new MovableBehaviour());
    }

    public Civilian(int x, int y, int hp) {
        this(new Position(x, y), hp);
    }
}
