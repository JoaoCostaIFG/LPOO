package controller;

import controller.collisionStrategy.AttackCollisionStrat;
import controller.collisionStrategy.BlockCollision;
import controller.collisionStrategy.CollisionStrategy;
import controller.collisionStrategy.NullCollision;
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
    private int maxOxy;
    private int scentDur;
    private EVENT currEvent;

    protected static final Map<Class<? extends CollidableElement>, CollisionStrategy> colHandlerMap =
            new HashMap<Class<? extends CollidableElement>, CollisionStrategy>() {{
                put(Skane.class, new NullCollision());
                put(SkaneBody.class, new NullCollision());
                put(Wall.class, new BlockCollision());
                put(MeleeGuy.class, new AttackCollisionStrat());
                put(Civilian.class, new AttackCollisionStrat());
            }};

    public SkaneController(Skane ska, int scentDur) {
        super(colHandlerMap);
        this.ska = ska;
        this.maxOxy = ska.getMaxOxygenLevel();
        this.scentDur = scentDur;
    }

    public void takeDamage(int dmg) {
        if (dmg < 0) return;
        ska.takeDamage(dmg);
        for (int i = 0; i < dmg; ++i)
            ska.shrink();
    }

    public void nom(int nourishment) {
        if (nourishment < 0) return;
        ska.setHp(ska.getHp() + nourishment);
        for (int i = 0; i < nourishment; ++i)
            ska.grow();
    }

    public void toggleBury() {
        if (ska.isBury())
            ska.bury(false);
        else if (ska.getOxygenLevel() == maxOxy)
            ska.bury(true);
    }

    public void inhale() {
        int oxy_lvl = ska.getOxygenLevel();
        if (oxy_lvl == 0)
            ska.bury(false);

        if (ska.isBury())
            ska.setOxygenLevel(oxy_lvl - 1);
        else if (oxy_lvl < maxOxy)
            ska.setOxygenLevel(Math.min(oxy_lvl + 2, maxOxy));
    }

    public void tickScentTrail() {
        ska.tickScentTrail();
        ska.dropScent(scentDur);
    }

    @Override
    public void setEvent(EVENT event) {
        currEvent = event;
    }

    @Override
    public void update(Room room) {
        tickScentTrail();
        inhale();

        if (currEvent == EVENT.NullEvent) return;
        else if (currEvent == EVENT.Bury) toggleBury();
        else { // Movement Event
            ska.dropScent(scentDur);
            Position newPos;
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
                default:
                    newPos = ska.getPos();
                    break;
            }
            if (canMove(newPos, ska, room))
                move(ska, newPos);
        }

        currEvent = EVENT.NullEvent;
    }

    @Override
    public void move(Skane ska, Position pos) {
        ska.setPos(pos);
    }
}
