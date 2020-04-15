package arena.element;

import arena.Position;

public class Skane extends Element implements MortalElement, MovableElement {
    private Integer hp, oxygen_level;
    private Boolean is_bury;
    private final Integer DFLT_OXY = 200;

    public Skane(int x, int y) {
        super(x, y);
        this.hp = DFLT_HP;
        this.is_bury = false;
        this.oxygen_level = DFLT_OXY;
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
        this.hp -= dmg;
        if (this.hp <= 0) // is dead
            return false;

        return true;
    }

    public boolean isAlive() {
        return (this.hp > 0);
    }

    public Boolean isBury() {
        return this.is_bury;
    }

    public void bury(Boolean go_underground) {
        if (go_underground == this.is_bury) // no state change
            return;

        if (go_underground) {
            if (this.oxygen_level.equals(DFLT_OXY))
                this.is_bury = true;
        } else
            this.is_bury = false;
    }

    public void breath() {
        if (oxygen_level == 0)
            is_bury = false;

        if (is_bury) {
            --oxygen_level;
        } else if (oxygen_level < DFLT_OXY) {
            oxygen_level += DFLT_OXY / 50;
            if (oxygen_level > DFLT_OXY)
                oxygen_level = DFLT_OXY;
        }
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
