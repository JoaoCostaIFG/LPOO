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

    private List<Scent> sss = new ArrayList<>();

    public List<Scent> getsss() {
        return sss;
    }

    public List<Element> raycast(Position s, Position t) {
        float x = s.getX();
        float y = s.getY();
        float dirX = t.getX() - x;
        float dirY = t.getY() - y;
        float norm = (float) Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2));
        List<Element> elems = new ArrayList<>();
        Position p;

        dirX /= norm;
        dirY /= norm;

        while (elems.size() == 0) {
            x += dirX;
            y += dirY;
            p = new Position(Math.round(x), Math.round(y));
            elems = getSamePos(p);

            sss.add(new Scent(p, 1));
        }

        return elems;
    }

    public boolean isSkanePos(Position p) {
        for (Element e : getSamePos(p))
            if (e instanceof Skane || e instanceof SkaneBody)
                return true;

        return false;
    }

    // TODO strategy pattern
    public void moveCivilians() {
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
        }
    }
}
