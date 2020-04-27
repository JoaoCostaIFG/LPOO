package room.element;

import room.Position;
import room.colliders.RectangleCollider;
import room.element.element_behaviours.AgressiveBehaviour;
import room.element.element_behaviours.CollidableBehaviour;
import room.element.element_behaviours.MortalBehaviour;
import room.element.element_behaviours.MovableBehaviour;

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
