package room.element.element_behaviours;

public class AgressiveBehaviour implements Agressive {
    private int atk;

    public AgressiveBehaviour(int atk) {
        this.atk = atk;
    }

    public AgressiveBehaviour() {
        this(0);
    }

    @Override
    public int getAtk() {
        return this.atk;
    }

    @Override
    public void setAtk(int atk) {
        this.atk = atk;
    }
}
