package org.g73.skanedweller.controller.creator;


import org.g73.skanedweller.model.Position;

public abstract class Creator<T> {
    public abstract T create(Position pos);
}
