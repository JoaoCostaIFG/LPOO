package model.element.skane;

import model.Position;
import model.colliders.Collider;
import model.colliders.RectangleCollider;
import model.element.*;
import model.element.element_behaviours.AgressiveBehaviour;
import model.element.element_behaviours.CollidableBehaviour;
import model.element.element_behaviours.ImmortalBehaviour;
import model.element.element_behaviours.ImovableBehaviour;

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
