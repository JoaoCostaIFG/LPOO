public class MovLeft implements MovCommand {
    private MovableElement e;
    private Integer speed;

    public MovLeft(MovableElement e, Integer speed) {
        this.e = e;
        this.speed = speed;
    }

    @Override
    public Position execute() {
        return e.moveLeft(speed);
    }
}
