package room.element.skane;

import room.Position;
import room.element.EntityQueMorde;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class Skane extends EntityQueMorde {
    private Boolean is_bury;
    private int oxygen_level;
    private List<SkaneBody> body;
    private int scent_dur;
    private HashSet<Scent> scent_trail;

    public Skane(Position pos, int atk, int hp, int oxy, int size, int scent) {
        super(pos, hp, atk);
        this.is_bury = false;
        this.oxygen_level = oxy;
        this.scent_dur = scent;
        this.scent_trail = new LinkedHashSet<>();

        this.body = new LinkedList<>();
        for (int i = 0; i < size; ++i)
            this.grow();
    }

    public Skane(int x, int y, int atk, int hp, int oxy, int size, int scent) {
        this(new Position(x, y), atk, hp, oxy, size, scent);
    }

    public Skane(SkaneOpts opts) {
        this(opts.pos,
                opts.attack_dmg,
                opts.hp,
                opts.oxygen_lvl,
                opts.size,
                opts.scent_duration);
    }

    public int getOxygenLevel() {
        return this.oxygen_level;
    }

    public void setOxygenLevel(int oxygen_level) {
        this.oxygen_level = oxygen_level;
    }

    public Boolean isBury() {
        return this.is_bury;
    }

    public void bury(Boolean go_underground) {
        this.is_bury = go_underground;
    }

    public int getSize() {
        return this.body.size();
    }

    public List<SkaneBody> getBody() {
        return this.body;
    }

    public void grow() {
        body.add(0, new SkaneBody(getPos()));
    }

    public void shrink() {
        body.remove(0);
    }

    @Override
    public void setPos(Position new_pos) {
        int body_size = body.size();

        if (body_size > 0) {
            for (int i = 0; i < body_size - 1; ++i)
                body.get(i).setPos(body.get(i + 1).getPos());

            body.get(body_size - 1).setPos(this.getPos());
        }

        super.setPos(new_pos);
    }

    public void dropScent() {
        scent_trail.add(new Scent(getPos(), scent_dur));
    }
}
