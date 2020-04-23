package room.element.skane;

import room.Position;
import room.colliders.RectangleCollider;
import room.element.Entity;
import room.element.EntityQueMorde;

import java.util.*;

public class Skane extends EntityQueMorde {
    private Boolean is_bury;
    private int oxygen_level;
    private List<SkaneBody> body;
    private int scent_dur;
    private LinkedHashSet<Scent> scent_trail;

    public Skane(Position pos, int atk, int hp, int oxy, int size, int scent_dur) {
        super(pos, hp, atk, new RectangleCollider(pos, 1, 1));
        this.is_bury = false;
        this.oxygen_level = oxy;
        this.scent_dur = scent_dur;
        this.scent_trail = new LinkedHashSet<>();
        this.addObserver(this.getCollider());

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

        super.notifyObservers(new_pos);
        super.setPos(new_pos);
    }

    @Override
    public Entity clone() throws CloneNotSupportedException {
        //FIXME
        Skane ska = new Skane(getPos(), getAtk(), getHp(), oxygen_level, 0, scent_dur);
        ska.body = new LinkedList<>();
//        System.out.println(ska.getCollider().getX() + " " + ska.getCollider().getY() + " ");
        for (SkaneBody b: this.body) {
            ska.body.add(new SkaneBody(b.getPos()));
//            System.out.println(b.getCollider().getX() + " " + ska.getCollider().getY());
        }
//        System.out.println("\n");
        return ska;
    }
}
