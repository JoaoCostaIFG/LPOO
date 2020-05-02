package org.g73.skanedweller.model.element;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.element_behaviours.AgressiveBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.CollidableBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.MortalBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.MovableBehaviour;

public class Civilian extends Element {
    public Civilian(Position pos, int hp) {
        super(pos,
                new AgressiveBehaviour(),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new MortalBehaviour(hp),
                new MovableBehaviour());
    }

    public Civilian(int x, int y, int hp) {
        this(new Position(x, y), hp);
    }
}
