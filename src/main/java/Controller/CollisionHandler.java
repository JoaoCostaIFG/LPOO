package Controller;

import room.Position;
import room.element.skane.Skane;
import room.Room;
import room.element.Wall;

public class CollisionHandler {
    private Room room;

    CollisionHandler(Room room) {
        this.room = room;
    }

    public boolean canSkaneMove(Position position) {
        Skane skane = room.getSkane();

        if (!skane.isAlive())
            return false;
        if (position.equals(skane.getPos())) // skane didn't move
            return false;
        // wall collision
        for (Wall wall : room.getWalls()) {
            if (position.equals(wall.getPos()))
                return false;
        }

        // bound checking
        return (position.getX() > 0 && position.getX() < (room.getWidth() - 1) &&
                position.getY() > 0 && position.getY() < (room.getHeight() - 1));
    }
}
