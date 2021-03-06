package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.collision_strategy.BlockCollision;
import org.g73.skanedweller.controller.collision_strategy.CollisionStrategy;
import org.g73.skanedweller.controller.collision_strategy.NullCollision;
import org.g73.skanedweller.controller.collision_strategy.SkaneAttackCollision;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.g73.skanedweller.model.element.RangedGuy;
import org.g73.skanedweller.model.element.Wall;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.g73.skanedweller.view.EVENT;

import java.util.HashMap;
import java.util.Map;

public class SkaneController extends MovableController<Skane> implements PlayerController {
    protected static final Map<Class<? extends Collidable>, CollisionStrategy> colHandlerMap =
            new HashMap<Class<? extends Collidable>, CollisionStrategy>() {{
                put(Skane.class, new BlockCollision());
                put(SkaneBody.class, new NullCollision());
                put(Wall.class, new BlockCollision());
                put(MeleeGuy.class, new SkaneAttackCollision());
                put(Civilian.class, new SkaneAttackCollision());
                put(RangedGuy.class, new SkaneAttackCollision());
            }};
    private Skane ska;
    private int scentDur;
    private EVENT currEvent;

    public SkaneController(Skane ska, int scentDur) {
        super(colHandlerMap);
        this.ska = ska;
        this.scentDur = scentDur;
        this.currEvent = EVENT.NullEvent;
    }

    public void toggleBury() {
        if (ska.isBury())
            ska.bury(false);
        else if (ska.getOxygenLevel() == ska.getMaxOxygenLevel())
            ska.bury(true);
    }

    public void inhale() {
        int oxy_lvl = ska.getOxygenLevel();
        if (oxy_lvl == 0)
            ska.bury(false);

        if (ska.isBury())
            ska.setOxygenLevel(oxy_lvl - 1);
        else if (oxy_lvl < ska.getMaxOxygenLevel())
            ska.setOxygenLevel(Math.min(oxy_lvl + 2, ska.getMaxOxygenLevel()));
    }

    public void tickScentTrail() {
        ska.tickScentTrail();
        ska.dropScent(scentDur);
    }

    @Override
    public boolean isAlive() {
        return ska.isAlive();
    }

    @Override
    public void setEvent(EVENT event) {
        currEvent = event;
    }

    @Override
    public void handleEvent(EVENT event, Room room) {
        if (event == EVENT.NullEvent) return;
        else if (event == EVENT.Bury) toggleBury();
        else { // Movement Event
            Position newPos;
            boolean changed = false;
            switch (event) {
                case MoveLeft:
                    newPos = ska.moveLeft();
                    changed = true;
                    break;
                case MoveRight:
                    newPos = ska.moveRight();
                    changed = true;
                    break;
                case MoveUp:
                    newPos = ska.moveUp();
                    changed = true;
                    break;
                case MoveDown:
                    newPos = ska.moveDown();
                    changed = true;
                    break;
                default: // useless
                    newPos = ska.getPos();
                    break;
            }
            if (changed && canMove(newPos, ska, room)) {
                move(ska, newPos);
                tickScentTrail();
            }
        }
        currEvent = EVENT.NullEvent; // reset event
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
