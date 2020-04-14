import GameElement.MovableElement;
import GameElement.Position;

public class MovLeft extends MovCommand {
    public MovLeft(MovableElement e, Integer speed) {
        super(e, speed);
    }

    @Override
    public Position execute() {
        return e.moveLeft(speed);
    }
}
