package room.element;

import room.Position;
import room.colliders.RectangleCollider;
import room.element.element_behaviours.AgressiveBehaviour;
import room.element.element_behaviours.CollidableBehaviour;
import room.element.element_behaviours.ImmortalBehaviour;
import room.element.element_behaviours.ImovableBehaviour;

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
