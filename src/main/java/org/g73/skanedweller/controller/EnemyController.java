package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.collision_strategy.BlockCollision;
import org.g73.skanedweller.controller.collision_strategy.CollisionStrategy;
import org.g73.skanedweller.controller.collision_strategy.NullCollision;
import org.g73.skanedweller.controller.collision_strategy.SkaneDamagedStrat;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.element.Civilian;
import org.g73.skanedweller.model.element.Element;
import org.g73.skanedweller.model.element.MeleeGuy;
import org.g73.skanedweller.model.element.Wall;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyController extends MovableController<Element> {
    protected static final Map<Class<? extends Collidable>, CollisionStrategy> colHandlerMap =
            new HashMap<Class<? extends Collidable>, CollisionStrategy>() {{
                put(Skane.class, new SkaneDamagedStrat());
                put(SkaneBody.class, new BlockCollision());
                put(Wall.class, new BlockCollision());
                put(MeleeGuy.class, new NullCollision());
                put(Civilian.class, new NullCollision());
            }};

    public EnemyController() {
        super(colHandlerMap);
    }

    public EnemyController(CollisionHandler colHandler) {
        super(colHandler);
    }

    private void MoveEnemies(Room room) {
        List<Position> posList;
        for (Element e : room.getEnemies()) {
            if (e.getMovCounter() > 0) {
                e.tickMovCounter();
                continue;
            }
            posList = e.genMoves(room);
            for (Position p : posList) {
                if (canMove(p, e, room)) {
                    e.setPos(p);
                    break;
                }
            }
        }
    }

    @Override
    public void update(Room room) {
        this.MoveEnemies(room);
    }

    @Override
    public void move(Element e, Position pos) {
        e.setPos(pos);
    }
}
