package room.element.element_behaviours;

public class ImmortalBehaviour implements Mortal {
    @Override
    public int getHp() {
        return 1;
    }

    @Override
    public void setHp(int hp) {
    }

    @Override
    public void takeDamage(int dmg) {
    }

    @Override
    public boolean isAlive() {
        return true;
    }
}
