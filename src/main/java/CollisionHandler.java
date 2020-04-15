import room.Position;
import room.element.Skane;
import room.element.Wall;

public class CollisionHandler {
    private Game game;
    CollisionHandler(Game game) {
        this.game = game;
    }

    public boolean canSkaneMove(Position position) {
        Skane skane = game.getRoom().getSkane();

        // check if alive
        if (!skane.isAlive())
            return false;

        // skane didn't move
        if (position.equals(skane.getPos()))
            return false;

        // wall collision
        for (Wall wall : game.getRoom().getWalls()) {
            if (position.equals(wall.getPos()))
                return false;
        }

        // bound checking
        return (position.getX() > 0 && position.getX() < (game.getRoom().getWidth() - 1) &&
                position.getY() > 0 && position.getY() < (game.getRoom().getHeight() - 1));
    }
}
