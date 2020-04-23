package room;

import room.element.*;
import observe.Observable;
import observe.Observer;
import room.element.skane.Skane;
import room.element.skane.SkaneBody;

import java.util.ArrayList;
import java.util.List;

public class Room implements Observable<Room> {
    private int width, height;
    private List<Observer<Room>> observers;
    private Skane skane = null;
    private List<Wall> walls;
    private List<Entity> enemies;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.observers = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.enemies = new ArrayList<>();
    }

    public Room(TerminalSizeInterface board_size) {
        this(board_size.getColumns(), board_size.getRows());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(TerminalSizeInterface new_size) {
        this.setSize(new_size.getColumns(), new_size.getRows());
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Entity> getEnemies() {
        return enemies;
    }

    public Skane getSkane() {
        return this.skane;
    }

    public Position getSkanePos() {
        return this.skane.getPos();
    }

    public boolean isSkaneBury() {
        return this.skane.isBury();
    }

    public boolean isSkanePos(Position p) {
        if (p.equals(skane.getPos()))
            return true;

        for (SkaneBody b : skane.getBody())
            if (p.equals(b.getPos()))
                return true;

        return false;
    }

    public List<Element> getSamePos(Position pos) {
        List<Element> elems = new ArrayList<>();

        /* skane */
        if (skane != null) {
            if (skane.getPos().equals(pos))
                elems.add(skane);
            for (SkaneBody sb : skane.getBody())
                if (sb.getPos().equals(pos))
                    elems.add(sb);
        }

        /* other */
        for (Wall w : walls)
            if (w.getPos().equals(pos))
                elems.add(w);

        for (Entity e : enemies)
            if (e.getPos().equals(pos))
                elems.add(e);

        return elems;
    }

    public void moveSkane(Position new_p) {
        this.skane.setPos(new_p);
    }

    public void addElement(Element e) {
        if (e instanceof Skane) skane = (Skane) e;
        else if (e instanceof Wall) walls.add((Wall) e);
        else if (e instanceof Civilian) enemies.add((Civilian) e);
        else if (e instanceof MeleeGuy) enemies.add((MeleeGuy) e);
    }

    public void addElements(List<Element> elems) {
        for (Element e : elems)
            addElement(e);
    }

    @Override
    public void addObserver(Observer<Room> obs) {
        this.observers.add(obs);
    }

    @Override
    public void removeObserver(Observer<Room> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Room subject) {
        for (Observer<Room> observer : this.observers)
            observer.changed(this);
    }

    private List<Element> octant03Ray(Position s, Position t, int deltaX, int deltaY, int xDirection, int yDirection) {
        // Line in octant 0 or 3 (|deltaX| >= |deltaY|).
        List<Element> elems = new ArrayList<>();

        int errorUp = deltaY * 2;
        int errorDown = deltaX * 2 - errorUp;
        int errorTerm = errorUp - deltaX;

        int x = s.getX(), y = s.getY();
        while (elems.size() == 0 && (x != t.getX() || y != t.getY())) {
            if (errorTerm >= 0) {
                y += yDirection;
                errorTerm -= errorDown; // error goes down
            } else {
                errorTerm += errorUp;
            }

            x += xDirection;
            elems = getSamePos(new Position(x, y));
        }

        return elems;
    }

    private List<Element> octant12Ray(Position s, Position t, int deltaX, int deltaY, int xDirection, int yDirection) {
        // Line in octant 1 or 2 (|deltaX| <= |deltaY|).
        List<Element> elems = new ArrayList<>();

        int errorUp = deltaX * 2;
        int errorDown = deltaY * 2 - errorUp;
        int errorTerm = errorUp - deltaY;

        int x = s.getX(), y = s.getY();
        while (elems.size() == 0 && (x != t.getX() || y != t.getY())) {
            if (errorTerm >= 0) {
                x += xDirection;
                errorTerm -= errorDown; // error goes down
            } else {
                errorTerm += errorUp;
            }

            y += yDirection;
            elems = getSamePos(new Position(x, y));
        }

        return elems;
    }

    public List<Element> raycast(Position s, Position t) {
        /* Bresenham's line-drawing algorithm adapted for collision detetion
         * ray-casting. Integer arithmetic only version.
         * Idea from: "Black Book - Special edition", by Michael Abrash's.
         */
        int deltaX = t.getX() - s.getX(); // 'length' of the line
        int xDirection;
        if (deltaX >= 0) {
            xDirection = 1;
        } else {
            xDirection = -1;
            deltaX = -deltaX; // abs
        }

        int deltaY = t.getY() - s.getY(); // 'height' of the line
        int yDirection;
        if (deltaY >= 0) {
            yDirection = 1;
        } else {
            yDirection = -1;
            deltaY = -deltaY; // abs
        }

        if (deltaX > deltaY)
            return octant03Ray(s, t, deltaX, deltaY, xDirection, yDirection);
        return octant12Ray(s, t, deltaX, deltaY, xDirection, yDirection);
    }
}
