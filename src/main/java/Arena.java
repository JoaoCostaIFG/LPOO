import GameElement.Position;
import GameElement.Skane;
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
    private MovCommand skane_mov;
    private List<Wall> walls;

    public Arena(TerminalSize board_size) {
        this.board_size = board_size;
        this.game_state = Game.GameState.NORMAL;

        /* spawn skane at random pos (inside bounds) */
        Random random = new Random();
        this.skane = new Skane(random.nextInt(getBoardWidth() - 2) + 1,
                random.nextInt(getBoardHeight() - 2) + 1);
        this.skane_mov = new MovStop(skane);

        this.walls = createWalls();
    }

    public int getBoardWidth() {
        return board_size.getColumns();
    }

    public int getBoardHeight() {
        return board_size.getRows();
    }

    public Game.GameState getGameState() {
        return this.game_state;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < getBoardWidth(); ++c) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, getBoardHeight() - 1));
        }

        for (int r = 1; r < getBoardHeight() - 1; ++r) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(getBoardWidth() - 1, r));
        }

        return walls;
    }

    public void resizeBoard(TerminalSize new_board_size) {
        this.board_size = new_board_size;
        this.walls = createWalls();

        // TODO put skane inbounds if it is not
    }

    public void update() {
        moveSkane();

        checkGame();
        releaseKeys();
    }

    public void draw(TextGraphics gra) {
        // background
        gra.setBackgroundColor(TextColor.Factory.fromString("#313742"));
        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(getBoardWidth(), getBoardHeight()), ' ');

        // walls
        for (Wall wall : walls)
            wall.draw(gra);

        // skane
        skane.draw(gra);
        //gra.putString(new TerminalPosition(getBoardWidth() + 1, 1),
        //       "HP: " + Integer.toString(skane.getHp()));
    }

    public void releaseKeys() {
        this.skane_mov = new MovStop(skane);
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    this.skane_mov = new MovLeft(skane, 1);
                    break;
                case 'd':
                case 'D':
                    this.skane_mov = new MovRight(skane, 1);
                    break;
                case 'w':
                case 'W':
                    this.skane_mov = new MovUp(skane, 1);
                    break;
                case 's':
                case 'S':
                    this.skane_mov = new MovDown(skane, 1);
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
                this.skane_mov = new MovLeft(skane, 1);
                break;
            case ArrowRight:
                this.skane_mov = new MovRight(skane, 1);
                break;
            case ArrowUp:
                this.skane_mov = new MovUp(skane, 1);
                break;
            case ArrowDown:
                this.skane_mov = new MovDown(skane, 1);
                break;
            case Escape:
            case EOF:
                this.game_state = Game.GameState.END;
                break;
            default:
                break;
        }
    }

    private void checkGame() {
        if (!skane.isAlive()) { // Skane died ;(
            skane.setMe("");
            this.game_state = Game.GameState.LOSE;
        }
    }

    private boolean canSkaneMove(Position position) {
        // check if alive
        if (!skane.isAlive())
            return false;

        // skane didn't move
        if (position.equals(skane.getPos()))
            return false;

        // wall collision
        for (Wall wall : walls) {
            if (position.equals(wall.getPos()))
                return false;
        }

        // bound checking
        return (position.getX() > 0 && position.getX() < (getBoardWidth() - 1) &&
                position.getY() > 0 && position.getY() < (getBoardHeight() - 1));
    }

    private void moveSkane() {
        if (canSkaneMove(skane_mov.execute()))
            skane.setPos(skane_mov.execute());
    }
}
