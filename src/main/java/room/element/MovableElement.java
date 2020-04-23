package room.element;

import room.Position;

public interface MovableElement {
    Position moveDown();

    Position moveDown(int y);

    Position moveLeft();

    Position moveLeft(int x);

    Position moveRight();

    Position moveRight(int x);

    Position moveUp();

    Position moveUp(int y);

    int getMovCounter();

    void setMovCounter(int numTicks);

    void tickMovCounter();
}
