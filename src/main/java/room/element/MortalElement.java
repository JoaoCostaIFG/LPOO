package room.element;

public interface MortalElement {
    Integer getHp();

    void setHp(Integer hp);

    void takeDamage(Integer dmg);

    boolean isAlive();
}
