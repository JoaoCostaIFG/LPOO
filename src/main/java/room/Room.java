package room;

import room.element.Element;
import room.element.Skane;
import room.element.Wall;
import observe.Observable;
import observe.Observer;
import com.googlecode.lanterna.TerminalSize;

import java.util.ArrayList;
import java.util.List;

public class Room implements Observable<Room> {
    private TerminalSize board_size;
    private List<Observer<Room>> observers;
    private Skane skane;
    private List<Wall> walls;

    public Room(TerminalSize board_size) {
        this.board_size = board_size;

        this.walls = new ArrayList<>();
        this.observers = new ArrayList<>();
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

    public List<Wall> getWalls() {
        return walls;
    }

    public Skane getSkane() {
        return this.skane;
    }

    public void moveSkane(Position new_p) {
        this.skane.setPos(new_p);
    }

    public void addElement(Element e) {
        if (e instanceof Skane) skane = (Skane) e;
        if (e instanceof Wall) walls.add((Wall) e);
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

}
