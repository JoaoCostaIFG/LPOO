import room.Position;
import room.element.skane.Skane;
import room.element.Wall;

public class CollisionHandler {
    private GameController game_controller;

    CollisionHandler(GameController game_controller) {
        this.game_controller = game_controller;
    }

    public boolean canSkaneMove(Position position) {
        Skane skane = game_controller.getRoom().getSkane();

        if (!skane.isAlive())
            return false;
        if (position.equals(skane.getPos())) // skane didn't move
            return false;
        // wall collision
        for (Wall wall : game_controller.getRoom().getWalls()) {
            if (position.equals(wall.getPos()))
                return false;
        }

        // bound checking
        return (position.getX() > 0 && position.getX() < (game_controller.getRoom().getWidth() - 1) &&
                position.getY() > 0 && position.getY() < (game_controller.getRoom().getHeight() - 1));
    }
}
