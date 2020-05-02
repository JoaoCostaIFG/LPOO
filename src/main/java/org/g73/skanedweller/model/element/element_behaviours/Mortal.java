package org.g73.skanedweller.model.element.element_behaviours;

public interface Mortal {
    int getHp();

    void setHp(int hp);

    void takeDamage(int dmg);

    boolean isAlive();
}
