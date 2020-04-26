package room.element;

import observe.Observer;
import room.Position;
import room.Room;
import room.colliders.Collider;
import room.colliders.RectangleCollider;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity extends Element implements MortalElement, MovableElement, CollidableElement {
    private int movCounter = 0;
    private int hp;
    private MoveStrategy strategy = null;
    private Collider collider;

    public Entity(Position pos, int hp, Collider col) {
        super(pos);
        this.hp = hp;
        this.addObserver(col);
    }

    public Entity(Position pos, int hp) {
        this(pos, hp, new RectangleCollider(pos, 1, 1));
    }

    public Entity(int x, int y, int hp) {
        this(new Position(x, y), hp);
    }

    /* health */
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void takeDamage(int dmg) {
        setHp(Math.max(hp - dmg, 0));
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean collidesWith(CollidableElement element) {
        return collider.collidesWith(element.getCollider());
    }

    public Position shadowStep(Position pos) {
        // FIXME code repated on SkaneBody and Wall
        /*
        Position currPos = this.getPos();
        super.setPos(pos);
        notifyObservers(pos);

        return currPos;
         */
        notifyObservers(pos);
        return this.getPos();
    }

    public Collider getCollider() {
        return this.collider;
    }

    /* collision observer */
    @Override
    public void setPos(Position pos) {
        super.setPos(pos);
        notifyObservers(pos);
    }

    @Override
    public void addObserver(Observer<Position> observer) {
        this.collider = (Collider) observer;
    }

    @Override
    public void removeObserver(Observer<Position> observer) {
        this.collider = null;
    }

    @Override
    public void notifyObservers(Position subject) {
        this.collider.changed(subject);
    }

    /* movement strategy */
    public void setMoveStrat(MoveStrategy strat) {
        this.strategy = strat;
    }

    public List<Position> genMoves(Room r) {
        if (strategy == null)
            return new ArrayList<>();
        return strategy.genMoves(r, this);
    }

    public int getMovCounter() {
        return this.movCounter;
    }

    public void setMovCounter(int numTicks) {
        this.movCounter = numTicks;
    }

    public void tickMovCounter() {
        --this.movCounter;
    }

    /* movement */
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
}
