import GameElement.MovableElement;
import GameElement.Position;

public class MovStop extends MovCommand {
    public MovStop(MovableElement e) {
        // moving 0 is = not moving
        super(e, 0);
    }

    @Override
    public Position execute() {
        return e.moveRight(super.speed);
    }
}
