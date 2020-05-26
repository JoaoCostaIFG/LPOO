package org.g73.skanedweller.model.element;

import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.model.colliders.Collider;
import org.g73.skanedweller.model.element.element_behaviours.*;
import org.g73.skanedweller.observe.Observer;

import java.util.List;

public abstract class Element implements Agressive, Collidable, Mortal, Movable {
    private Position pos;
    private Agressive agressiveBehaviour;
    private Collidable collidableBehaviour;
    private Mortal mortalBehaviour;
    private Movable movableBehaviour;

    public Element(Position pos, Agressive agr, Collidable col, Mortal mor, Movable mov) {
        this.pos = pos;

        this.agressiveBehaviour = agr; // TODO this is good enough?
        this.collidableBehaviour = col;
        this.mortalBehaviour = mor;
        this.movableBehaviour = mov;
    }

    public Element(Position pos) {
        this(pos,
                new PassiveBehaviour(),
                new NotCollidableBehaviour(),
                new ImmortalBehaviour(),
                new ImovableBehaviour());
    }

    public Element(int x, int y) {
        this(new Position(x, y));
    }

    public int getX() {
        return this.pos.getX();
    }

    public void setX(int x) {
        this.pos.setX(x);
    }

    public int getY() {
        return this.pos.getY();
    }

    public void setY(int y) {
        this.pos.setY(y);
    }

    public Position getPos() {
        return this.pos;
    }

    public void setPos(Position position) {
        this.pos = position;
        notifyObservers(position);
    }

    /* movement */
    // TODO keep this here?
    // TODO maybe throw all pos peeep into another behaviour
    public Position moveUp() {
        return new Position(getX(), getY() - 1);
    }

    public Position moveUp(int y) {
        return new Position(getX(), getY() - y);
    }

    public Position moveDown() {
        return new Position(getX(), getY() + 1);
    }

    public Position moveDown(int y) {
        return new Position(getX(), getY() + y);
    }

    public Position moveLeft() {
        return new Position(getX() - 1, getY());
    }

    public Position moveLeft(int x) {
        return new Position(getX() - x, getY());
    }

    public Position moveRight() {
        return new Position(getX() + 1, getY());
    }

    public Position moveRight(int x) {
        return new Position(getX() + x, getY());
    }

    /* delegation */
    @Override
    public void addObserver(Observer<Position> observer) {
        collidableBehaviour.addObserver(observer);
    }

    @Override
    public void removeObserver(Observer<Position> observer) {
        collidableBehaviour.removeObserver(observer);
    }

    @Override
    public void notifyObservers(Position subject) {
        collidableBehaviour.notifyObservers(subject);
    }

    @Override
    public int getAtk() {
        return agressiveBehaviour.getAtk();
    }

    @Override
    public void setAtk(int atk) {
        agressiveBehaviour.setAtk(atk);
    }

    @Override
    public int getRange() {
        return agressiveBehaviour.getRange();
    }

    @Override
    public void setRange(int range) {
        agressiveBehaviour.setRange(range);
    }

    @Override
    public void setAtkStrat(AttackStrategy attackStrat) {
        agressiveBehaviour.setAtkStrat(attackStrat);
    }

    @Override
    public boolean attack(Room room, Element me, Element target) {
        // FIXME
        return agressiveBehaviour.attack(room , me, target);
    }

    public boolean attack(Room room, Element target) {
        // FIXME
        return attack(room, this, target);
    }

    @Override
    public int getAtkCounter() {
        return agressiveBehaviour.getAtkCounter();
    }

    @Override
    public void setAtkCounter(int numTicks) {
        agressiveBehaviour.setAtkCounter(numTicks);
    }

    @Override
    public void tickAtkCounter() {
        agressiveBehaviour.tickAtkCounter();
    }

    @Override
    public Collider getCollider() {
        return collidableBehaviour.getCollider();
    }

    @Override
    public boolean collidesWith(Collidable colidee) {
        return collidableBehaviour.collidesWith(colidee);
    }

    @Override
    public Position shadowStep(Position pos) {
        return collidableBehaviour.shadowStep(pos);
    }

    @Override
    public int getHp() {
        return mortalBehaviour.getHp();
    }

    @Override
    public void setHp(int hp) {
        mortalBehaviour.setHp(hp);
    }

    @Override
    public void takeDamage(int dmg) {
        mortalBehaviour.takeDamage(dmg);
    }

    @Override
    public boolean isAlive() {
        return mortalBehaviour.isAlive();
    }

    @Override
    public int getMovCounter() {
        return movableBehaviour.getMovCounter();
    }

    @Override
    public void setMovCounter(int numTicks) {
        movableBehaviour.setMovCounter(numTicks);
    }

    @Override
    public void tickMovCounter() {
        movableBehaviour.tickMovCounter();
    }

    @Override
    public void setMoveStrat(MoveStrategy moveStrat) {
        movableBehaviour.setMoveStrat(moveStrat);
    }

    @Override
    public List<Position> genMoves(Room r, Element e) {
        // FIXME
        return movableBehaviour.genMoves(r, e);
    }

    public List<Position> genMoves(Room r) {
        return genMoves(r, this);
    }
}
