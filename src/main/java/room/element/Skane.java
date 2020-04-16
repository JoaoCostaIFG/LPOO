package room.element;

import room.Position;

import java.util.Vector;

public class Skane extends EntityQueMorde {
    private Vector<SkaneBody> body;
    private Boolean is_bury;
    private Integer oxygen_level;

    public Skane(Integer x, Integer y, Integer atk, Integer hp, Integer oxy) {
        super(x, y, hp, atk);
        this.is_bury = false;
        this.oxygen_level = oxy;

        this.body = new Vector<>();
        for (int i = 0; i < 3; ++i)
            this.grow();
    }

    public Skane(Position pos, Integer atk, Integer hp, Integer oxy) {
        super(pos, hp, atk);
        this.is_bury = false;
        this.oxygen_level = oxy;

        this.body = new Vector<>();
        for (int i = 0; i < 3; ++i)
            this.grow();
    }

    public Integer getOxygenLevel() {
        return this.oxygen_level;
    }

    public void setOxygenLevel(Integer oxygen_level) {
        this.oxygen_level = oxygen_level;
    }

    public Boolean isBury() {
        return this.is_bury;
    }

    public void bury(Boolean go_underground) {
        this.is_bury = go_underground;
    }

    public Vector<SkaneBody> getBody() {
        return this.body;
    }

    public Integer getSize() {
        return this.body.size();
    }

    public void grow() {
        body.insertElementAt(new SkaneBody(getPos()), 0);
    }

    public void shrink() {
        body.removeElementAt(0);
    }

    @Override
    public void setPos(Position new_pos) {
        for (int i = 0; i < body.size() - 1; ++i)
            body.elementAt(i).setPos(body.elementAt(i + 1).getPos());
        if (body.size() > 0)
            body.lastElement().setPos(this.getPos());

        super.setPos(new_pos);
    }
}
