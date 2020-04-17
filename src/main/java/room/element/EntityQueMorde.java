package room.element;

import room.Position;

public abstract class EntityQueMorde extends Entity implements AgressiveElement {
    private int atk;

    public EntityQueMorde(Position pos, int hp, int atk) {
        super(pos, hp);
        this.atk = atk;
    }

    public EntityQueMorde(int x, int y, int hp, int atk) {
        this(new Position(x, y), hp, atk);
    }

    /* attack */
    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }
}
