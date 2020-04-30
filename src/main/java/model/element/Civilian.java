package model.element;

import model.Position;
import model.colliders.RectangleCollider;
import model.element.element_behaviours.AgressiveBehaviour;
import model.element.element_behaviours.CollidableBehaviour;
import model.element.element_behaviours.MortalBehaviour;
import model.element.element_behaviours.MovableBehaviour;

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
