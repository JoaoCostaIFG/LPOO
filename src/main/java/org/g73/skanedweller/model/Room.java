package org.g73.skanedweller.model;

import org.g73.skanedweller.model.element.*;
import org.g73.skanedweller.model.element.element_behaviours.Collidable;
import org.g73.skanedweller.model.element.skane.Skane;
import org.g73.skanedweller.model.element.skane.SkaneBody;
import org.g73.skanedweller.observe.Observable;
import org.g73.skanedweller.observe.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Room implements Observable<Room> {
    private int width, height;
    private List<Observer<Room>> observers;
    private Skane skane = null;
    private List<Element> enemies;
    private List<Wall> walls;
    private RayCasting rayCasting;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.observers = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.walls = new ArrayList<>();
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

    public void setRayCasting(RayCasting rayCasting) {
        this.rayCasting = rayCasting;
    }

    public RayCasting getRayCasting() {
        return this.rayCasting;
    }

    public List<Position> posRay(Position s, Position t) {
        return rayCasting.posRay(this, s, t);
    }

    public List<Element> elemRay(Position s, Position t) {
        return rayCasting.elemRay(this, s, t);
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
        if (e instanceof Civilian) enemies.add(e);
        else if (e instanceof MeleeGuy) enemies.add(e);
        else if (e instanceof RangedGuy) enemies.add(e);
        else if (e instanceof Skane) skane = (Skane) e;
        else if (e instanceof Wall) walls.add((Wall) e);

        this.notifyObservers(this);
    }

    public void removeDeadEnemies() {
        ListIterator<Element> iter = enemies.listIterator();
        boolean deletedSomething = false;
        while(iter.hasNext()){
            if(!iter.next().isAlive()) {
                iter.remove();
                deletedSomething = true;
            }
        }

        if (deletedSomething)
            notifyObservers(this);
    }

    public List<Element> getSamePos(Position pos) {
        List<Element> elems = new ArrayList<>();

        for (Element e : getElements())
            if (e.getPos().equals(pos))
                elems.add(e);

        return elems;
    }

    public List<Collidable> getColliding(Collidable ent) {
        // TODO Can be optimized (Divide by octants) or use Listenable
        List<Collidable> res = new ArrayList<>();
        for (Collidable e : getElements()) {
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

    @Override
    public void addObserver(Observer<Room> observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Room> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Room r) {
        for (Observer<Room> o: this.observers)
            o.changed(r);
    }
}
