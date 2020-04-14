package arena;

import arena.element.DrawableElement;
import arena.element.Element;
import arena.element.Skane;
import com.googlecode.lanterna.TerminalSize;
import arena.element.Wall;
import commands.*;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private TerminalSize board_size;
    private List<ArenaObserver> observers;
    private Skane skane;
    private Command skane_mov;
    private List<Wall> walls;
    private Boolean is_done;

    public Arena(TerminalSize board_size) {
        this.is_done = false;
        this.board_size = board_size;

        this.walls = new ArrayList<>();
        this.observers = new ArrayList<>();

        this.skane_mov = new NullCommand();
    }

    public int getWidth() {
        return board_size.getColumns();
    }

    public int getHeight() {
        return board_size.getRows();
    }

    public void setSize(TerminalSize new_size) {
        this.board_size = new_size;
    }

    public Boolean isArenaFinished() {
        return this.is_done;
    }

    public void finishArena() {
        this.is_done = true;
    }

    public Skane getSkane() {
        return this.skane;
    }

    public List<DrawableElement> getAllDrawable() {
        List<DrawableElement> d = new ArrayList<>();

        d.add(skane);
        d.addAll(walls);

        return d;
    }

    public void addElement(Element e) {
        if (e instanceof Skane) skane = (Skane) e;
        if (e instanceof Wall) walls.add((Wall) e);
    }

    public void addObserver(ArenaObserver obs) {
        this.observers.add(obs);
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
        return (position.getX() > 0 && position.getX() < (getWidth() - 1) &&
                position.getY() > 0 && position.getY() < (getHeight() - 1));
    }

    public void moveSkane(Position new_p) {
        if (canSkaneMove(new_p))
            skane.setPos(new_p);
    }
}
