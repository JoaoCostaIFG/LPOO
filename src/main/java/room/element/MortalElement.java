package room.element;

public interface MortalElement {
    int getHp();

    void setHp(int hp);

    void takeDamage(int dmg);

    boolean isAlive();
}
