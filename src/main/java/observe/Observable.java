package arena.observe;

import java.util.ArrayList;
import java.util.List;

public interface Observable<T> {
    public void addObserver(Observer<T> observer);
    public void removeObserver(Observer<T> observers);
    public void notifyObservers(T subject);
}
