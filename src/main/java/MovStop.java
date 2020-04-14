import GameElement.MovCommand;
import GameElement.MovableElement;
import GameElement.Position;

public class MovStop implements MovCommand {
    private MovableElement e;

    public MovStop(MovableElement e) {
        this.e = e;
    }

    @Override
    public Position execute() {
        // moving 0 up is = not moving
        return e.moveRight(0);
    }
}
