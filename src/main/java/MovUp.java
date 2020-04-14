public class MovUp implements MovCommand {
    private MovableElement e;
    private Integer speed;

    public MovUp(MovableElement e, Integer speed) {
        this.e = e;
        this.speed = speed;
    }

    @Override
    public Position execute() {
        return e.moveUp(speed);
    }
}
