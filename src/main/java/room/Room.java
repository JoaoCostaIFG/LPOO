package room;

import room.element.*;
import observe.Observable;
import observe.Observer;
import room.element.skane.Scent;
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

    private List<Element> octant03Ray(int x0, int y0, int deltaX, int deltaY, int xDirection) {
        System.out.println("oct 03");
        /*
         * Draws a line in octant 0 or 3 ( |deltaX| >= deltaY ).
         * x0, y0: coordinates of start of the line
         * deltaX, deltaY: length of the line (both > 0)
         * xDirection: 1 if line is drawn left to right, -1 if drawn right to left
         */
        List<Element> elems = new ArrayList<>();

        // Set up initial error term and values used inside drawing loop
        int deltaYx2 = deltaY * 2;
        int deltaYx2MinusDeltaXx2 = deltaYx2 - deltaX * 2;
        int errorTerm = deltaYx2 - deltaX;

        while (elems.size() == 0 && (x0 > 0 && x0 < width)) {
            /* See if it's time to advance the Y coordinate */
            if (errorTerm >= 0) {
                // Advance the Y coordinate & adjust the error term back down
                ++y0;
                errorTerm += deltaYx2MinusDeltaXx2;
            } else {
                // Add to the error term
                errorTerm += deltaYx2;
            }

            // advance the X coordinate to next pixel
            x0 += xDirection;
            elems = getSamePos(new Position(x0, y0));

            sss.add(new Scent(new Position(x0, y0), 1));
        }

        return elems;
    }

    private List<Element> octant12Ray(int x0, int y0, int deltaX, int deltaY, int xDirection) {
        System.out.println("oct 12");
        /*
         * Draws a line in octant 1 or 2 ( |deltaX| < deltaY ).
         * x0, y0: coordinates of start of the line
         * deltaX, deltaY: length of the line (both > 0)
         * xDirection: 1 if line is drawn left to right, -1 if drawn right to left
         */
        List<Element> elems = new ArrayList<>();

        // Set up initial error term and values used inside drawing loop
        int deltaXx2 = deltaX * 2;
        int deltaXx2MinusDeltaYx2 = deltaXx2 - (int) (deltaY * 2);
        int errorTerm = deltaXx2 - (int) deltaY;

        while (elems.size() == 0 && (y0 > 0 && y0 < height)) {
            /* See if it's time to advance the X coordinate */
            if (errorTerm >= 0) {
                // Advance the X coordinate & adjust the error term back down
                x0 += xDirection;
                errorTerm += deltaXx2MinusDeltaYx2;
            } else {
                // Add to the error term
                errorTerm += deltaXx2;
            }

            // advance the Y coordinate to next pixel
            y0++;
            elems = getSamePos(new Position(x0, y0));

            sss.add(new Scent(new Position(x0, y0), 1));
        }

        return elems;
    }

    public List<Element> raycast(Position s, Position t) {
        /*
         * Fast bresenham's line-drawing algorithm adapted for
         * collision detetion ray-casting.
         */
        List<Element> elems;

        /* source and target coords */
        int x0 = s.getX(), x1 = t.getX(), y0 = s.getY(), y1 = t.getY();
        int tmp;
        if (y0 > y1) { // swap source and target
            tmp = y0;
            y0 = y1;
            y1 = tmp;
            tmp = x0;
            x0 = x1;
            x1 = tmp;
        }

        int deltaX = x1 - x0;
        int deltaY = y1 - y0;
        if (deltaX > 0) {
            if (deltaX > deltaY)
                elems = octant03Ray(x0, y0, deltaX, deltaY, 1);
            else
                elems = octant12Ray(x0, y0, deltaX, deltaY, 1);
        } else {
            deltaX = -deltaX; // absolute value of deltaX
            if (deltaX > deltaY)
                elems = octant03Ray(x0, y0, deltaX, deltaY, -1);
            else
                elems = octant12Ray(x0, y0, deltaX, deltaY, -1);
        }

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
    public void moveCivilians() {
        // TODO strategy pattern
        List<Element> ray;
        Position civie_pos, p;
        double dist, dist_tmp;
        boolean got_one;

        for (Civilian civie : civies) {
            civie_pos = civie.getPos();
            if (isSkanePos(civie_pos)) // don't go anywhere if already on skane
                continue;

            sss.clear();
            ray = raycast(civie_pos, skane.getPos());

            dist = -1;
            got_one = false;
            if (ray.size() > 0 && isSkanePos(ray.get(0).getPos())) {
                got_one = true;
                p = ray.get(0).getPos();
                dist = civie_pos.dist(p);
            }

            /*
            for (SkaneBody sb : skane.getBody()) {
                ray = raycast(civie_pos, sb.getPos());

                if (ray.size() > 0) {
                    p = ray.get(0).getPos();
                    dist_tmp = civie_pos.dist(p);
                    if (!got_one || dist_tmp < dist) {
                        got_one = true;
                        dist = dist_tmp;
                    }
                }
            }
             */
        }
    }
}
