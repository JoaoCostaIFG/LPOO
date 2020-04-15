package arena.element;

public interface MortalElement {
    Integer getHp();

    void setHp(Integer hp);

    void damage(Integer dmg);

    boolean isAlive();
}
