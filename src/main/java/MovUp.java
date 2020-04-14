import GameElement.MovableElement;
import GameElement.Position;

public class MovUp extends MovCommand {

    public MovUp(MovableElement e, Integer speed) {
        super(e, speed);
    }

    @Override
    public Position execute() {
        return e.moveUp(speed);
    }
}
