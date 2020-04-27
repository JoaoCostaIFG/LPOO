package room.element.skane;

import room.Position;
import room.colliders.Collider;
import room.colliders.RectangleCollider;
import room.element.*;
import room.element.element_behaviours.AgressiveBehaviour;
import room.element.element_behaviours.CollidableBehaviour;
import room.element.element_behaviours.ImmortalBehaviour;
import room.element.element_behaviours.ImovableBehaviour;

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
