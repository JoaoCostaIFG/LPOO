package room.element.skane;

import observe.Observable;
import observe.Observer;
import room.Position;
import room.colliders.CompositeCollider;
import room.colliders.RectangleCollider;
import room.element.EntityQueMorde;

import java.util.*;

public class Skane extends EntityQueMorde {
    private Boolean is_bury;
    private int oxygen_level;
    private List<SkaneBody> body;
    private int scent_dur;
    private LinkedHashSet<Scent> scent_trail;

    public Skane(Position pos, int atk, int hp, int oxy, int size, int scent_dur) {
        super(pos, hp, atk, new CompositeCollider());
        this.is_bury = false;
        this.oxygen_level = oxy;
        this.scent_dur = scent_dur;
        this.scent_trail = new LinkedHashSet<>();

        RectangleCollider ska_head_col = new RectangleCollider(pos, 1, 1);
        this.getCollider().addCollider(ska_head_col);

        this.body = new ArrayList<>();
        for (int i = 0; i < size; ++i)
            this.grow();
    }

    public Skane(int x, int y, int atk, int hp, int oxy, int size, int scent_dur) {
        this(new Position(x, y), atk, hp, oxy, size,scent_dur);
    }

    public Skane(SkaneOpts opts) {
        this(opts.pos,
                opts.attack_dmg,
                opts.hp,
                opts.oxygen_lvl,
                opts.size,
                opts.scent_dur
        );
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
        SkaneBody n_body = new SkaneBody(getPos());
        body.add(0, n_body);
        this.getCollider().addCollider(n_body.getCollider());
    }

    public void shrink() {
        SkaneBody s_body = body.get(0);
        body.remove(s_body);
        this.getCollider().removeCollider(s_body.getCollider());
    }

    public Position getTailPos() {
        return body.get(body.size() - 1).getPos();
    }

    public LinkedHashSet<Scent> getScentTrail() {
        return this.scent_trail;
    }

    public void dropScent(){
        scent_trail.add(new Scent(getTailPos(), scent_dur));
    }

    public void tickScentTrail() {
        for (Scent s : scent_trail)
            s.tick();

        dropScent();
        scent_trail.removeIf(s -> s.getDuration() == 0);
    }

    @Override
    public void setPos(Position new_pos) {
        tickScentTrail();

        int body_size = body.size();
        if (body_size > 0) {
            for (int i = 0; i < body_size - 1; ++i)
                body.get(i).setPos(body.get(i + 1).getPos());

            body.get(body_size - 1).setPos(this.getPos());
        }

        super.setPos(new_pos);
    }

    @Override
    public CompositeCollider getCollider() {
        return (CompositeCollider) super.getCollider();
    }
}
