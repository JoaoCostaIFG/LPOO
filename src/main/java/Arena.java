import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private Game.GameState game_state;
    private TerminalSize board_size;
    private Skane skane;
    private List<Wall> walls;

    public Arena(TerminalSize board_size) {
        this.board_size = board_size;
        this.game_state = Game.GameState.NORMAL;

        /* spawn skane at random pos (inside bounds) */
        Random random = new Random();
        this.skane = new Skane(random.nextInt(getBoardWidth() - 2) + 1,
                random.nextInt(getBoardHeigth() - 2) + 1);

        this.walls = createWalls();
    }

    public int getBoardWidth() {
        return board_size.getColumns();
    }

    public int getBoardHeigth() {
        return board_size.getRows();
    }

    public Game.GameState getGameState() {
        return this.game_state;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < getBoardWidth(); ++c) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, getBoardHeigth() - 1));
        }

        for (int r = 1; r < getBoardHeigth() - 1; ++r) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(getBoardWidth() - 1, r));
        }

        return walls;
    }

    public void draw(TextGraphics gra) {
        // background
        gra.setBackgroundColor(TextColor.Factory.fromString("#313742"));
        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(getBoardWidth(), getBoardHeigth()), ' ');

        // walls
        for (Wall wall : walls)
            wall.draw(gra);

        // skane
        skane.draw(gra);
        gra.putString(new TerminalPosition(getBoardWidth() + 1, 1),
                "HP: " + Integer.toString(skane.getHp()));
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    moveSkane(skane.moveLeft());
                    break;
                case 'd':
                case 'D':
                    moveSkane(skane.moveRight());
                    break;
                case 'w':
                case 'W':
                    moveSkane(skane.moveUp());
                    break;
                case 's':
                case 'S':
                    moveSkane(skane.moveDown());
                    break;
                case 'r':
                case 'R':
                    this.game_state = Game.GameState.RESTART;
                    break;
                default:
                    break;
            }
        }

        switch (key.getKeyType()) {
            case ArrowLeft:
                moveSkane(skane.moveLeft());
                break;
            case ArrowRight:
                moveSkane(skane.moveRight());
                break;
            case ArrowUp:
                moveSkane(skane.moveUp());
                break;
            case ArrowDown:
                moveSkane(skane.moveDown());
                break;
            case Escape:
            case EOF:
                this.game_state = Game.GameState.END;
                break;
            default:
                break;
        }
    }

    private boolean canSkaneMove(Position position) {
        // check if alive
        if (!skane.isAlive())
            return false;

        // wall collision
        for (Wall wall : walls) {
            if (position.equals(wall.getPos()))
                return false;
        }

        // stay inside arena
        return (position.getX() > 0 && position.getX() < (getBoardWidth() - 1) &&
                position.getY() > 0 && position.getY() < (getBoardHeigth() - 1));
    }

    private void checkGame() {
        if (skane.isAlive())    // nothing happened
            return;
        else {  // lose state
            skane.setMe("");
            this.game_state = Game.GameState.LOSE;
        }
    }

    private void moveSkane(Position position) {
        if (canSkaneMove(position)) {
            skane.setPos(position);
            checkGame();
        }
    }
}
