package model.element.element_behaviours;

public class MortalBehaviour implements Mortal {
    private int hp;

    public MortalBehaviour(int hp) {
        this.hp = hp;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void takeDamage(int dmg) {
        setHp(Math.max(hp - dmg, 0));
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }
}
