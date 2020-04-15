package arena.element;

public class Skane extends Element implements MortalElement, MovableElement{
    private Integer hp;

    public Skane(int x, int y) {
        super(x, y);
        this.hp = DFLT_HP;
    }

    public Integer getHp() {
        return hp;
    }

    @Override
    public void setHp(Integer hp) {
        this.hp = hp;
    }

    @Override
    public Boolean damage(Integer dmg) {
        return null;
    }

    public boolean isAlive() {
        return (this.hp > 0);
    }

    /* movement */
    @Override
    public Position moveUp() {
        return new Position(super.getX(), super.getY() - 1);
    }

    @Override
    public Position moveUp(int y) {
        return new Position(super.getX(), super.getY() - y);
    }

    @Override
    public Position moveDown() {
        return new Position(super.getX(), super.getY() + 1);
    }

    @Override
    public Position moveDown(int y) {
        return new Position(super.getX(), super.getY() + y);
    }

    @Override
    public Position moveLeft() {
        return new Position(super.getX() - 1, super.getY());
    }

    @Override
    public Position moveLeft(int x) {
        return new Position(super.getX() - x, super.getY());
    }

    @Override
    public Position moveRight() {
        return new Position(super.getX() + 1, super.getY());
    }

    @Override
    public Position moveRight(int x) {
        return new Position(super.getX() + x, super.getY());
    }
}
