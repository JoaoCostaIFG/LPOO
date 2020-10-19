import java.util.ArrayList;
import java.util.List;

public abstract class Bar {
    private boolean happy_hour = false;
    private List<BarObserver> observers;

    public Bar() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(BarObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BarObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (BarObserver observer : observers)
            if (isHappyHour()) observer.happyHourStarted(this);
            else observer.happyHourEnded(this);
    }

    public boolean isHappyHour() {
        return happy_hour;
    }

    public void startHappyHour() {
        this.happy_hour = true;
        this.notifyObservers();
    }

    public void endHappyHour() {
        this.happy_hour = false;
        this.notifyObservers();
    }
}
