package room.element;

import room.Position;

import java.util.Vector;

public class Skane extends EntityQueMorde {
    private Vector<SkaneBody> body;
    private Boolean is_bury;
    private Integer max_oxy;
    private Integer oxygen_level;

    public Skane(Integer x, Integer y, Integer atk, Integer hp, Integer oxy) {
        super(x, y, hp, atk);
        this.is_bury = false;
        this.max_oxy = oxy;
        this.oxygen_level = max_oxy;

        this.body = new Vector<>();
        for (int i = 0; i < 3; ++i)
            this.grow();
    }

    public Skane(Position pos, Integer atk, Integer hp, Integer oxy) {
        super(pos, hp, atk);
        this.is_bury = false;
        this.max_oxy = oxy;
        this.oxygen_level = max_oxy;

        this.body = new Vector<>();
    }

    public Boolean isBury() {
        return this.is_bury;
    }

    public void bury(Boolean go_underground) {
        if (go_underground == this.is_bury) // no state change
            return;

        if (go_underground) {
            if (this.oxygen_level.equals(max_oxy))
                this.is_bury = true;
        } else
            this.is_bury = false;
    }

    public void inhale() {
        if (oxygen_level == 0)
            is_bury = false;

        if (is_bury) {
            --oxygen_level;
        } else if (oxygen_level < max_oxy) {
            oxygen_level += max_oxy / 50;
            if (oxygen_level > max_oxy)
                oxygen_level = max_oxy;
        }
    }

    public Vector<SkaneBody> getBody() {
        return this.body;
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
