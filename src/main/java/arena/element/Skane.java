package arena.element;

import arena.Position;

public class Skane extends Entity implements MovableElement {
    private Integer oxygen_level;
    private Boolean is_bury;
    private Integer max_oxy;

    public Skane(Integer x, Integer y, Integer hp, Integer oxy) {
        super(x, y, hp);
        this.is_bury = false;
        this.oxygen_level = oxy;
        this.max_oxy = oxy;
    }

    @Override
    public void damage(Integer dmg) {
        if (getHp() - dmg <= 0)
            setHp(0);
        else
            setHp(getHp() - dmg);
    }

    @Override
    public boolean isAlive() {
        return getHp() > 0;
    }

    public Boolean isBury() {
        return this.is_bury;
    }

    public void bury(Boolean go_underground) {
        if (go_underground == this.is_bury) // no state change
            return;

        if (go_underground) {
            if (this.oxygen_level.equals(max_oxy))
                this.is_bury = true;
        } else
            this.is_bury = false;
    }

    public void breath() {
        if (oxygen_level == 0)
            is_bury = false;

        if (is_bury) {
            --oxygen_level;
        } else if (oxygen_level < max_oxy) {
            oxygen_level += max_oxy / 50;
            if (oxygen_level > max_oxy)
                oxygen_level = max_oxy;
        }
    }

    /* movement */
    @Override
    public Position moveUp() {
        return new Position(getX(), getY() - 1);
    }

    @Override
    public Position moveUp(int y) {
        return new Position(getX(), getY() - y);
    }

    @Override
    public Position moveDown() {
        return new Position(getX(), getY() + 1);
    }

    @Override
    public Position moveDown(int y) {
        return new Position(getX(), getY() + y);
    }

    @Override
    public Position moveLeft() {
        return new Position(getX() - 1, getY());
    }

    @Override
    public Position moveLeft(int x) {
        return new Position(getX() - x, getY());
    }

    @Override
    public Position moveRight() {
        return new Position(getX() + 1, getY());
    }

    @Override
    public Position moveRight(int x) {
        return new Position(getX() + x, getY());
    }
}
