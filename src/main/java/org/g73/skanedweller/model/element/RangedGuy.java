package org.g73.skanedweller.model.element;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.element_behaviours.*;

public class RangedGuy extends Element {
    public RangedGuy(Position pos, int hp, int atk, int range) {
        super(pos,
                new AgressiveBehaviour(atk, range),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new MortalBehaviour(hp),
                new MovableBehaviour());
    }

    public RangedGuy(int x, int y, int hp, int atk, int range) {
        this(new Position(x, y), hp, atk, range);
    }
}
