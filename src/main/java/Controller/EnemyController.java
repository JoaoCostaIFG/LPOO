package Controller;

import Controller.collisionStrategy.BlockCollision;
import Controller.collisionStrategy.CollisionStrategy;
import Controller.collisionStrategy.NullCollision;
import room.Position;
import room.Room;
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
        for (Civilian civ : room.getCivies()) {
            posList = civ.executeStrategy();
            for (Position pos : posList)
                if (canMove(pos, civ, room)) {
                    move(civ, pos);
                    break;
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
