package arena.element;

public abstract class Entity extends Element implements MortalElement {
    private Integer hp;

    public Entity(int x, int y, int hp) {
        super(x, y);
        this.hp = hp;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public abstract void damage(Integer dmg);

    public abstract boolean isAlive();
}
