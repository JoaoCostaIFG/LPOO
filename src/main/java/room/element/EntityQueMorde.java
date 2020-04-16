package room.element;

import room.Position;

public abstract class EntityQueMorde extends Entity implements AgressiveElement {
    private Integer atk;

    public EntityQueMorde(Integer x, Integer y, Integer hp, Integer atk) {
        super(x, y, hp);
        this.atk = atk;
    }

    public EntityQueMorde(Position pos, Integer hp, Integer atk) {
        super(pos, hp);
        this.atk = atk;
    }

    /* attack */
    public Integer getAtk() {
        return atk;
    }

    public void setAtk(Integer atk) {
        this.atk = atk;
    }
}
