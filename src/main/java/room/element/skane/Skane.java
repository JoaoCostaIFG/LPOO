package room.element.skane;

import room.Position;
import room.element.Entity;
import room.element.EntityQueMorde;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Skane extends EntityQueMorde {
    public static class SkaneOpts {
        public int attack_dmg, hp, oxygen_lvl, size;
        public Position pos = null;
    }

    private Boolean isBury;
    private int oxyLvl, maxOxy;
    private List<SkaneBody> body;
    private LinkedHashSet<Scent> scentTrail;

    public Skane(Position pos, int atk, int hp, int oxy, int size) {
        super(pos, hp, atk);
        this.isBury = false;
        this.oxyLvl = oxy;
        this.maxOxy = this.oxyLvl;
        this.scentTrail = new LinkedHashSet<>();

        this.body = new ArrayList<>();
        for (int i = 0; i < size; ++i)
            this.grow();
    }

    public Skane(int x, int y, int atk, int hp, int oxy, int size) {
        this(new Position(x, y), atk, hp, oxy, size);
    }

    public Skane(SkaneOpts opts) {
        this(opts.pos,
                opts.attack_dmg,
                opts.hp,
                opts.oxygen_lvl,
                opts.size
        );
    }

    public int getMaxOxygenLevel () {
        return this.maxOxy;
    }

    public int getOxygenLevel() {
        return this.oxyLvl;
    }

    public void setOxygenLevel(int oxyLvl) {
        this.oxyLvl = oxyLvl;
    }

    public Boolean isBury() {
        return this.isBury;
    }

    public void bury(Boolean go_underground) {
        this.isBury = go_underground;
    }

    public int getSize() {
        return this.body.size();
    }

    public List<SkaneBody> getBody() {
        return this.body;
    }

    public void grow() {
        body.add(0, new SkaneBody(this.getPos()));
    }

    public void shrink() {
        SkaneBody s_body = body.get(0);
        body.remove(s_body);
    }

    public Position getTailPos() {
        return body.get(body.size() - 1).getPos();
    }

    public LinkedHashSet<Scent> getScentTrail() {
        return this.scentTrail;
    }

    public void dropScent(int scentDur) {
        scentTrail.add(new Scent(getTailPos(), scentDur));
    }

    public void tickScentTrail() {
        for (Scent s : scentTrail)
            s.tick();

        scentTrail.removeIf(s -> s.getDuration() == 0);
    }

    @Override
    public void setPos(Position new_pos) {
        int body_size = body.size();
        if (body_size > 0) {
            for (int i = 0; i < body_size - 1; ++i)
                body.get(i).setPos(body.get(i + 1).getPos());

            body.get(body_size - 1).setPos(this.getPos());
        }

        super.notifyObservers(new_pos);
        super.setPos(new_pos);
    }
}
