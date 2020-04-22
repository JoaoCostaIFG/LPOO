package room;

import room.colliders.Collider;
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
    private Skane skane;
    private List<Wall> walls;
    private List<Civilian> civies;

    private List<MeleeGuy> meleeGuys;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.observers = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.civies = new ArrayList<>();
        this.meleeGuys = new ArrayList<>();
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

    public List<Civilian> getCivies() {
        return civies;
    }

    public List<MeleeGuy> getMeleeGuys() {
        return meleeGuys;
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

    public List<Element> getElements() {
        List<Element> elems = new ArrayList<>();
        elems.add(skane);

        for (SkaneBody sb : this.skane.getBody())
            elems.add(sb);

        for (Wall w : walls)
            elems.add(w);

        for (Civilian c : civies)
            elems.add(c);

        for (MeleeGuy m : meleeGuys)
            elems.add(m);

        return elems;
    }

    public List<CollidableElement> getCollElements() { // Equal to enemies, all are collidable // Equal to enemies, all are collidable
        List<CollidableElement> elems = new ArrayList<>();
        elems.add(skane);

        for (SkaneBody sb : this.skane.getBody())
            elems.add(sb);

        for (Wall w : walls)
            elems.add(w);

        for (Civilian c : civies)
            elems.add(c);

        for (MeleeGuy m : meleeGuys)
            elems.add(m);

        return elems;
    }

    public List<Element> getSamePos(Position pos) {
        List<Element> elems = new ArrayList<>();

        for(Element e: getElements())
            if(e.getPos().equals(pos))
                elems.add(e);

        return elems;
    }

    public void moveSkane(Position new_p) {
        this.skane.setPos(new_p);
    }

    public void addElement(Element e) {
        if (e instanceof Skane) skane = (Skane) e;
        else if (e instanceof Wall) walls.add((Wall) e);
        else if (e instanceof Civilian) civies.add((Civilian) e);
        else if (e instanceof MeleeGuy) meleeGuys.add((MeleeGuy) e);
    }

    public void addElements(List<Element> elems) {
        for (Element e : elems)
            addElement(e);
    }

    // TODO Can be optimized (Divide by octants) or use Listenable
    public List<CollidableElement> getCollidingElems(CollidableElement ent) {
        List<CollidableElement> res = new ArrayList<>();
        for(CollidableElement e: getCollElements())
            if (e.collidesWith(ent))
                res.add(e);
        return res;
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

    /*
     * x0, y0: coordinates of start of the line.
     * deltaX, deltaY: length of the line (length => abs => > 0).
     * xDirection: 1 if line is drawn left to right, -1 if drawn right to left.
     * yDirection: 1 if line is drawn top to bottom, -1 if drawn bottom to top.
     */
    private List<Element> octant03Ray(int x0, int y0, int deltaX, int deltaY, int xDirection, int yDirection) {
        /*
         * Draws a line in octant 0 or 3 ( |deltaX| >= |deltaY| )
         * => x is the dominant dimension.
         */
        List<Element> elems = new ArrayList<>();

        // Set up initial error term and values used inside drawing loop
        int deltaYx2 = deltaY * 2;
        int deltaYx2MinusDeltaXx2 = deltaYx2 - deltaX * 2;
        int errorTerm = deltaYx2 - deltaX;

        int x = x0, y = y0;
        while (elems.size() == 0 && (x > 0 && x < width)) {
            /* See if it's time to advance the Y coordinate */
            if (errorTerm >= 0) {
                // Advance the Y coordinate & adjust the error term back down
                y += yDirection;
                errorTerm += deltaYx2MinusDeltaXx2;
            } else {
                // Add to the error term
                errorTerm += deltaYx2;
            }

            // advance the X coordinate to next pixel
            x += xDirection;
            elems = getSamePos(new Position(x, y));
        }

        return elems;
    }

    /*
     * x0, y0: coordinates of start of the line.
     * deltaX, deltaY: length of the line (length => abs => > 0).
     * xDirection: 1 if line is drawn left to right, -1 if drawn right to left.
     * yDirection: 1 if line is drawn top to bottom, -1 if drawn bottom to top.
     */
    private List<Element> octant12Ray(int x0, int y0, int deltaX, int deltaY, int xDirection, int yDirection) {
        /*
         * Draws a line in octant 1 or 2 ( |deltaX| <= |deltaY| )
         * => y is the dominant dimension.
         */
        List<Element> elems = new ArrayList<>();

        // Set up initial error term and values used inside drawing loop
        int deltaXx2 = deltaX * 2;
        int deltaXx2MinusDeltaYx2 = deltaXx2 - deltaY * 2;
        int errorTerm = deltaXx2 - deltaY;

        int x = x0, y = y0;
        while (elems.size() == 0 && (y > 0 && y < height)) {
            /* See if it's time to advance the X coordinate */
            if (errorTerm >= 0) {
                // Advance the X coordinate & adjust the error term back down
                x += xDirection;
                errorTerm += deltaXx2MinusDeltaYx2;
            } else {
                // Add to the error term
                errorTerm += deltaXx2;
            }

            // advance the Y coordinate to next pixel
            y += yDirection;
            elems = getSamePos(new Position(x, y));
        }

        return elems;
    }

    public List<Element> raycast(Position s, Position t) {
        /*
         * Fast bresenham's line-drawing algorithm adapted for
         * collision detetion ray-casting. Using an integer arithmetic
         * only version.
         * More info about the original algorithm can be found on the book:
         * "Black Book - Special edition", by Michael Abrash's.
         */
        List<Element> elems;

        /* source and target coords */
        int x0 = s.getX(), x1 = t.getX(), y0 = s.getY(), y1 = t.getY();

        int deltaX = x1 - x0;
        int xDirection;
        if (deltaX >= 0) {
            xDirection = 1;
        } else {
            xDirection = -1;
            deltaX = -deltaX; // abs
        }
        int deltaY = y1 - y0;
        int yDirection;
        if (deltaY >= 0) {
            yDirection = 1;
        } else {
            yDirection = -1;
            deltaY = -deltaY; // abs
        }

        if (deltaX > deltaY)
            elems = octant03Ray(x0, y0, deltaX, deltaY, xDirection, yDirection);
        else
            elems = octant12Ray(x0, y0, deltaX, deltaY, xDirection, yDirection);

        return elems;
    }

    public boolean isSkanePos(Position p) {
        if (p.equals(skane.getPos()))
            return true;

        for (SkaneBody b : skane.getBody())
            if (p.equals(b.getPos()))
                return true;

        return false;
    }
}
