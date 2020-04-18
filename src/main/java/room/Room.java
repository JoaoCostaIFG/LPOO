package room;

import room.element.*;
import observe.Observable;
import observe.Observer;
import room.element.skane.Scent;
import room.element.skane.Skane;
import room.element.skane.SkaneBody;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class Room implements Observable<Room> {
    private int width, height;
    private List<Observer<Room>> observers;
    private Skane skane;
    private List<Wall> walls;
    private List<Civilian> civies;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.observers = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.civies = new ArrayList<>();
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

    public Skane getSkane() {
        return this.skane;
    }

    public List<Element> getSamePos(Position pos) {
        List<Element> elems = new ArrayList<>();

        if (skane.getPos().equals(pos))
            elems.add(skane);

        for (SkaneBody sb : skane.getBody())
            if (sb.getPos().equals(pos))
                elems.add(sb);

        for (Wall w : walls)
            if (w.getPos().equals(pos))
                elems.add(w);

        for (Civilian c : civies)
            if (c.getPos().equals(pos))
                elems.add(c);

        return elems;
    }

    public void moveSkane(Position new_p) {
        this.skane.setPos(new_p);
    }

    public void addElement(Element e) {
        if (e instanceof Skane) skane = (Skane) e;
        else if (e instanceof Wall) walls.add((Wall) e);
        else if (e instanceof Civilian) civies.add((Civilian) e);
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

            sss.add(new Scent(new Position(x, y), 1));
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

            sss.add(new Scent(new Position(x, y), 1));
        }

        return elems;
    }

    public List<Element> raycast(Position s, Position t) {
        /*
         * Fast bresenham's line-drawing algorithm adapted for
         * collision detetion ray-casting. Using an integer arithmetic
         * only version.
         * More info about the original algorithm can be found on the book:
         * "Black Book - Special edition", by Michael Abrash's
         */
        List<Element> elems;

        /* source and target coords */
        int x0 = s.getX(), x1 = t.getX(), y0 = s.getY(), y1 = t.getY();

        /*
        // Swapping source and target when source's y > target's y, allows us
        // to get rid of yDirection. This wasn't done in this case because the
        // ray cast would be inverted in those cases => useful for line-drawing
        // bad for collision detection.
        int swap;
        if (y0 > y1) { // swap source and target
            swap = y0;
            y0 = y1;
            y1 = swap;
            swap = x0;
            x0 = x1;
            x1 = swap;
        }
        */

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

        // deltaX and deltaY are enough to know the target location
        // (given the source location and the x/yDirection).
        if (deltaX > deltaY)
            elems = octant03Ray(x0, y0, deltaX, deltaY, xDirection, yDirection);
        else
            elems = octant12Ray(x0, y0, deltaX, deltaY, xDirection, yDirection);

        return elems;
    }

    public boolean isSkanePos(Position p) {
        for (Element e : getSamePos(p))
            if (e instanceof Skane || e instanceof SkaneBody)
                return true;

        return false;
    }


    private List<Scent> sss = new ArrayList<>();

    public List<Scent> getsss() {
        return sss;
    }

    private int i = 0;

    public void moveCivilians() {
        // TODO strategy pattern
        List<Element> ray;
        Position civie_pos, p;
        double dist, dist_tmp;
        boolean got_one;

        if (i >= 5)
            i = 0;
        else if (i >= 1) {
            ++i;
            return;
        }
        ++i;

        for (Civilian civie : civies) {
            civie_pos = civie.getPos();
            if (isSkanePos(civie_pos)) // don't go anywhere if already on skane
                continue;

            sss.clear();
            p = civie_pos;
            ray = raycast(civie_pos, skane.getPos());

            dist = -1;
            got_one = false;
            if (ray.size() > 0) {
                p = ray.get(0).getPos();
                if (isSkanePos(p)) {
                    got_one = true;
                    dist = civie_pos.dist(p);
                }
            }

            for (SkaneBody sb : skane.getBody()) {
                ray = raycast(civie_pos, sb.getPos());

                if (ray.size() > 0) {
                    p = ray.get(0).getPos();
                    if (isSkanePos(p)) {
                        dist_tmp = civie_pos.dist(p);
                        if (!got_one || dist_tmp < dist) {
                            got_one = true;
                            dist = dist_tmp;
                        }
                    }
                }
            }

            if (got_one) {
                int dx = p.getX() - civie_pos.getX();
                int dy = p.getY() - civie_pos.getY();

                Position dest_hori, dest_vert;
                if (dx > 0)
                    dest_hori = civie.moveRight();
                else
                    dest_hori = civie.moveLeft();
                if (dy > 0)
                    dest_vert = civie.moveDown();
                else
                    dest_vert = civie.moveUp();

                boolean moved = false;
                boolean mainX = (Math.abs(dx) > Math.abs(dy));
                if (mainX && getSamePos(dest_hori).size() == 0) {
                    moved = true;
                    civie.setPos(dest_hori);
                } else if (getSamePos(dest_vert).size() == 0) {
                    moved = true;
                    civie.setPos(dest_vert);
                }

                if (!moved) {
                    if (mainX && getSamePos(dest_vert).size() == 0)
                        civie.setPos(dest_vert);
                    else if (getSamePos(dest_hori).size() == 0)
                        civie.setPos(dest_hori);
                }
            }
        }
    }
}
