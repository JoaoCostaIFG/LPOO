import GameElement.MovCommand;
import GameElement.MovableElement;
import GameElement.Position;

public class MovDown implements MovCommand {
    private MovableElement e;
    private Integer speed;

    public MovDown(MovableElement e, Integer speed) {
        this.e = e;
        this.speed = speed;
    }

    @Override
    public Position execute() {
        return e.moveDown(speed);
    }
}
