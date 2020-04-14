public class MovRight implements MovCommand {
    private MovableElement e;
    private Integer speed;

    public MovRight(MovableElement e, Integer speed) {
        this.e = e;
        this.speed = speed;
    }

    @Override
    public Position execute() {
        return e.moveRight(speed);
    }
}
