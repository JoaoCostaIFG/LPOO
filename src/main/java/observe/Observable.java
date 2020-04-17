package observe;

public interface Observable<T> {
    void addObserver(Observer<T> observer);
    void removeObserver(Observer<T> observers);
    void notifyObservers(T subject);
}
