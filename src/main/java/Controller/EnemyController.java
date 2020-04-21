package Controller;

import room.Position;
import room.Room;
import room.element.Civilian;

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
        for (Civilian c : room.getCivies()) {
            posList = c.executeStrategy();
            for (Position p : posList){
                if (colHandler.canSkaneMove(p)) { // TODO
                    c.setPos(p);
                    break;
                }
            }
        }
    }
}
