import GameElement.MovableElement;
import GameElement.Position;

public class MovRight extends MovCommand {
    public MovRight(MovableElement e, Integer speed) {
        super(e, speed);
    }

    @Override
    public Position execute() {
        return e.moveRight(speed);
    }
}
