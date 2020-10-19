package org.g73.skanedweller.observe;

public interface Observer<T> {
    void changed(T observable);
}
