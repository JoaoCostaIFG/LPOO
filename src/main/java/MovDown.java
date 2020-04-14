import GameElement.MovableElement;
import GameElement.Position;

public class MovDown extends MovCommand {
    public MovDown(MovableElement e, Integer speed) {
        super(e, speed);
    }

    @Override
    public Position execute() {
        return e.moveDown(speed);
    }
}
