package controller;

import controller.collision_strategy.BlockCollision;
import controller.collision_strategy.CollisionStrategy;
import controller.collision_strategy.NullCollision;
import room.Position;
import room.Room;
import room.element.Entity;
import room.element.*;
import room.element.skane.Skane;
import room.element.skane.SkaneBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyController extends MovableController<Civilian> {
    protected static final Map<Class<? extends CollidableElement>, CollisionStrategy> colHandlerMap =
            new HashMap<Class<? extends CollidableElement>, CollisionStrategy>() {{
                put(Skane.class, new NullCollision());
                put(SkaneBody.class, new NullCollision());
                put(Wall.class, new BlockCollision());
                put(MeleeGuy.class, new NullCollision());
                put(Civilian.class, new NullCollision());
            }};

    public EnemyController() {
        super(colHandlerMap);
    }

    private void MoveEnemies(Room room) {
        List<Position> posList;
        for (Entity e : room.getEnemies()) {
            if (e.getMovCounter() > 0) {
                e.tickMovCounter();
                continue;
            }

            posList = e.executeStrategy(room);
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
    public void move(Civilian civ, Position pos) {
        civ.setPos(pos);
    }
}
