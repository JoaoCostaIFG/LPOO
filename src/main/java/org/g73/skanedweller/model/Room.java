package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.g73.skanedweller.observe.Observable;
import org.g73.skanedweller.observe.Observer;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int width, height;
    private List<Observer<Room>> observers;
    private Skane skane = null;
    private List<Wall> walls;
    private List<Element> enemies;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.observers = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.enemies = new ArrayList<>();
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

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Element> getEnemies() {
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

    public List<Element> getElements() {
        List<Element> elems = new ArrayList<>();

        /* other */
        elems.addAll(walls);
        elems.addAll(enemies);

        /* skane */
        if (skane != null) {
            elems.add(skane);
            elems.addAll(skane.getBody());
        }

        return elems;
    }

    public void addElement(Element e) {
        if (e instanceof Skane) skane = (Skane) e;
        else if (e instanceof Wall) walls.add((Wall) e);
        else if (e instanceof Civilian) enemies.add(e);
        else if (e instanceof MeleeGuy) enemies.add(e);
        else if (e instanceof RangedGuy) enemies.add(e);
    }

    public void addElements(List<Element> elems) {
        for (Element e : elems)
            addElement(e);
    }

    public List<Element> getSamePos(Position pos) {
        List<Element> elems = new ArrayList<>();

        for (Element e : getElements())
            if (e.getPos().equals(pos))
                elems.add(e);

        return elems;
    }

    public List<Collidable> getCollidableElems() {
        // Equal to enemies, all are collidable
        List<Collidable> elems = new ArrayList<>();

        /* other */
        elems.addAll(walls);
        elems.addAll(enemies);

        /* skane */
        if (skane != null) {
            elems.add(skane);
            elems.addAll(skane.getBody());
        }

        return elems;
    }

    public List<Collidable> getColliding(Collidable ent) {
        // TODO Can be optimized (Divide by octants) or use Listenable
        List<Collidable> res = new ArrayList<>();
        for (Collidable e : getCollidableElems()) {
            if (e.collidesWith(ent) && !e.equals(ent))
                res.add(e);
        }

        return res;
    }

    public List<Collidable> getCollidingElemsInPos(Element element, Position pos) {
        // What elems does it collide if we move ent to the pos position?
        Position oldPos = element.shadowStep(pos);
        List<Collidable> res = getColliding(element);
        element.shadowStep(oldPos);
        return res;
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
