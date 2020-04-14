public interface MovableElement {
    Position moveDown();

    Position moveDown(int y);

    Position moveLeft();

    Position moveLeft(int x);

    Position moveRight();

    Position moveRight(int x);

    Position moveUp();

    Position moveUp(int y);
}
