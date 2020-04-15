package observe;

public interface Observable<T> {
    public void addObserver(Observer<T> observer);
    public void removeObserver(Observer<T> observers);
    public void notifyObservers(T subject);
}
