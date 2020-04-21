package collisions;

import room.Position;
import room.colliders.RectangleCollider;
import room.element.skane.Skane;
import room.Room;
import room.element.Wall;

public class CollisionHandler {
    private Room room;

    public CollisionHandler(Room room) {
        this.room = room;
    }

    public boolean canSkaneMove(Position position) {
        Skane skane = room.getSkane();
        if (!skane.isAlive())
            return false;
        if (skane.getPos().equals(position)) // skane didn't move
            return false;

        // Create a collider in the wanted position and check for collisions
        RectangleCollider col = new RectangleCollider(position, 1, 1);

        // wall collision
        for (Wall wall : room.getWalls()) {
            if (col.collidesWith(wall.getCollider()))
                return false;
        }

        // bound checking
        if (!(position.getX() > 0 && position.getX() < (room.getWidth() - 1) &&
                position.getY() > 0 && position.getY() < (room.getHeight() - 1)))
           return false;

        return true;
    }
}
