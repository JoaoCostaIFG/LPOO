package arena.observe;

public interface Observer<T> {
    void changed(T observable);
}
