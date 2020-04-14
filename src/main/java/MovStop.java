public class MovStop implements MovCommand {
    private MovableElement e;

    public MovStop(MovableElement e) {
        this.e = e;
    }

    @Override
    public Position execute() {
        return null;
    }
}
