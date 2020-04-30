package model.element;

import model.Position;
import model.colliders.RectangleCollider;
import model.element.element_behaviours.AgressiveBehaviour;
import model.element.element_behaviours.CollidableBehaviour;
import model.element.element_behaviours.ImmortalBehaviour;
import model.element.element_behaviours.ImovableBehaviour;

public class Wall extends Element {
    public Wall(Position pos) {
        super(pos,
                new AgressiveBehaviour(),
                new CollidableBehaviour(new RectangleCollider(pos, 1, 1)),
                new ImmortalBehaviour(),
                new ImovableBehaviour());
    }

    public Wall(int x, int y) {
        this(new Position(x, y));
    }
}
