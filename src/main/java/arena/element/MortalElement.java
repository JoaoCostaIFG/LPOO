package arena.element;

public interface MortalElement {
    Integer DFLT_HP = 3;

    Integer getHp();

    void setHp(Integer hp);

    Boolean damage(Integer dmg);

    boolean isAlive();
}
