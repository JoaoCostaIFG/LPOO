package Controller;

import room.Position;
import room.Room;
import room.element.Civilian;
import room.element.MeleeGuy;

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
            for (Position p : posList) {
                if (colHandler.canSkaneMove(p)) { // TODO
                    c.setPos(p);
                    break;
                }
            }
        }

        for (MeleeGuy m : room.getMeleeGuys()) {
            posList = m.executeStrategy();
            for (Position p : posList) {
                if (colHandler.canSkaneMove(p)) { // TODO
                    m.setPos(p);
                    break;
                }
            }
        }
    }
}
