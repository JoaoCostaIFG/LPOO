package Controller;

import room.Position;
import room.Room;
import room.element.Entity;

import java.util.List;

public class EnemyController {
    CollisionHandler colHandler;
    Room room;

    public EnemyController(Room room, CollisionHandler colHandler) {
        this.room = room;
        this.colHandler = colHandler;
    }

    public void MoveEnemies() {
        List<Position> posList;
        for (Entity e : room.getEnemies()) {
            posList = e.executeStrategy(room);

            for (Position p : posList) {
                if (colHandler.canSkaneMove(p)) { // TODO
                    e.setPos(p);
                    break;
                }
            }
        }
    }
}
