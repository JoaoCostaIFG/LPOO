package model.element;

import model.Position;
import model.colliders.RectangleCollider;
import model.element.element_behaviours.AgressiveBehaviour;
import model.element.element_behaviours.CollidableBehaviour;
import model.element.element_behaviours.MortalBehaviour;
import model.element.element_behaviours.MovableBehaviour;

public class MeleeGuy extends Element {
    public MeleeGuy(Position pos, int hp, int atk) {
        super(pos,
                new AgressiveBehaviour(atk),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new MortalBehaviour(hp),
                new MovableBehaviour());
    }

    public MeleeGuy(int x, int y, int hp, int atk) {
        this(new Position(x, y), hp, atk);
    }
}
