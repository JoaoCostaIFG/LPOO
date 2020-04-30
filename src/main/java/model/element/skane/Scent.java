package model.element.skane;

import model.Position;
import model.element.Element;

import java.util.Objects;

public class Scent extends Element {
    private int duration;

    public Scent(Position pos, int duration) {
        super(pos);
        this.duration = duration;
    }

    public Scent(int x, int y, int duration) {
        this(new Position(x, y), duration);
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int new_duration) {
        this.duration = new_duration;
    }

    public void tick() {
        --duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scent scent = (Scent) o;
        return duration == scent.duration && getPos().equals(scent.getPos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, getX(), getY());
    }
}
