package org.g73.skanedweller.model.element;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.colliders.RectangleCollider;
import org.g73.skanedweller.model.element.element_behaviours.AgressiveBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.CollidableBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.MortalBehaviour;
import org.g73.skanedweller.model.element.element_behaviours.MovableBehaviour;

public class MeleeGuy extends Element {
    public MeleeGuy(Position pos, int hp, int atk, int range) {
        super(pos,
                new AgressiveBehaviour(atk, range),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new MortalBehaviour(hp),
                new MovableBehaviour());
    }

    public MeleeGuy(int x, int y, int hp, int atk, int range) {
        this(new Position(x, y), hp, atk, range);
    }
}
