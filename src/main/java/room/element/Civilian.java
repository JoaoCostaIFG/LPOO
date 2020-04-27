package room.element;

import room.Position;
import room.colliders.RectangleCollider;
import room.element.element_behaviours.AgressiveBehaviour;
import room.element.element_behaviours.CollidableBehaviour;
import room.element.element_behaviours.MortalBehaviour;
import room.element.element_behaviours.MovableBehaviour;

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
