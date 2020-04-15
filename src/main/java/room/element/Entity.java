package room.element;

import room.Position;

public abstract class Entity extends Element implements MortalElement, MovableElement {
    private Integer hp;

    public Entity(Integer x, Integer y, Integer hp) {
        super(x, y);
        this.hp = hp;
    }

    public Entity(Position pos, Integer hp) {
        super(pos);
        this.hp = hp;
    }

    /* health */
    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public void takeDamage(Integer dmg) {
        if (hp - dmg <= 0)
            setHp(0);
        else
            setHp(hp - dmg);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    /* movement */
    public Position moveUp() {
        return new Position(getX(), getY() - 1);
    }

    public Position moveUp(int y) {
        return new Position(getX(), getY() - y);
    }

    public Position moveDown() {
        return new Position(getX(), getY() + 1);
    }

    public Position moveDown(int y) {
        return new Position(getX(), getY() + y);
    }

    public Position moveLeft() {
        return new Position(getX() - 1, getY());
    }

    public Position moveLeft(int x) {
        return new Position(getX() - x, getY());
    }

    public Position moveRight() {
        return new Position(getX() + 1, getY());
    }

    public Position moveRight(int x) {
        return new Position(getX() + x, getY());
    }
}
