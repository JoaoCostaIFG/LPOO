package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.collision_strategy.BlockCollision;
import org.g73.skanedweller.controller.collision_strategy.CollisionStrategy;
import org.g73.skanedweller.controller.collision_strategy.NullCollision;
import org.g73.skanedweller.controller.collision_strategy.SkaneAttackCollision;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.g73.skanedweller.model.element.Wall;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.g73.skanedweller.view.EVENT;

import java.util.HashMap;
import java.util.Map;

public class SkaneController extends MovableController<Skane> implements PlayerController {
    private Skane ska;
    private int maxOxy;
    private int scentDur;
    private EVENT currEvent;

    protected static final Map<Class<? extends Collidable>, CollisionStrategy> colHandlerMap =
            new HashMap<Class<? extends Collidable>, CollisionStrategy>() {{
                put(Skane.class, new NullCollision());
                put(SkaneBody.class, new NullCollision());
                put(Wall.class, new BlockCollision());
                put(MeleeGuy.class, new SkaneAttackCollision());
                put(Civilian.class, new SkaneAttackCollision());
            }};

    public SkaneController(Skane ska, int scentDur) {
        super(colHandlerMap);
        this.ska = ska;
        this.maxOxy = ska.getMaxOxygenLevel();
        this.scentDur = scentDur;
        this.currEvent = EVENT.NullEvent;
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
    public void handleEvent(EVENT event, Room room) {
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
            if (canMove(newPos, ska, room)) {
                move(ska, newPos);
                tickScentTrail();
            }
        }
        currEvent = EVENT.NullEvent;
    }

    @Override
    public void update(Room room) {
        inhale();
        handleEvent(this.currEvent, room);
    }

    @Override
    public void move(Skane ska, Position pos) {
        ska.setPos(pos);
    }
}
