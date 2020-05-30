package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RayCast implements RayCasting {
    private List<Position> octant03Ray(Room room, Position s, Position t,
                                       int deltaX, int deltaY, int xDirection,
                                       int yDirection) {
        // Line in octant 0 or 3 (|deltaX| >= |deltaY|).
        List<Position> ret = new ArrayList<>();

        int errorUp = deltaY * 2;
        int errorDown = deltaX * 2 - errorUp;
        int errorTerm = errorUp - deltaX;

        int x = s.getX(), y = s.getY();
        while ((x != t.getX() || y != t.getY()) &&
                (x >= 0 && x <= room.getWidth() && y >= 0 && y <= room.getHeight())) {
            if (errorTerm >= 0) {
                y += yDirection;
                errorTerm -= errorDown; // error goes down
            } else {
                errorTerm += errorUp;
            }

            x += xDirection;
            ret.add(new Position(x, y));
            if (room.getSamePos(new Position(x, y)).size() > 0)
                break;
        }

        return ret;
    }

    private List<Position> octant12Ray(Room room, Position s, Position t,
                                       int deltaX, int deltaY, int xDirection,
                                       int yDirection) {
        // Line in octant 1 or 2 (|deltaX| <= |deltaY|).
        List<Position> ret = new ArrayList<>();

        int errorUp = deltaX * 2;
        int errorDown = deltaY * 2 - errorUp;
        int errorTerm = errorUp - deltaY;

        int x = s.getX(), y = s.getY();
        while ((x != t.getX() || y != t.getY()) &&
                (x >= 0 && x <= room.getWidth() && y >= 0 && y <= room.getHeight())) {
            if (errorTerm >= 0) {
                x += xDirection;
                errorTerm -= errorDown; // error goes down
            } else {
                errorTerm += errorUp;
            }

            y += yDirection;
            ret.add(new Position(x, y));
            if (room.getSamePos(new Position(x, y)).size() > 0)
                break;
        }

        return ret;
    }

    public List<Position> posRay(Room room, Position s, Position t) {
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
            return octant03Ray(room, s, t, deltaX, deltaY, xDirection, yDirection);
        return octant12Ray(room, s, t, deltaX, deltaY, xDirection, yDirection);
    }

    public List<Element> elemRay(Room room, Position s, Position t) {
        List<Position> lp = posRay(room, s, t);
        if (lp.size() == 0)
            return Collections.emptyList();

        return room.getSamePos(lp.get(lp.size() - 1));
    }
}
