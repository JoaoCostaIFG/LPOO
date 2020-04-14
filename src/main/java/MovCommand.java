import GameElement.MovableElement;
import GameElement.Position;

public abstract class MovCommand {
    protected MovableElement e;
    protected Integer speed;

    public MovCommand(MovableElement e, Integer speed) {
        this.e = e;
        this.speed = speed;
    }

    public abstract Position execute();
}
