package Controller;

import Controller.collisionStrategy.*;
import gui.EVENT;
import room.Position;
import room.Room;
import room.element.*;
import room.element.skane.Skane;
import room.element.skane.SkaneBody;

import java.util.HashMap;
import java.util.Map;

public class SkaneController extends MovableController<Skane> implements PlayerController {
    private Skane ska;
    private int max_oxy;
    private int scent_dur;
    private EVENT currEvent;

    protected static final Map<Class<? extends CollidableElement>, CollisionStrategy> colHandlerMap =
        new HashMap<Class<? extends CollidableElement>, CollisionStrategy>() {{
        put(Skane.class, new NullCollision());
        put(SkaneBody.class, new NullCollision());
        put(Wall.class, new BlockCollision());
        put(MeleeGuy.class, new AttackCollisionStrat());
        put(Civilian.class, new AttackCollisionStrat());
    }};

    public SkaneController(Skane ska, int max_oxygen) {
        super(colHandlerMap);
        this.ska = ska;
        this.max_oxy = max_oxygen;
        this.scent_dur = 3; // TODO
        this.currEvent = EVENT.NullEvent;
    }

    private void takeDamage(int dmg) {
        if (dmg < 0) return;
        ska.takeDamage(dmg);
        for (int i = 0; i < dmg; ++i)
            ska.shrink();
    }

    private void nom(int nourishment) {
        if (nourishment < 0) return;
        ska.setHp(ska.getHp() + nourishment);
        for (int i = 0; i < nourishment; ++i)
            ska.grow();
    }

    private void toggleBury() {
        if (ska.isBury())
            ska.bury(false);
        else if (ska.getOxygenLevel() == max_oxy)
            ska.bury(true);
    }

    private void inhale() {
        int oxy_lvl = ska.getOxygenLevel();
        if (oxy_lvl == 0)
            ska.bury(false);

        if (ska.isBury()) {
            ska.setOxygenLevel(oxy_lvl - 1);
        } else if (oxy_lvl < max_oxy) {
            if (oxy_lvl + max_oxy / 50 > max_oxy)
                ska.setOxygenLevel(max_oxy);
            else
                ska.setOxygenLevel(oxy_lvl + max_oxy / 50);
        }
    }

    @Override
    public void setEvent(EVENT event) {
        currEvent = event;
    }

    @Override
    public void update(Room room) {
        this.inhale();
        if (currEvent != EVENT.NullEvent) {
            if (currEvent == EVENT.Bury) toggleBury();
            else { // Movement Event
                Position newPos = ska.getPos();
                switch (currEvent) {
                    case MoveLeft:
                        newPos = ska.moveLeft();
                        break;
                    case MoveRight:
                        newPos = ska.moveRight();
                        break;
                    case MoveUp:
                        newPos = ska.moveUp();
                        break;
                    case MoveDown:
                        newPos = ska.moveDown();
                        break;
                }
                if (canMove(newPos, ska, room))
                    move(ska, newPos);
            }
        }
        currEvent = EVENT.NullEvent;
    }

    @Override
    public void move(Skane ska, Position pos) {
        ska.setPos(pos);
    }
}
